package io.github.coho04.entertainment.util;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.github.coho04.entertainment.discord.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides helper methods for managing audio players.
 * It contains a map of music managers for each guild and an audio player manager.
 */
public class AudioPlayerHelper {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    /**
     * Constructor for the AudioPlayerHelper.
     * It initializes the music managers map and the audio player manager, and registers the audio sources.
     */
    public AudioPlayerHelper() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    /**
     * This method connects to the voice channel of the member if the audio manager is not already connected.
     *
     * @param member       The member to connect to their voice channel.
     * @param audioManager The audio manager to connect to the voice channel.
     */
    private static void connectToVoiceChannel(Member member, AudioManager audioManager) {
        if (!audioManager.isConnected()) {
            if (member.getVoiceState().inAudioChannel()) {
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            }
        }
    }

    /**
     * This method loads and plays a track from a URL.
     * It retrieves the music manager for the guild, loads the track from the URL, and plays the track.
     *
     * @param e        The SlashCommandInteractionEvent object that represents the slash command interaction event.
     * @param trackUrl The URL of the track to load and play.
     */
    public void loadAndPlay(final SlashCommandInteractionEvent e, String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(e.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                e.reply(track.getInfo().title + " wurde der Warteschlange hinzugefügt!").queue();
                play(e.getGuild(), e.getMember(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().getFirst();
                }
                for (AudioTrack track : playlist.getTracks()) {
                    musicManager.scheduler.queue(track);
                }
                e.reply(firstTrack.getInfo().title + " wurde der Warteschlange hinzugefügt! (Erster Song der Playlist: " + playlist.getName() + ")").queue();
                play(e.getGuild(), e.getMember(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                e.reply("Es konnte nichts gefunden werden mit dem Link: " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                e.reply(exception.getMessage() + " konnte nicht abgespielt werden!").queue();
            }
        });
    }

    /**
     * This method plays a track.
     * It connects to the voice channel of the member and queues the track.
     *
     * @param guild        The guild to play the track in.
     * @param member       The member to connect to their voice channel.
     * @param musicManager The music manager to queue the track.
     * @param track        The track to play.
     */
    private void play(Guild guild, Member member, GuildMusicManager musicManager, AudioTrack track) {
        connectToVoiceChannel(member, guild.getAudioManager());
        musicManager.scheduler.queue(track);
    }

    /**
     * This method retrieves the music manager for a guild.
     * If a music manager does not exist for the guild, it creates a new one and adds it to the map.
     *
     * @param guild The guild to retrieve the music manager for.
     * @return The music manager for the guild.
     */
    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        GuildMusicManager musicManager = musicManagers.get(guild.getIdLong());
        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guild.getIdLong(), musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }
}
