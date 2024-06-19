package de.goldendeveloper.entertainment.discord;

import de.goldendeveloper.entertainment.discord.commands.games.ScissorsRockPaper;
import de.goldendeveloper.entertainment.enums.EntertainmentType;
import de.goldendeveloper.entertainment.enums.ScissorsRockPaperType;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * This class represents custom events for the Discord bot.
 * It extends the ListenerAdapter from the JDA library, which allows it to listen for and handle various types of events.
 */
public class CustomEvents extends ListenerAdapter {

    /**
     * This method is triggered when a button interaction event occurs.
     * It retrieves the type of the button that was clicked and responds with a message based on the type.
     * @param e The ButtonInteractionEvent object that represents the button interaction event.
     */
    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        String type = e.getButton().getId();
        EntertainmentType entertainmentType = EntertainmentType.getEntertainmentType(type);
        switch (entertainmentType) {
            case SERIES, MOVIE,GAMES -> e.getInteraction().reply("Wir empfehlen dir " + entertainmentType.getNameWithArticle() + " [" + entertainmentType.getItem() + "]!").queue();
            case FACTS, JOKES, EIGHTBALL -> e.getInteraction().reply(entertainmentType.getItem()).queue();
        }
    }

    /**
     * This method is triggered when a command auto complete interaction event occurs.
     * It checks if the command is the ScissorsRockPaper command and if the focused option is the object option.
     * If both conditions are met, it replies with the choices for the ScissorsRockPaperType enum.
     * @param e The CommandAutoCompleteInteractionEvent object that represents the command auto complete interaction event.
     */
    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent e) {
        String cmd = e.getName();
        if (cmd.equalsIgnoreCase(ScissorsRockPaper.cmdScissorsStonePapered)) {
            if (e.getFocusedOption().getName().equalsIgnoreCase(ScissorsRockPaper.cmdScissorsStonePaperedOptionObjekt)) {
                e.replyChoices(
                        ScissorsRockPaperType.getAllScissorsRockPaperTypes().stream().map(type -> new Command.Choice(type.getName(), type.getValue())).collect(Collectors.toList())
                ).queue();
            }
        }
    }
}
