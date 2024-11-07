package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import io.github.coho04.entertainment.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class ClearQueue implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("clear-queue", "Löscht die Queue!");
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            Main.getAudioPlayerHelper().getMusicManager(e.getGuild().getIdLong()).getScheduler().getQueue().clear();
            e.reply("Die Queue wurde gelöscht!").queue();
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
