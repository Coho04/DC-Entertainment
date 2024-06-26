package io.github.coho04.entertainment.discord.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * The GuildMusicManager class represents a music manager for a guild. It contains an AudioPlayer and a TrackScheduler,
 * and provides methods for accessing and manipulating them.
 */
public class GuildMusicManager {

    public final AudioPlayer player;
    public final TrackScheduler scheduler;

    /**
     * Creates a player and a track scheduler.
     *
     * @param manager Audio player manager to use for creating the player.
     */
    public GuildMusicManager(AudioPlayerManager manager) {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
    }

    /**
     * @return Audio player for the guild.
     */
    public AudioPlayer getPlayer() {
        return player;
    }

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }
}