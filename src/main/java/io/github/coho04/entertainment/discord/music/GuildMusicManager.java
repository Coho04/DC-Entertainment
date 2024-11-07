package io.github.coho04.entertainment.discord.music;

import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.client.Link;
import dev.arbjerg.lavalink.client.player.LavalinkPlayer;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class GuildMusicManager {

    private final TrackScheduler scheduler = new TrackScheduler(this);
    private final long guildId;
    private final LavalinkClient lavalink;

    public GuildMusicManager(long guildId, LavalinkClient lavalink) {
        this.lavalink = lavalink;
        this.guildId = guildId;
    }

    public void stop() {
        this.scheduler.getQueue().clear();

        this.getPlayer().ifPresent(
                (player) -> player.setPaused(false)
                        .setTrack(null)
                        .subscribe()
        );
    }

    public Optional<Link> getLink() {
        return Optional.ofNullable(
                this.lavalink.getLinkIfCached(this.guildId)
        );
    }

    public Optional<LavalinkPlayer> getPlayer() {
        return this.getLink().map(Link::getCachedPlayer);
    }

    public Mono<LavalinkPlayer> getMonoPlayer() {
        if (this.getPlayer().isPresent()) {
            return Mono.just(this.getPlayer().get());
        }
        return null;
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }
}