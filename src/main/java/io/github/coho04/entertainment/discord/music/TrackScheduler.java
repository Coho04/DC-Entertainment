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

    public TrackScheduler(GuildMusicManager guildMusicManager) {
        this.guildMusicManager = guildMusicManager;
    }

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


    private void startTrack(Track track) {
        this.guildMusicManager.getLink().ifPresent(
                (link) -> link.createOrUpdatePlayer()
                        .setTrack(track)
                        .setVolume(35)
                        .subscribe()
        );
    }

    public Queue<Track> getQueue() {
        return queue;
    }
}