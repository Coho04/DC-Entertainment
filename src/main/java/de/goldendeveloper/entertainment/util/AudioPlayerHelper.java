package de.goldendeveloper.entertainment.util;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

public class AudioPlayerHelper {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public AudioPlayerHelper() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    private static void connectToVoiceChannel(Member member, AudioManager audioManager) {
        if (!audioManager.isConnected()) {
            if (member.getVoiceState().inAudioChannel()) {
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            }
        }
    }

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
                    firstTrack = playlist.getTracks().get(0);
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

    private void play(Guild guild, Member member, GuildMusicManager musicManager, AudioTrack track) {
        connectToVoiceChannel(member, guild.getAudioManager());
        musicManager.scheduler.queue(track);
    }


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
