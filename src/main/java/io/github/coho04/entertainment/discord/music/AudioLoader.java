package io.github.coho04.entertainment.discord.music;

import dev.arbjerg.lavalink.client.AbstractAudioLoadResultHandler;
import dev.arbjerg.lavalink.client.player.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Handles the loading of audio tracks and playlists for a Discord server.
 */
public class AudioLoader extends AbstractAudioLoadResultHandler {
    private final SlashCommandInteractionEvent event;
    private final GuildMusicManager manager;

    /**
     * Constructs an AudioLoader.
     *
     * @param event   The SlashCommandInteractionEvent triggered by the command.
     * @param manager The GuildMusicManager instance.
     */
    public AudioLoader(SlashCommandInteractionEvent event, GuildMusicManager manager) {
        this.event = event;
        this.manager = manager;
    }

    /**
     * Called when a single track is loaded.
     *
     * @param result The result containing the loaded track.
     */
    @Override
    public void ontrackLoaded(@NotNull TrackLoaded result) {
        final Track track = result.getTrack();

        var userData = new MyUserData(event.getUser().getIdLong());

        track.setUserData(userData);

        this.manager.getScheduler().enqueue(track);

        final var trackTitle = track.getInfo().getTitle();

        event.getHook().sendMessage(trackTitle + " wurde der Warteschlange hinzugefügt \nAngefragt von: <@" + userData.requester() + '>').queue();
    }

    /**
     * Called when a playlist is loaded.
     *
     * @param result The result containing the loaded playlist.
     */
    @Override
    public void onPlaylistLoaded(@NotNull PlaylistLoaded result) {
        final int trackCount = result.getTracks().size();
        event.getHook()
                .sendMessage(trackCount + "Tracks wurden von " + result.getInfo().getName() + " der Warteschlange hinzugefügt!")
                .queue();

        this.manager.getScheduler().enqueuePlaylist(result.getTracks());
    }

    /**
     * Called when search results are loaded.
     *
     * @param result The result containing the search results.
     */
    @Override
    public void onSearchResultLoaded(@NotNull SearchResult result) {
        final List<Track> tracks = result.getTracks();

        if (tracks.isEmpty()) {
            event.getHook().sendMessage("Keine Tracks gefunden!").queue();
            return;
        }
        final Track firstTrack = tracks.getFirst();
        event.getHook().sendMessage("Zur Warteschlange hinzugefügt: " + firstTrack.getInfo().getTitle()).queue();
        this.manager.getScheduler().enqueue(firstTrack);
    }

    /**
     * Called when no matches are found.
     */
    @Override
    public void noMatches() {
        event.getHook().sendMessage("Keine passenden Ergebnisse für deine Eingabe gefunden").queue();
    }

    /**
     * Called when loading a track fails.
     *
     * @param result The result containing the failure information.
     */
    @Override
    public void loadFailed(@NotNull LoadFailed result) {
        event.getHook().sendMessage("Track konnte nicht geladen werden! " + result.getException().getMessage()).queue();
    }
}