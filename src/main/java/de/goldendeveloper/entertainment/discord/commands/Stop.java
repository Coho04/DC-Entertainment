package de.goldendeveloper.entertainment.discord.commands;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Stop implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("stop", "Stoppe die Musik!");
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(e.getGuild());
            if (musicManager.getPlayer().getPlayingTrack() != null) {
                musicManager.getPlayer().stopTrack();
                if (e.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
                    e.getGuild().getAudioManager().closeAudioConnection();
                    e.reply("Ich beende die Vorstellung!").queue();
                }
            } else {
                e.reply("Es wird momentan nichts abgespielt!").queue();
            }
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
