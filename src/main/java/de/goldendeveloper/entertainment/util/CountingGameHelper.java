package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.mysql.entities.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import javax.annotation.Nullable;
import java.util.HashMap;

public class CountingGameHelper {

    private String guildId;
    private String textChannelId;
    private int currentNumber;

    public static final String tableName = "CountingGame";
    public static final String guildColumn = "Guild";
    public static final String channelColumn = "Channel";
    public static final String numberColumn = "currentNumber";

    // Guild | Channel | Zahl
    public CountingGameHelper(Guild guild, TextChannel channel, int currentNumber) {
        this.currentNumber = currentNumber;
        this.guildId = guild.getId();
        this.textChannelId = channel.getId();
    }

    public static CountingGameHelper create(String textChannelName, Guild guild) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(tableName)) {
                Table table = db.getTable(tableName);
                TextChannel textChannel = guild.createTextChannel(textChannelName).complete();
                table.insert(
                        new RowBuilder()
                                .with(table.getColumn(channelColumn), textChannel.getId())
                                .with(table.getColumn(guildColumn), guild.getId())
                                .with(table.getColumn(numberColumn), 0)
                                .build()
                );
                return new CountingGameHelper(guild, textChannel, 0);
            }
        }
        return null;
    }

    @Nullable
    public static CountingGameHelper findGame(Guild guild) {
        Table table = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName).getTable(tableName);
        if (table.getColumn(guildColumn).getAll().getAsString().contains(guild.getId())) {
            HashMap<String, SearchResult> result = table.getRow(table.getColumn(guildColumn), guild.getId()).getData();
            int num = result.get(numberColumn).getAsInt();
            TextChannel channel = Main.getDcBot().getDiscord().getBot().getTextChannelById(result.get(channelColumn).getAsString());
            if (channel != null) {
                return new CountingGameHelper(guild, channel, num);
            }
        }
        return null;
    }

    public void delete() {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(tableName)) {
                Table table = db.getTable(tableName);
                if (table.getColumn(guildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(guildColumn), guildId).drop();
                }
            }
        }
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public TextChannel getTextChannel() {
        return Main.getDcBot().getDiscord().getBot().getTextChannelById(textChannelId);
    }

    public Guild getGuildId() {
        return Main.getDcBot().getDiscord().getBot().getGuildById(guildId);
    }

    public void setGuild(Guild guild) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(tableName)) {
                Table table = db.getTable(tableName);
                if (table.getColumn(guildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(guildColumn), guildId).set(table.getColumn(guildColumn), guild.getId());
                    this.guildId = guild.getId();
                }
            }
        }
    }

    public void setCurrentNumber(int currentNumber) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(tableName)) {
                Table table = db.getTable(tableName);
                if (table.getColumn(guildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(guildColumn), guildId).set(table.getColumn(numberColumn), String.valueOf(currentNumber));
                    this.currentNumber = currentNumber;
                }
            }
        }
    }

    public void setTextChannel(TextChannel textChannel) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(tableName)) {
                Table table = db.getTable(tableName);
                if (table.getColumn(guildColumn).getAll().getAsString().contains(guildId)) {
                    table.getRow(table.getColumn(guildColumn), guildId).set(table.getColumn(guildColumn), textChannel.getId());
                    this.textChannelId = textChannel.getId();
                }
            }
        }
    }

    public static boolean hasCountingGame(Guild guild) {
        if (Main.getMysqlConnection().getMysql().existsDatabase(MysqlConnection.dbName)) {
            Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
            if (db.existsTable(tableName)) {
                Table table = db.getTable(tableName);
                return table.getColumn(guildColumn).getAll().getAsString().contains(guild.getId());
            }
        }
        return false;
    }
}
