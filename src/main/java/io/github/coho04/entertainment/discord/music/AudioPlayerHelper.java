package io.github.coho04.entertainment.discord.music;

import dev.arbjerg.lavalink.client.*;
import dev.arbjerg.lavalink.client.event.TrackEndEvent;
import io.github.coho04.entertainment.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Helper class to manage audio playback in a Discord server using Lavalink.
 */
public class AudioPlayerHelper {

    private final LavalinkClient lavalinkClient;
    public final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();

    /**
     * Constructs an AudioPlayerHelper and initializes the Lavalink client.
     * Subscribes to the TrackEndEvent to handle track end events.
     */
    public AudioPlayerHelper() {
        this.lavalinkClient = Main.getDcBot().getDiscord().getClient();
        lavalinkClient.on(TrackEndEvent.class).subscribe((event) -> Optional.ofNullable(musicManagers.get(event.getGuildId())).ifPresent(
                (mng) -> mng.getScheduler().onTrackEnd(event.getEndReason())
        ));
    }

    /**
     * Loads and plays a track in the specified guild.
     *
     * @param event    The SlashCommandInteractionEvent triggered by the command.
     * @param trackUrl The URL of the track to be loaded and played.
     */
    public void loadAndPlay(SlashCommandInteractionEvent event, String trackUrl) {
        Guild guild = event.getGuild();
        event.getJDA().getDirectAudioController().connect(event.getMember().getVoiceState().getChannel());
        final long guildId = guild.getIdLong();

        Link link = lavalinkClient.getOrCreateLink(guildId);
        final var audioManager = this.getOrCreateMusicManager(guildId);
        link.loadItem(trackUrl)
                .doOnError(e -> {
                    event.reply("Fehler beim Laden des Tracks: " + e.getMessage()).queue();
                    System.out.println("Fehler beim Laden des Tracks: " + e.getMessage());
                })
                .subscribe(new AudioLoader(event, audioManager));
        event.reply("Track wird geladen...").queue();
    }

    /**
     * Retrieves or creates a GuildMusicManager for the specified guild.
     *
     * @param guildId The ID of the guild.
     * @return The GuildMusicManager instance for the guild.
     */
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

    /**
     * Retrieves the GuildMusicManager for the specified guild.
     *
     * @param guildId The ID of the guild.
     * @return The GuildMusicManager instance for the guild.
     */
    public GuildMusicManager getMusicManager(long guildId) {
        synchronized (this) {
            return this.musicManagers.get(guildId);
        }
    }
}
