package io.github.coho04.entertainment.discord.commands.music;

import io.github.coho04.entertainment.Main;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the Play command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Play implements CommandInterface {

    public static String cmdPlay = "play";
    public static String cmdPlayOptionTrack = "trackurl";

    /**
     * This method is used to define the command data for the Play command.
     * It returns a CommandData object that contains the command name and description, and the options for the command.
     *
     * @return CommandData object for the Play command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash(cmdPlay, "Spiele einen Song ab!").addOption(OptionType.STRING, cmdPlayOptionTrack, "YouTube Video Link!", true);
    }

    /**
     * This method is used to execute the Play command when it is invoked as a slash command.
     * It retrieves the track URL from the command options and loads and plays the track if the command is from a guild.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        String TrackUrl = e.getOption(cmdPlayOptionTrack).getAsString();
        if (e.isFromGuild()) {
            Main.getAudioPlayerHelper().loadAndPlay(e, TrackUrl);
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
