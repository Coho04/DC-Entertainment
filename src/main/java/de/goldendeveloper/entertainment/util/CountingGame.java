package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.mysql.entities.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;


import javax.annotation.Nullable;
import java.util.HashMap;

public class CountingGame {

    private String guildId;
    private String textChannelId;
    private int currentNumber;

    public static final String TableName = "CountingGame";
    public static final String GuildColumn = "Guild";
    public static final String ChannelColumn = "Channel";
    public static final String NumberColumn = "currentNumber";

    // Guild | Channel | Zahl
    public CountingGame(Guild guild, TextChannel channel, int currentNumber) {
        this.currentNumber = currentNumber;
        this.guildId = guild.getId();
        this.textChannelId = channel.getId();
    }

    public static CountingGame create(String textChannelName, Guild guild) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(TableName)) {
                Table table = db.getTable(TableName);
                TextChannel textChannel = guild.createTextChannel(textChannelName).complete();
                table.insert(
                        new RowBuilder()
                                .with(table.getColumn(ChannelColumn), textChannel.getId())
                                .with(table.getColumn(GuildColumn), guild.getId())
                                .with(table.getColumn(NumberColumn), 0)
                                .build()
                );
                return new CountingGame(guild, textChannel, 0);
            }
        }
        return null;
    }

    @Nullable
    public static CountingGame findGame(Guild guild) {
        Table table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(TableName);
        if (table.getColumn(GuildColumn).getAll().getAsString().contains(guild.getId())) {
            HashMap<String, SearchResult> result = table.getRow(table.getColumn(GuildColumn), guild.getId()).getData();
            int num = result.get(NumberColumn).getAsInt();
            TextChannel channel = Main.getDiscord().getBot().getTextChannelById(result.get(ChannelColumn).getAsString());
            if (channel != null) {
                return new CountingGame(guild, channel, num);
            }
        }
        return null;
    }

    public void delete() {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(TableName)) {
                Table table = db.getTable(TableName);
                if (table.getColumn(GuildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(GuildColumn), guildId).drop();
                }
            }
        }
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public TextChannel getTextChannel() {
        return Main.getDiscord().getBot().getTextChannelById(textChannelId);
    }

    public Guild getGuildId() {
        return Main.getDiscord().getBot().getGuildById(guildId);
    }

    public void setGuild(Guild guild) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(TableName)) {
                Table table = db.getTable(TableName);
                if (table.getColumn(GuildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(GuildColumn), guildId).set(table.getColumn(GuildColumn), guild.getId());
                    this.guildId = guild.getId();
                }
            }
        }
    }

    public void setCurrentNumber(int currentNumber) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(TableName)) {
                Table table = db.getTable(TableName);
                if (table.getColumn(GuildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(GuildColumn), guildId).set(table.getColumn(NumberColumn), String.valueOf(currentNumber));
                    this.currentNumber = currentNumber;
                }
            }
        }
    }

    public void setTextChannel(TextChannel textChannel) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(TableName)) {
                Table table = db.getTable(TableName);
                if (table.getColumn(GuildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(GuildColumn), guildId).set(table.getColumn(GuildColumn), textChannel.getId());
                    this.textChannelId = textChannel.getId();
                }
            }
        }
    }

    public static boolean hasCountingGame(Guild guild) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(TableName)) {
                Table table = db.getTable(TableName);
                if (table.getColumn(GuildColumn).getAll().getAsString().contains(guild.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
