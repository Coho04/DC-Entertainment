package de.goldendeveloper.entertainment.discord.commands.games;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.Main;
import io.sentry.Sentry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EightBall implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("8ball", "Stelle eine Ja-oder-Nein-Frage und erhalte eine Antwort")
                .addOption(OptionType.STRING, "frage", "Die Frage, die du dem 8Ball stellen möchtest", true);
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        String question = e.getOption("frage").getAsString();
        try (Connection connection = Main.getMysql().getSource().getConnection()) {
            String selectQuery = "SELECT name FROM eightball group by name order by rand() limit 1;";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.execute("USE `GD-Entertainment`");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(":8ball: " + question);
                eb.setDescription(rs.getString("name"));
                eb.setColor(0x3333ff);
                e.replyEmbeds(eb.build()).queue();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            Sentry.captureException(exception);
        }
    }
}
