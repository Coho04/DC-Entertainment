package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.entertainment.Main;
import io.github.coho04.entertainment.discord.music.GuildMusicManager;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the Volume command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Volume implements CommandInterface {

    public static String cmdVolumeOptionVolume = "volume";

    /**
     * This method is used to define the command data for the Volume command.
     * It returns a CommandData object that contains the command name and description, and the options for the command.
     *
     * @return CommandData object for the Volume command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("volume", "Ändere die Lautstärke!")
                .addOption(OptionType.INTEGER, cmdVolumeOptionVolume, "Musik Lautstärke", true);
    }

    /**
     * This method is used to execute the Volume command when it is invoked as a slash command.
     * It retrieves the volume from the command options and sets the volume of the player if the command is from a guild and the player is not paused.
     * If the player is paused, it sends a reply that there is no track to change the volume of.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        if (e.isFromGuild()) {
            int volume = e.getOption(cmdVolumeOptionVolume).getAsInt();
            GuildMusicManager musicManager = Main.getAudioPlayerHelper().getGuildAudioPlayer(e.getGuild());
            if (!musicManager.getPlayer().isPaused()) {
                musicManager.getPlayer().setVolume(volume);
                e.reply("Die Musik wird nun mit Lautstärke " + volume + "!").queue();
            } else {
                e.reply("Es konnte nichts Abgespielt werden!").queue();
            }
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
