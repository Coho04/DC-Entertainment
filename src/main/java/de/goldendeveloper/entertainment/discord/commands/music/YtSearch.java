package de.goldendeveloper.entertainment.discord.commands.music;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.Youtube;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class YtSearch implements CommandInterface {

    public static String cmdPlayOptionTrack = "track-url";

    @Override
    public CommandData commandData() {
        return Commands.slash("ytsearch", "Suche nach einem YouTube Video!")
                .addOption(OptionType.STRING, cmdPlayOptionTrack, "YouTube Video Link!", true);
    }

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
