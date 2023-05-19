package de.goldendeveloper.entertainment.discord.commands;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.util.CountingGameHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CountingGame implements CommandInterface {

    public static String cmdCountingGame = "counting-game";
    public static String cmdCountingGameChannelName = "channel-name";

    @Override
    public CommandData commandData() {
        return Commands.slash(cmdCountingGame, "Erstellt einen Zähl Game Channel und Startet das Game!")
                .addOption(OptionType.STRING, cmdCountingGameChannelName, "TextChanel Name für den Game Channel", true);
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            if (!CountingGameHelper.hasCountingGame(e.getGuild())) {
                String channelName = e.getOption(cmdCountingGameChannelName).getAsString();
                CountingGameHelper.create(channelName, e.getGuild());
                e.reply("Der Counting Channel wurde erstellt!").queue();
            } else {
                e.reply("Dieser Server hat bereits einen ZählChannel bitte lösche diesen zuerst mit dem Befehl: /" + DeleteCountingGame.cmdDeleteCountingGame + "!").queue();
            }
        } else {
            e.reply("Dazu hast du keine Rechte!").queue();
        }
    }
}
