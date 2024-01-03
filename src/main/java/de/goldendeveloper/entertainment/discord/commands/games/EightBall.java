package de.goldendeveloper.entertainment.discord.commands.games;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.interfaces.CommandInterface;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.SearchResult;
import de.goldendeveloper.mysql.entities.Table;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.HashMap;
import java.util.Random;

public class EightBall implements CommandInterface {

    @Override
    public CommandData commandData() {
        return Commands.slash("8ball", "Stelle eine Ja-oder-Nein-Frage und erhalte eine Antwort")
                .addOption(OptionType.STRING, "frage", "Die Frage, die du dem 8Ball stellen möchtest", true);
    }

    @Override
    public void runSlashCommand(SlashCommandInteractionEvent e, DCBot dcBot) {
        String question = e.getOption("frage").getAsString();
        Table table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(MysqlConnection.EightBallTable);
        Row row = table.getRowById(new Random().nextInt(table.countRows()));
        HashMap<String, SearchResult> rowData = row.getData();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(":8ball: " + question);
        eb.setDescription(rowData.get("name").getAsString());
        eb.setColor(0x3333ff);
        e.replyEmbeds(eb.build()).queue();
    }
}
