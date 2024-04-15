package de.goldendeveloper.entertainment.discord;

import de.goldendeveloper.entertainment.discord.commands.games.ScissorsRockPaper;
import de.goldendeveloper.entertainment.enums.EntertainmentType;
import de.goldendeveloper.entertainment.enums.ScissorsRockPaperType;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class CustomEvents extends ListenerAdapter {

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
}
