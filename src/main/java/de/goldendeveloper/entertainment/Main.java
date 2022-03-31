package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.discord.Discord;
import de.goldendeveloper.entertainment.util.*;
import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.MysqlTypes;
import de.goldendeveloper.mysql.entities.Table;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static MYSQL mysql;
    public static String dbName = "Discord_Entertainment_Bot";
    public static String serienTName = "Serien";
    public static String movieTName = "Filme";
    public static String gameTName = "Games";
    public static String jokeTName = "Jokes";
    public static String factTName = "Fakt";
    public static String eightBallTName = "EightBall";
    public static String columnName = "Name";
    public static String DiscordID = "DiscordServerID";
    public static String emojiGameChannelID = "EmojiGameChannelID";
    public static String galgenGameChannelID = "GalgenGameChannelID";
    public static String GalgenGameTable = "GalgenGame";
    public static String GameTable = "EmojiGame";
    public static String DiscordTable = "DiscordServer";
    public static String columnGameBegriff = "begriff";
    public static String GameDifficulty = "difficulty";
    public static String GameEmojiOne = "emojione";
    public static String GameHint = "hint";
    public static String GameEmojiTwo = "emojitwo";
    public static String EmojiGameActive = "Emoji";
    public static String GalgenGameActive = "Galgen";

    private static Discord discord;
    private static Config config;

    public static void main(String[] args) {
        config = new Config();
        discord = new Discord(config.getDiscordToken());
        mysqlConnect();
    }

    public static MYSQL getMysql() {
        return mysql;
    }

    public static Discord getDiscord() {
        return discord;
    }

    public static void mysqlConnect() {
        mysql = new MYSQL(config.getMysqlHostname(), config.getMysqlUsername(), config.getMysqlPassword(), config.getMysqlPort());
        if (!mysql.existsDatabase(dbName)) {
            mysql.createDatabase(dbName);
        }
        Database db = mysql.getDatabase(dbName);
        createTables(db, serienTName);
        createTables(db, movieTName);
        createTables(db, jokeTName);
        createTables(db, eightBallTName);
        createTables(db, gameTName);
        createTables(db, factTName);

        if (!db.existsTable(GameTable)) {
            db.createTable(GameTable);
        }
        if (!db.existsTable(DiscordTable)) {
            db.createTable(DiscordTable);
        }
        if (!db.existsTable(GalgenGameTable)) {
            db.createTable(GalgenGameTable);
        }

        new GalgenGame(Main.GalgenGameTable);

        Table table = db.getTable(DiscordTable);
        createGame(table, DiscordID, emojiGameChannelID, galgenGameChannelID, EmojiGameActive, GalgenGameActive);

        table = db.getTable(GameTable);
        createGame(table, GameDifficulty, GameHint, columnGameBegriff, GameEmojiOne, GameEmojiTwo);

        if (table.isEmpty()) {
            new EmojiGame(table);
        }
        System.out.println("MYSQL Fertig");
    }

    public static void createTables(Database db, String tableName) {
        if (!db.existsTable(tableName)) {
            db.createTable(tableName);
        }
        Table table = db.getTable(tableName);
        if (!table.existsColumn("name")) {
            table.addColumn("name", MysqlTypes.VARCHAR, 250);
        }
    }

    public static Config getConfig() {
        return config;
    }

    public static void createGame(Table table, String ColumnOne, String ColumnTwo, String ColumnThree, String ColumnFour, String ColumnFive) {
        List<String> columns = new ArrayList<>();
        columns.add(ColumnOne);
        columns.add(ColumnTwo);
        columns.add(ColumnThree);
        columns.add(ColumnFour);
        columns.add(ColumnFive);

        for (String clm : columns) {
            if (!table.existsColumn(clm)) {
                table.addColumn(clm, MysqlTypes.VARCHAR, 50);
            }
        }
    }
}
