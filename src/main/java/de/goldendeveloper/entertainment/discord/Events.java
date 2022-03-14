package de.goldendeveloper.entertainment.discord;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.mysql.entities.Table;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.List;

public class Events extends ListenerAdapter {

    public static final String movie = "movie";
    public static final String serien = "serien";
    public static final String jokes = "jokes";
    public static final String games = "games";
    public static final String fact = "facts";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        String cmd = e.getName();
        if (cmd.equalsIgnoreCase(Discord.cmdRandom)) {
            MessageEmbed embed = new EmbedBuilder().setTitle("Wähle aus welches Entertainment Programm du haben möchtest!").build();
            e.getInteraction().replyEmbeds(embed).addActionRow(
                    Button.danger(movie, "Filme"),
                    Button.primary(serien, "Serien"),
                    Button.secondary(jokes, "Jokes"),
                    Button.success(games, "Games"),
                    Button.primary(fact, "Fakten")
            ).queue();
        } else if (cmd.equalsIgnoreCase(Discord.cmdGameStart)) {

        } else if (cmd.equalsIgnoreCase(Discord.cmdHelp)) {
            List<Command> cmds = Main.getDiscord().getBot().retrieveCommands().complete();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("**Help Commands**");
            embed.setColor(Color.MAGENTA);
            embed.setFooter("@_Coho04_#6380", e.getJDA().getSelfUser().getAvatarUrl());
            for (Command cm : cmds) {
                embed.addField("/" + cm.getName(), cm.getDescription(), true);
            }
            e.getInteraction().replyEmbeds(embed.build()).addActionRow(Button.link("https://wiki.coho04.de/bots/discord/", "Online Übersicht"))
                    .addActionRow(Button.link("https://support.coho04.de", "Support Anfragen")).queue();
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
        Main.getMysql().connect();
        Table table = null;
        switch (ID) {
            case movie -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.movieTName);
            case serien -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.serienTName);
            case games -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.gameTName);
            case jokes -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.jokeTName);
            case fact -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.factTName);
        }
        if (table != null) {
            String object = table.getRandomFromColumn("name").toString();
            Main.getMysql().disconnect();
            return object;
        }
        return "";
    }
}
