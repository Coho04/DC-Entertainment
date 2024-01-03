package de.goldendeveloper.entertainment.discord;

import de.goldendeveloper.entertainment.discord.commands.games.ScissorsRockPaper;
import de.goldendeveloper.entertainment.enums.EntertainmentType;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.entertainment.enums.ScissorsRockPaperType;
import de.goldendeveloper.entertainment.util.helpers.games.CountingGameHelper;
import de.goldendeveloper.mysql.entities.*;
import io.sentry.Sentry;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class CustomEvents extends ListenerAdapter {

    private final List<String> countingChannels = new ArrayList<>();

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        String type = e.getButton().getId();
        EntertainmentType entertainmentType = EntertainmentType.getEntertainmentType(type);
        switch (entertainmentType) {
            case SERIES, MOVIE,GAMES -> e.getInteraction().reply("Wir empfehlen dir " + entertainmentType.getNameWithArticle() + " [" + entertainmentType.getItem() + "]!").queue();
            case FACTS, JOKES, EIGHTBALL -> e.getInteraction().reply(entertainmentType.getItem()).queue();
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent e) {
        String cmd = e.getName();
        if (cmd.equalsIgnoreCase(ScissorsRockPaper.cmdScissorsStonePapered)) {
            if (e.getFocusedOption().getName().equalsIgnoreCase(ScissorsRockPaper.cmdScissorsStonePaperedOptionObjekt)) {
                e.replyChoices(
                        ScissorsRockPaperType.getAllScissorsRockPaperTypes().stream().map(type -> new Command.Choice(type.getName(), type.getValue())).collect(Collectors.toList())
                ).queue();
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromGuild() && !e.getAuthor().isBot()) {
            Table table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(CountingGameHelper.tableName);
            if (countingChannels.contains(e.getGuild().getId()) || table.getColumn(CountingGameHelper.channelColumn).getAll().getAsString().contains(e.getChannel().getId())) {
                if (!countingChannels.contains(e.getGuild().getId())) {
                    countingChannels.add(e.getGuild().getId());
                }
                String message = e.getMessage().getContentRaw();
                int number = table.getRow(table.getColumn(CountingGameHelper.channelColumn), e.getChannel().getId()).getData().get(CountingGameHelper.numberColumn).getAsInt();
                CountingGameHelper countingGame = CountingGameHelper.findGame(e.getGuild());
                if (isNumeric(message)) {
                    number = number + 1;
                    if (Integer.parseInt(message) == number) {
                        e.getMessage().addReaction(Emoji.fromUnicode("✅")).queue();
                        if (countingGame != null) {
                            countingGame.setCurrentNumber(number);
                        }
                    } else {
                        assert countingGame != null;
                        countingGame.setCurrentNumber(0);
                        e.getMessage().addReaction(Emoji.fromUnicode("❌")).queue();
                        e.getChannel().sendMessage(e.getAuthor().getAsMention() + " STOPPT DAS SPIEL BEI " + number + "!! Die nächste Zahl ist ´1´.").queue();
                    }
                } else {
                    assert countingGame != null;
                    countingGame.setCurrentNumber(0);
                    e.getMessage().addReaction(Emoji.fromUnicode("❌")).queue();
                    e.getChannel().sendMessage(e.getAuthor().getAsMention() + " STOPPT DAS SPIEL BEI " + number + "!! Die nächste Zahl ist ´1´. Dieser Zählkanal ist im reinen Zahlenmodus.").queue();
                }
            }
        }
    }


    public boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            Sentry.captureException(e);
            System.out.println(e.getMessage());
        }
        return false;
    }
}
