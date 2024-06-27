package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.entertainment.Main;
import io.github.coho04.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the LeaveVoice command.
 * It implements the CommandInterface from the DCBot library.
 */
public class LeaveVoice implements CommandInterface {

    /**
     * This method is used to define the command data for the LeaveVoice command.
     * It returns a CommandData object that contains the command name and description.
     *
     * @return CommandData object for the LeaveVoice command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("leave-voice", "Verlässt den Voice Channel!");
    }

    /**
     * This method is used to execute the LeaveVoice command when it is invoked as a slash command.
     * It checks if the command is from a guild and if the guild is not null.
     * If the bot is currently playing a track, it stops the track.
     * If the bot is in an audio channel, it leaves the audio channel.
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
            }
            if (e.getGuild().getSelfMember().getVoiceState() != null) {
                if (e.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
                    e.getGuild().getAudioManager().closeAudioConnection();
                }
            }
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
