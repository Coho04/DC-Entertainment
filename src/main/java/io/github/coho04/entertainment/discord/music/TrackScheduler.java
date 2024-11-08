package io.github.coho04.entertainment.discord.music;

import dev.arbjerg.lavalink.client.player.Track;
import dev.arbjerg.lavalink.protocol.v4.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * TrackScheduler is responsible for managing the audio tracks in a queue and playing them sequentially.
 */
public class TrackScheduler {
    private final GuildMusicManager guildMusicManager;
    private final Queue<Track> queue = new LinkedList<>();

    /**
     * Constructs a TrackScheduler.
     *
     * @param guildMusicManager The GuildMusicManager instance.
     */
    public TrackScheduler(GuildMusicManager guildMusicManager) {
        this.guildMusicManager = guildMusicManager;
    }

    /**
     * Adds a track to the queue or starts playing it if no track is currently playing.
     *
     * @param track The track to be added to the queue.
     */
    public void enqueue(Track track) {
        this.guildMusicManager.getPlayer().ifPresentOrElse(
                (player) -> {
                    if (player.getTrack() == null) {
                        this.startTrack(track);
                    } else {
                        this.queue.offer(track);
                    }
                },
                () -> this.startTrack(track)
        );
    }

    /**
     * Adds a list of tracks to the queue and starts playing the first track if no track is currently playing.
     *
     * @param tracks The list of tracks to be added to the queue.
     */
    public void enqueuePlaylist(List<Track> tracks) {
        this.queue.addAll(tracks);

        this.guildMusicManager.getPlayer().ifPresentOrElse(
                (player) -> {
                    if (player.getTrack() == null) {
                        this.startTrack(this.queue.poll());
                    }
                },
                () -> this.startTrack(this.queue.poll())
        );
    }

    /**
     * Handles the end of a track and starts the next track if applicable.
     *
     * @param endReason The reason why the track ended.
     */
    public void onTrackEnd(Message.EmittedEvent.TrackEndEvent.AudioTrackEndReason endReason) {
        if (endReason.getMayStartNext()) {
            final var nextTrack = this.queue.poll();

            if (nextTrack != null) {
                this.startTrack(nextTrack);
            }
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        this.guildMusicManager.getPlayer().ifPresentOrElse(
                (player) -> {
                    if (player.getTrack() != null) {
                        player.stopTrack();
                    }
                    this.startTrack(this.queue.poll());
                },
                () -> this.startTrack(this.queue.poll())
        );
    }

    /**
     * Starts playing the given track.
     *
     * @param track The track to be played.
     */
    private void startTrack(Track track) {
        this.guildMusicManager.getLink().ifPresent(
                (link) -> link.createOrUpdatePlayer()
                        .setTrack(track)
                        .setVolume(35)
                        .subscribe()
        );
    }

    /**
     * Retrieves the queue of tracks.
     *
     * @return The queue of tracks.
     */
    public Queue<Track> getQueue() {
        return queue;
    }
}