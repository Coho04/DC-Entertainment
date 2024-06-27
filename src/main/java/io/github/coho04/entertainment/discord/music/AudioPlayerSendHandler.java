package io.github.coho04.entertainment.discord.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * This class represents an AudioPlayerSendHandler.
 * It implements the AudioSendHandler interface from the JDA library.
 * It is used to handle sending audio data to Discord.
 */
public class AudioPlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;

    /**
     * @param audioPlayer Audio player to wrap.
     */
    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    /**
     * Checks if the AudioPlayerSendHandler can provide audio frames.
     *
     * @return True if the AudioPlayerSendHandler can provide audio frames, false otherwise.
     */
    @Override
    public boolean canProvide() {
        return audioPlayer.provide(frame);
    }

    /**
     * Provides a 20ms audio frame from the buffer.
     *
     * @return A ByteBuffer containing the 20ms audio frame.
     */
    @Override
    public ByteBuffer provide20MsAudio() {
        ((Buffer) buffer).flip();
        return buffer;
    }

    /**
     * Checks if the AudioPlayerSendHandler supports the Opus audio format.
     *
     * @return True if the AudioPlayerSendHandler supports Opus, false otherwise.
     */
    @Override
    public boolean isOpus() {
        return true;
    }
}