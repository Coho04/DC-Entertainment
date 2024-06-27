package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.entertainment.Main;
import io.github.coho04.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the Stop command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Stop implements CommandInterface {

    /**
     * This method is used to define the command data for the Stop command.
     * It returns a CommandData object that contains the command name and description.
     *
     * @return CommandData object for the Stop command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("stop", "Stoppe die Musik!");
    }

    /**
     * This method is used to execute the Stop command when it is invoked as a slash command.
     * It checks if the command is from a guild and if the guild is not null.
     * If the bot is currently playing a track, it stops the track and if the bot is in an audio channel, it leaves the audio channel and sends a reply that the performance is being ended.
     * If the bot is not playing a track, it sends a reply that there is currently no music playing and if the bot is in an audio channel, it leaves the audio channel.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild() && e.getGuild() != null) {
            GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(e.getGuild());
            if (musicManager.getPlayer().getPlayingTrack() != null) {
                musicManager.getPlayer().stopTrack();
                if (e.getGuild().getSelfMember().getVoiceState() != null) {
                    if (e.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
                        e.getGuild().getAudioManager().closeAudioConnection();
                        e.reply("Ich beende die Vorstellung!").queue();
                    }
                }
            } else {
                e.reply("Es wird momentan nichts abgespielt!").queue();
                if (e.getGuild().getSelfMember().getVoiceState() != null) {
                    if (e.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
                        e.getGuild().getAudioManager().closeAudioConnection();
                    }
                }
            }
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
