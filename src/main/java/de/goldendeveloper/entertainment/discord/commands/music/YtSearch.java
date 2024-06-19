package de.goldendeveloper.entertainment.discord.commands.music;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.Youtube;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * This class represents the YtSearch command.
 * It implements the CommandInterface from the DCBot library.
 */
public class YtSearch implements CommandInterface {

    public static String cmdPlayOptionTrack = "track-url";

    /**
     * This method is used to define the command data for the YtSearch command.
     * It returns a CommandData object that contains the command name and description, and the options for the command.
     *
     * @return CommandData object for the YtSearch command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("ytsearch", "Suche nach einem YouTube Video!")
                .addOption(OptionType.STRING, cmdPlayOptionTrack, "YouTube Video Link!", true);
    }

    /**
     * This method is used to execute the YtSearch command when it is invoked as a slash command.
     * It retrieves the keyword from the command options and searches for a YouTube video with that keyword.
     * If the command is from a guild, it loads and plays the first video from the search results.
     * If the command is not from a guild, it sends a reply that the command can only be used on a server.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        String keyWord = e.getOption(cmdPlayOptionTrack).getAsString();
        Youtube youtube = new Youtube(Main.getCustomConfig().getYtApiKey());
        youtube.setKeyWord(keyWord);
        if (e.isFromGuild()) {
            Main.getAudioPlayerHelper().loadAndPlay(e, "https://www.youtube.com/watch?v=" + youtube.execute());
        } else {
            e.reply("Dieser Command ist nur auf einem Server möglich!").queue();
        }
    }
}
