package de.goldendeveloper.entertainment.discord.commands;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.enums.EntertainmentType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Entertainment implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("entertainment", "Wähle aus wie du Unterhalten werden möchtest!");
    }

    /**
     * This method is used to run a slash command for entertainment options.
     *
     * @param e The SlashCommandInteractionEvent representing the interaction event.
     * @param dcBot The DCBot instance.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        MessageEmbed embed = new EmbedBuilder().setTitle("Wähle aus welches Entertainment Programm du haben möchtest!").build();
        e.getInteraction().replyEmbeds(embed).addActionRow(
                Button.danger(EntertainmentType.MOVIE.getValue(), "Filme"),
                Button.primary(EntertainmentType.SERIES.getValue(), "Serien"),
                Button.secondary(EntertainmentType.JOKES.getValue(), "Jokes"),
                Button.success(EntertainmentType.GAMES.getValue(), "Games"),
                Button.primary(EntertainmentType.FACTS.getValue(), "Fakten")
        ).addActionRow(
                Button.secondary(EntertainmentType.EIGHTBALL.getValue(), "Eight-Ball"),
                Button.link("https://www.youtube.com/watch?v=dQw4w9WgXcQ", "P*rno")
        ).queue();
    }
}
