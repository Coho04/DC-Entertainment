package io.github.coho04.entertainment.discord.commands.games;

import io.github.coho04.entertainment.enums.ScissorsRockPaperType;
import io.github.coho04.dcbcore.DCBot;
import io.github.coho04.dcbcore.interfaces.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Random;

/**
 * This class represents the ScissorsRockPaper game command.
 * It implements the CommandInterface from the DCBot library.
 */
public class ScissorsRockPaper implements CommandInterface {

    public static String cmdScissorsStonePapered = "scissors-stone-paper";
    public static String cmdScissorsStonePaperedOptionObjekt = "objekt";

    /**
     * This method is used to define the command data for the ScissorsRockPaper command.
     * It returns a CommandData object that contains the command name and description, and the options for the command.
     *
     * @return CommandData object for the ScissorsRockPaper command.
     */
    @Override
    public CommandData commandData() {
        return Commands.slash(cmdScissorsStonePapered, "Spiele Schere Stein Papier!")
                .addOption(OptionType.STRING, cmdScissorsStonePaperedOptionObjekt, "Wähle Schere Stein oder Papier aus!", true, true);
    }

    /**
     * This method is used to execute the ScissorsRockPaper command when it is invoked as a slash command.
     * It retrieves the user's choice and the bot's choice, compares them, and sends the result as a reply to the command.
     *
     * @param e     The SlashCommandInteractionEvent object that represents the command interaction event.
     * @param dcBot The DCBot object that represents the bot.
     */
    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        String choice = e.getOption(cmdScissorsStonePaperedOptionObjekt).getAsString();
        ScissorsRockPaperType userChoice = ScissorsRockPaperType.getScissorsRockPaperType(choice);
        if (userChoice != null) {
            int i = new Random().nextInt(ScissorsRockPaperType.getAllScissorsRockPaperTypes().size());
            ScissorsRockPaperType botChoice = ScissorsRockPaperType.getAllScissorsRockPaperTypes().get(i);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("**Schere Stein Papier**");
            embedBuilder.addField("Deine Auswahl:", userChoice.getName(), false);
            embedBuilder.addField("Mein Auswahl:", botChoice.getName(), false);
            if (userChoice == botChoice) {
                embedBuilder.addField("Resultat", "Mhh, Sieht so aus als steht es Unentschieden!!", false);
            } else if (userChoice == ScissorsRockPaperType.STONE && botChoice == ScissorsRockPaperType.SCISSORS) {
                embedBuilder.addField("Resultat", "Gratulation du hast Gewonnen!!", false);
            } else if (userChoice == ScissorsRockPaperType.STONE && botChoice == ScissorsRockPaperType.PAPER) {
                embedBuilder.addField("Resultat", "Leider hast du Verloren, Versuch es am besten noch einmal!!", false);
            } else if (userChoice == ScissorsRockPaperType.SCISSORS && botChoice == ScissorsRockPaperType.STONE) {
                embedBuilder.addField("Resultat", "Leider hast du Verloren, Versuch es am besten noch einmal!!", false);
            } else if (userChoice == ScissorsRockPaperType.SCISSORS && botChoice == ScissorsRockPaperType.PAPER) {
                embedBuilder.addField("Resultat", "Gratulation du hast Gewonnen!!", false);
            } else if (userChoice == ScissorsRockPaperType.PAPER && botChoice == ScissorsRockPaperType.STONE) {
                embedBuilder.addField("Resultat", "Gratulation du hast Gewonnen!!", false);
            } else if (userChoice == ScissorsRockPaperType.PAPER && botChoice == ScissorsRockPaperType.SCISSORS) {
                embedBuilder.addField("Resultat", "Leider hast du Verloren, Versuch es am besten noch einmal!!", false);
            }
            embedBuilder.setFooter("@Entertainment", e.getJDA().getSelfUser().getAvatarUrl());
            e.replyEmbeds(embedBuilder.build()).queue();
        } else {
            e.reply("ERROR: Deine Antwort muss eins der Folgenden Beispiele sein: Schere, Stein, Papier").queue();
        }
    }
}
