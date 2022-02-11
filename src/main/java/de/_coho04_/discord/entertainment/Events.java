package de._coho04_.discord.entertainment;

import de._Coho04_.mysql.entities.Table;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

public class Events extends ListenerAdapter {

    public static final String movie = "movie";
    public static final String serien = "serien";
    public static final String jokes = "jokes";
    public static final String games = "games";

    @Override
    public void onSlashCommand(SlashCommandEvent e) {
        String cmd = e.getName();
        if (cmd.equalsIgnoreCase("random")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Wähle aus welches Entertainment Programm du haben möchtest!")
                    .build();
            e.getInteraction().replyEmbeds(embed).addActionRow(
                    Button.danger(movie, "Filme"),
                    Button.primary(serien, "Serien"),
                    Button.secondary(jokes, "Jokes"),
                    Button.success(games, "Games")
            ).queue();
        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent e) {
        String id = e.getButton().getId();
        if (id != null) {
            if (id.equalsIgnoreCase(serien)) {
                e.getInteraction().reply("Wir empfehlen dir die Serie [" + getItem(serien) + "]!").queue();
            } else if (id.equalsIgnoreCase(movie)) {
                e.getInteraction().reply("Wir empfehlen dir den Film [" + getItem(movie) + "]!").queue();
            } else if (id.equalsIgnoreCase(games)) {
                e.getInteraction().reply("Wir empfehlen dir das Game [" + getItem(games) + "]!").queue();
            } else if (id.equalsIgnoreCase(jokes)) {
                e.getInteraction().reply(getItem(jokes)).queue();
            }
        }
    }

    public static String getItem(String ID) {
        Table table = null;
        switch (ID) {
            case movie -> table = Main.mysql.getDatabase(Main.dbName).getTable(Main.movieTName);
            case serien -> table = Main.mysql.getDatabase(Main.dbName).getTable(Main.serienTName);
            case games -> table = Main.mysql.getDatabase(Main.dbName).getTable(Main.gameTName);
            case jokes -> table = Main.mysql.getDatabase(Main.dbName).getTable(Main.jokeTName);
        }
        if (table != null) {
            return table.getRandomFromColumn("name").toString();
        }
        return "";
    }
}
