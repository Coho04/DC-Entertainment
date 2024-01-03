package de.goldendeveloper.entertainment.discord.commands.music;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Volume implements CommandInterface {

    public static String cmdVolumeOptionVolume = "volume";


    @Override
    public CommandData commandData() {
        return Commands.slash("volume", "Ändere die Lautstärke!")
                .addOption(OptionType.INTEGER, cmdVolumeOptionVolume, "Musik Lautstärke", true);
    }

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
