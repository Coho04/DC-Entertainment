package de._coho04_.discord.entertainment;

import de._Coho04_.mysql.entities.Table;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class Events extends ListenerAdapter {

    public static final String movie = "movie";
    public static final String serien = "serien";
    public static final String jokes = "jokes";
    public static final String games = "games";
    public static final String fact = "facts";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        String cmd = e.getName();
        if (cmd.equalsIgnoreCase("random")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Wähle aus welches Entertainment Programm du haben möchtest!")
                    .build();
            e.getInteraction().replyEmbeds(embed).addActionRow(
                    Button.danger(movie, "Filme"),
                    Button.primary(serien, "Serien"),
                    Button.secondary(jokes, "Jokes"),
                    Button.success(games, "Games"),
                    Button.primary(fact, "Fakten")
            ).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        String id = e.getButton().getId();
        if (id != null) {
            if (id.equalsIgnoreCase(serien)) {
                e.getInteraction().reply("Wir empfehlen dir die Serie [" + getItem(serien) + "]!").queue();
            } else if (id.equalsIgnoreCase(movie)) {
                e.getInteraction().reply("Wir empfehlen dir den Film [" + getItem(movie) + "]!").queue();
            } else if (id.equalsIgnoreCase(games)) {
                e.getInteraction().reply("Wir empfehlen dir das Game [" + getItem(games) + "]!").queue();
            } else if (id.equalsIgnoreCase(fact)) {
                e.getInteraction().reply(getItem(fact)).queue();
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
            case fact -> table = Main.mysql.getDatabase(Main.dbName).getTable(Main.factTName);
        }
        if (table != null) {
            return table.getRandomFromColumn("name").toString();
        }
        return "";
    }
}
