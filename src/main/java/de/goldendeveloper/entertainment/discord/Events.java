package de.goldendeveloper.entertainment.discord;

import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.mysql.entities.Column;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Events extends ListenerAdapter {

    public static final String movie = "movie";
    public static final String serien = "serien";
    public static final String jokes = "jokes";
    public static final String games = "games";
    public static final String fact = "facts";
    public static final String skip = "skip";
    public static final String firstLetter = "firstLetter";

    @Override
    public void onShutdown(@NotNull ShutdownEvent e) {
        WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
        embed.setAuthor(new WebhookEmbed.EmbedAuthor(Main.getDiscord().getBot().getSelfUser().getName(), Main.getDiscord().getBot().getSelfUser().getAvatarUrl(), "https://Golden-Developer.de"));
        embed.addField(new WebhookEmbed.EmbedField(false, "[Status]", "OFFLINE"));
        embed.setColor(0xFF0000);
        embed.setFooter(new WebhookEmbed.EmbedFooter("@Golden-Developer",  Main.getDiscord().getBot().getSelfUser().getAvatarUrl()));
        new WebhookClientBuilder(Main.getConfig().getDiscordWebhook()).build().send(embed.build());
    }

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
            ).addActionRow(
                    Button.link("https://www.youtube.com/watch?v=dQw4w9WgXcQ", "P*rno")
            ).queue();
        } else if (cmd.equalsIgnoreCase(Discord.cmdEmojiStart)) {
            if (e.isFromGuild()) {
                if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (Main.getMysql().existsDatabase(Main.dbName)) {
                        Database db = Main.getMysql().getDatabase(Main.dbName);
                        if (db.existsTable(Main.DiscordTable)) {
                            Table table = db.getTable(Main.DiscordTable);
                            if (table.existsColumn(Main.DiscordID)) {
                                Column column = table.getColumn(Main.DiscordID);
                                if (column.getAll().contains(e.getGuild().getId())) {
                                    HashMap<String, Object> map = table.getRow(table.getColumn(Main.DiscordID), e.getGuild().getId());
                                    if (map.containsKey(Main.emojiGameChannelID)) {
                                        String Channel = map.get(Main.emojiGameChannelID).toString();
                                        if (!Channel.isEmpty() && !Channel.isBlank()) {
                                            TextChannel channel = e.getGuild().getTextChannelById(Channel);
                                            if (channel != null) {
                                                channel.sendMessageEmbeds(Objects.requireNonNull(EmojiEmbed())).setActionRows(
                                                        ActionRow.of(
                                                                Button.danger(skip, "Überspringen"),
                                                                Button.primary(firstLetter, "Erster Buchstabe")
                                                        )
                                                ).queue();
                                            }
                                        }
                                    }
                                } else {
                                    Guild guild = e.getGuild();
                                    guild.createTextChannel("emoji-quiz").queue(channel -> {
                                        table.insert(new Row(table, table.getDatabase())
                                                .with(Main.DiscordID, e.getGuild().getId())
                                                .with(Main.emojiGameChannelID, channel.getId())
                                        );
                                    });
                                }
                            }
                        }
                    }
                }
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdGalgenStart)) {
            if (e.isFromGuild()) {
                if (e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (Main.getMysql().existsDatabase(Main.dbName)) {
                        Database db = Main.getMysql().getDatabase(Main.dbName);
                        if (db.existsTable(Main.DiscordTable)) {
                            Table table = db.getTable(Main.DiscordTable);
                            if (table.existsColumn(Main.DiscordID)) {
                                Column column = table.getColumn(Main.DiscordID);
                                if (column.getAll().contains(e.getGuild().getId())) {
                                    HashMap<String, Object> map = table.getRow(table.getColumn(Main.DiscordID), e.getGuild().getId());
                                    if (map.containsKey(Main.emojiGameChannelID)) {
                                        String Channel = map.get(Main.emojiGameChannelID).toString();
                                        if (!Channel.isEmpty() && !Channel.isBlank()) {
                                            TextChannel channel = e.getGuild().getTextChannelById(Channel);
                                            if (channel != null) {
                                                channel.sendMessageEmbeds(EmojiEmbed()).setActionRows(
                                                        ActionRow.of(
                                                                Button.danger(skip, "Überspringen"),
                                                                Button.primary(firstLetter, "Erster Buchstabe")
                                                        )
                                                ).queue();
                                            }
                                        }
                                    }
                                } else {
                                    Guild guild = e.getGuild();
                                    guild.createTextChannel("emoji-quiz").queue(channel -> {
                                        table.insert(new Row(table, table.getDatabase())
                                                .with(Main.DiscordID, e.getGuild().getId())
                                                .with(Main.emojiGameChannelID, channel.getId())
                                        );
                                    });
                                }
                            }
                        }
                    }
                }
            }
        } else if (cmd.equalsIgnoreCase(Discord.cmdHelp)) {
            List<Command> commands = Main.getDiscord().getBot().retrieveCommands().complete();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("**Help Commands**");
            embed.setColor(Color.MAGENTA);
            embed.setFooter("@Golden-Developer", e.getJDA().getSelfUser().getAvatarUrl());
            for (Command cm : commands) {
                embed.addField("/" + cm.getName(), cm.getDescription(), true);
            }
            e.getInteraction().replyEmbeds(embed.build()).addActionRow(
                    Button.link("https://wiki.coho04.de/bots/discord/", "Online Übersicht"),
                    Button.link("https://support.coho04.de", "Support Anfragen")
            ).queue();
        } else if (cmd.equalsIgnoreCase("test")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription(
                    "-------------\n" +
                    "|/................ \\|\n" +
                    "|.................. |\n" +
                    "|.................. O\n" +
                    "|.................. |/\n" +
                    "|.................. |\\\n" +
                    "|................../\\\n" +
                    "-----------------------");
            e.getInteraction().deferReply(true).queue();
            e.getTextChannel().sendMessageEmbeds(builder.build()).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        String button = e.getButton().getId();
        if (button != null) {
            if (button.equalsIgnoreCase(serien)) {
                e.getInteraction().reply("Wir empfehlen dir die Serie [" + getItem(serien) + "]!").queue();
            } else if (button.equalsIgnoreCase(movie)) {
                e.getInteraction().reply("Wir empfehlen dir den Film [" + getItem(movie) + "]!").queue();
            } else if (button.equalsIgnoreCase(games)) {
                e.getInteraction().reply("Wir empfehlen dir das Game [" + getItem(games) + "]!").queue();
            } else if (button.equalsIgnoreCase(fact)) {
                e.getInteraction().reply(getItem(fact)).queue();
            } else if (button.equalsIgnoreCase(jokes)) {
                e.getInteraction().reply(getItem(jokes)).queue();
            } else if (button.equalsIgnoreCase(skip)) {
                Message msg = e.getChannel().getHistory().getMessageById(e.getChannel().getLatestMessageId());
                if (msg != null) {
                    msg.editMessageEmbeds(EmojiEmbed()).queue();
                }
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromGuild()) {
            if (Main.getMysql().existsDatabase(Main.dbName)) {
                Database db = Main.getMysql().getDatabase(Main.dbName);
                if (db.existsTable(Main.DiscordTable)) {
                    Table table = db.getTable(Main.DiscordTable);
                    if (table.existsColumn(Main.DiscordID)) {
                        if (table.getColumn(Main.DiscordID).getAll().contains(e.getGuild().getId())) {
                            if (table.existsColumn(Main.galgenGameChannelID)) {
                                if (table.getColumn(Main.galgenGameChannelID).getAll().contains(e.getTextChannel().getId())) {
                                    HashMap<String, Object> row = table.getRow(table.getColumn(Main.DiscordID), e.getGuild().getId());
                                    if (row.containsKey(Main.galgenGameChannelID)) {
                                        if (row.get(Main.galgenGameChannelID).toString().equalsIgnoreCase(e.getTextChannel().getId())) {
                                            TextChannel channel = e.getTextChannel();
                                            Message message = channel.getHistory().getMessageById(channel.getLatestMessageId());
                                            if (message != null) {
                                                if (!message.getEmbeds().isEmpty()) {
                                                    for (MessageEmbed embed : message.getEmbeds()) {

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /*if (e.isFromGuild()) {
            if (Main.getMysql().existsDatabase(Main.dbName)) {
                Database db = Main.getMysql().getDatabase(Main.dbName);
                if (db.existsTable(Main.DiscordTable)) {
                    Table table = db.getTable(Main.DiscordTable);
                    if (table.existsColumn(Main.DiscordID)) {
                        if (table.getColumn(Main.DiscordID).getAll().contains(e.getGuild().getId())) {
                            HashMap<String, Object> map = table.getRow(table.getColumn(Main.DiscordID), e.getGuild().getId());
                            if (e.getChannel().getId().equalsIgnoreCase(map.get(Main.ChannelID).toString())) {
                                //if (e.getMessage().getContentRaw().equalsIgnoreCase("")) {
                                TextChannel channel = e.getTextChannel();
                                Message message = channel.getHistory().getMessageById(channel.getLatestMessageId());
                                e.getMessage().delete().queue();
                                if (message != null) {
                                    message.editMessageEmbeds(EmojiEmbed()).queue();
                                }
                                //}
                            }
                        }
                    }
                }
            }
        }*/
    }

    public static String getItem(String ID) {
        Table table = null;
        switch (ID) {
            case movie -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.movieTName);
            case serien -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.serienTName);
            case games -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.gameTName);
            case jokes -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.jokeTName);
            case fact -> table = Main.getMysql().getDatabase(Main.dbName).getTable(Main.factTName);
        }
        if (table != null) {
            String object = table.getRandomFromColumn(Main.columnName).toString();
            return object;
        }
        return "";
    }

    public static MessageEmbed EmojiEmbed() {
        if (Main.getMysql().existsDatabase(Main.dbName)) {
            Database db = Main.getMysql().getDatabase(Main.dbName);
            if (db.existsTable(Main.GameTable)) {
                Table table = db.getTable(Main.GameTable);
                if (table.existsColumn("id")) {
                    Column id = table.getColumn("id");
                    HashMap<String, Object> row = table.getRow(id, Integer.toString(new Random().nextInt(id.getAll().size())));
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Emoji Quiz");
                    builder.addField("", "Gesuchter Begriff: " + row.get(Main.GameEmojiOne).toString() + " " + row.get(Main.GameEmojiTwo).toString(), true);
                    builder.addField("Schwierigkeit: ", row.get(Main.GameDifficulty).toString(), true);
                    builder.addField("Tipp: ", row.get(Main.GameHint).toString(), true);
                    builder.setFooter("» Dir fällt der Begriff nicht ein? Nutze den Überspringen-Button, um das Quiz zu überspringen.");
                    return builder.build();
                }
            }
        }
        return null;
    }
}
