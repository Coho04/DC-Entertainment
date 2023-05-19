package de.goldendeveloper.entertainment.discord.commands;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.discord.music.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Objects;

public class Pause implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("pause", "Pausiere die Musik!");
    }

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
