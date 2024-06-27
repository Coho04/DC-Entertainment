package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.entertainment.Main;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the Skip command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Skip implements CommandInterface {

    /**
     * This method is used to define the command data for the Skip command.
     * It returns a CommandData object that contains the command name and description.
     * @return CommandData object for the Skip command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("skip", "Überspringt das momentane Lied!");
    }

    /**
     * This method is used to execute the Skip command when it is invoked as a slash command.
     * It checks if the command is from a guild.
     * If the command is from a guild, it retrieves the GuildMusicManager for the guild, skips to the next track, and sends a reply that the next song is being played.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     * @param e The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            assert e.getGuild() != null;
            GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(e.getGuild());
            musicManager.scheduler.nextTrack();
            e.reply("Der nächste Song wird abgespielt!").queue();
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
