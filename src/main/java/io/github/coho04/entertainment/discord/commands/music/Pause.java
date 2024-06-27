package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.entertainment.Main;
import io.github.coho04.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Objects;

/**
 * This class represents the Pause command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Pause implements CommandInterface {

    /**
     * This method is used to define the command data for the Pause command.
     * It returns a CommandData object that contains the command name and description.
     *
     * @return CommandData object for the Pause command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("pause", "Pausiere die Musik!");
    }

    /**
     * This method is used to execute the Pause command when it is invoked as a slash command.
     * It checks if the command is from a guild.
     * If the bot is currently playing a track and it's not paused, it pauses the track and sends a reply that the music has been paused.
     * If the bot is not playing a track or the track is already paused, it sends a reply that there is currently no music playing.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(Objects.requireNonNull(e.getGuild()));
            if (!musicManager.getPlayer().isPaused()) {
                musicManager.getPlayer().setPaused(true);
                e.reply("Die Musik wurde pausiert!").queue();
            } else {
                e.reply("Es wird momentan nichts abgespielt!").queue();
            }
        } else {
            e.reply("Dieser Befehl ist nur auf einem Server möglich!").queue();
        }
    }
}
