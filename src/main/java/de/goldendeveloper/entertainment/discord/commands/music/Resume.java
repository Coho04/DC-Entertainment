package de.goldendeveloper.entertainment.discord.commands.music;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Resume implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("resume", "Setze die Musik fort!");
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            resumeTrack(e);
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }

    private void resumeTrack(SlashCommandInteractionEvent e) {
        GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(e.getGuild());
        if (musicManager.getPlayer().isPaused()) {
            musicManager.getPlayer().setPaused(false);
            e.reply("Die Musik wird weiter gespielt!").queue();
        } else {
            e.reply("Es konnte nichts Abgespielt werden!").queue();
        }
    }
}
