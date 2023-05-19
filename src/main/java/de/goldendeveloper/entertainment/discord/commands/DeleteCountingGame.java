package de.goldendeveloper.entertainment.discord.commands;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.util.CountingGameHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Objects;

public class DeleteCountingGame implements CommandInterface {

    public static String cmdDeleteCountingGame = "delete-counting-game";

    @Override
    public CommandData commandData() {
        return Commands.slash(cmdDeleteCountingGame, "Löscht das momentane Zählspiel!");
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.getMember() != null && e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            if (CountingGameHelper.hasCountingGame(e.getGuild())) {
                CountingGameHelper countingGame = CountingGameHelper.findGame(Objects.requireNonNull(e.getGuild()));
                if (countingGame != null) {
                    countingGame.delete();
                }
                e.reply("Der Counting Channel wurde gelöscht!").queue();
            } else {
                e.reply("Dieser Server hat keinen Zähl Channel bitte erstelle zuerst einen mit dem Befehl: /" + CountingGame.cmdCountingGame + "!").queue();
            }
        } else {
            e.reply("Dazu hast du keine Rechte!").queue();
        }
    }
}
