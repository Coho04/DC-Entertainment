package de.goldendeveloper.entertainment.discord.commands.music;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Play implements CommandInterface {

    public static String cmdPlay = "play";
    public static String cmdPlayOptionTrack = "trackurl";


    @Override
    public CommandData commandData() {
        return Commands.slash(cmdPlay, "Spiele einen Song ab!").addOption(OptionType.STRING, cmdPlayOptionTrack, "YouTube Video Link!", true);
    }

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
