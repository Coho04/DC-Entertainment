package de.goldendeveloper.entertainment.discord.commands.music;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the Resume command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Resume implements CommandInterface {

    /**
     * This method is used to define the command data for the Resume command.
     * It returns a CommandData object that contains the command name and description.
     *
     * @return CommandData object for the Resume command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("resume", "Setze die Musik fort!");
    }

    /**
     * This method is used to execute the Resume command when it is invoked as a slash command.
     * It checks if the command is from a guild.
     * If the command is from a guild, it calls the resumeTrack method.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            resumeTrack(e);
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }

    /**
     * This method is used to resume the track that is currently paused.
     * It retrieves the GuildMusicManager for the guild and checks if the player is paused.
     * If the player is paused, it sets the player to not paused and sends a reply that the music is being resumed.
     * If the player is not paused, it sends a reply that there is no track to resume.
     *
     * @param e The SlashCommandInteractionEvent object that represents the command interaction event.
     */
    private void resumeTrack(SlashCommandInteractionEvent e) {
        GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(e.getGuild());
        if (musicManager.getPlayer().isPaused()) {
            musicManager.getPlayer().setPaused(false);
            e.reply("Die Musik wird weiter gespielt!").queue();
        } else {
            e.reply("Es konnte nichts Abgespielt werden!").queue();
        }
    }
}
