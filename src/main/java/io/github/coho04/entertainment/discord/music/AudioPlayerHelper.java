package io.github.coho04.entertainment.discord.music;

import dev.arbjerg.lavalink.client.*;
import dev.arbjerg.lavalink.client.event.TrackEndEvent;
import io.github.coho04.entertainment.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AudioPlayerHelper {

    private final LavalinkClient lavalinkClient;
    public final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();


    public AudioPlayerHelper() {
        this.lavalinkClient = Main.getDcBot().getDiscord().getClient();
        lavalinkClient.on(TrackEndEvent.class).subscribe((event) -> {
            Optional.ofNullable(musicManagers.get(event.getGuildId())).ifPresent(
                    (mng) -> mng.scheduler.onTrackEnd(event.getTrack(), event.getEndReason())
            );
        });
    }

    public void loadAndPlay(SlashCommandInteractionEvent event, String trackUrl) {
        Guild guild = event.getGuild();
        event.getJDA().getDirectAudioController().connect(event.getMember().getVoiceState().getChannel());

        Link link = lavalinkClient.getOrCreateLink(event.getGuild().getIdLong());
        final long guildId = guild.getIdLong();
        final var audioManager = this.getOrCreateMusicManager(guildId);
        link.loadItem(trackUrl)
                .doOnError(e -> System.out.println("Track loading error: " + e.getMessage()))
                .subscribe(new AudioLoader(event, audioManager));
        event.reply("Track wird geladen...").queue();
    }

    public GuildMusicManager getOrCreateMusicManager(long guildId) {
        synchronized (this) {
            var musicManager = this.musicManagers.get(guildId);
            if (musicManager == null) {
                musicManager = new GuildMusicManager(guildId, this.lavalinkClient);
                this.musicManagers.put(guildId, musicManager);
            }
            return musicManager;
        }
    }

    public GuildMusicManager getMusicManager(long guildId) {
        synchronized (this) {
            return this.musicManagers.get(guildId);
        }
    }
}
