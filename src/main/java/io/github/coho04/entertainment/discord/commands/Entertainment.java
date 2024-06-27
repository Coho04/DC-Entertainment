package io.github.coho04.entertainment.discord.commands;

import io.github.coho04.entertainment.enums.EntertainmentType;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * This class represents the Entertainment command.
 * It implements the CommandInterface from the DCBot library.
 */
public class Entertainment implements CommandInterface {

    /**
     * This method is used to define the command data for the Entertainment command.
     * It returns a CommandData object that contains the command name and description.
     *
     * @return CommandData object for the Entertainment command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash("entertainment", "Wähle aus wie du Unterhalten werden möchtest!");
    }

    /**
     * This method is used to execute the Entertainment command when it is invoked as a slash command.
     * It creates an embed message with a title and adds action rows with buttons for different entertainment options.
     * The buttons are created with different styles and labels, and some of them have a value that corresponds to an EntertainmentType enum value.
     * The embed message and the action rows are sent as a reply to the command interaction.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
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
