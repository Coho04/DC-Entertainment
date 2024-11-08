package io.github.coho04.entertainment.discord.music;

import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.client.Link;
import dev.arbjerg.lavalink.client.player.LavalinkPlayer;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Manages the music playback for a specific guild.
 */
public class GuildMusicManager {

    private final TrackScheduler scheduler = new TrackScheduler(this);
    private final long guildId;
    private final LavalinkClient lavalink;

    /**
     * Constructs a GuildMusicManager.
     *
     * @param guildId The ID of the guild.
     * @param lavalink The LavaLink client instance.
     */
    public GuildMusicManager(long guildId, LavalinkClient lavalink) {
        this.lavalink = lavalink;
        this.guildId = guildId;
    }

    /**
     * Stops the music playback and clears the queue.
     */
    public void stop() {
        this.scheduler.getQueue().clear();

        this.getPlayer().ifPresent(
                (player) -> player.setPaused(false)
                        .setTrack(null)
                        .subscribe()
        );
    }

    /**
     * Retrieves the Link for the guild if it is cached.
     *
     * @return An Optional containing the Link if it is cached, otherwise empty.
     */
    public Optional<Link> getLink() {
        return Optional.ofNullable(
                this.lavalink.getLinkIfCached(this.guildId)
        );
    }

    /**
     * Retrieves the LavaLinkPlayer for the guild if it is cached.
     *
     * @return An Optional containing the LavaLinkPlayer if it is cached, otherwise empty.
     */
    public Optional<LavalinkPlayer> getPlayer() {
        return this.getLink().map(Link::getCachedPlayer);
    }

    /**
     * Retrieves a Mono that emits the LavaLinkPlayer if it is cached.
     *
     * @return A Mono emitting the LavaLinkPlayer if it is cached, otherwise null.
     */
    public Mono<LavalinkPlayer> getMonoPlayer() {
        if (this.getPlayer().isPresent()) {
            return Mono.just(this.getPlayer().get());
        }
        return null;
    }

    /**
     * Retrieves the TrackScheduler for the guild.
     *
     * @return The TrackScheduler instance.
     */
    public TrackScheduler getScheduler() {
        return scheduler;
    }
}