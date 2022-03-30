package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.discord.Discord;
import de.goldendeveloper.entertainment.util.*;
import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.MysqlTypes;
import de.goldendeveloper.mysql.entities.Table;

public class Main {

    private static MYSQL mysql;
    public static String dbName = "Discord_Entertainment_Bot";
    public static String serienTName = "Serien";
    public static String movieTName = "Filme";
    public static String gameTName = "Games";
    public static String jokeTName = "Jokes";
    public static String factTName = "Fakt";
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
        if (!table.hasColumn(DiscordID)) {
            table.addColumn(DiscordID, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(emojiGameChannelID)) {
            table.addColumn(emojiGameChannelID, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(galgenGameChannelID)) {
            table.addColumn(galgenGameChannelID, MysqlTypes.VARCHAR, 50);
        }

        if (!table.existsColumn(EmojiGameActive)) {
            table.addColumn(EmojiGameActive, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(GalgenGameActive)) {
            table.addColumn(GalgenGameActive, MysqlTypes.VARCHAR, 50);
        }

        table = db.getTable(GameTable);
        if (!table.existsColumn(GameDifficulty)) {
            table.addColumn(GameDifficulty, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(GameHint)) {
            table.addColumn(GameHint, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(columnGameBegriff)) {
            table.addColumn(columnGameBegriff, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(GameEmojiOne)) {
            table.addColumn(GameEmojiOne, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(GameEmojiTwo)) {
            table.addColumn(GameEmojiTwo, MysqlTypes.VARCHAR, 50);
        }
        if (table.isEmpty()) {
            new EmojiGame(table);
        }
        table = db.getTable(gameTName);
        if (table.isEmpty()) {
            new Game(table);
        }
        table = db.getTable(jokeTName);
        if (table.isEmpty()) {
            new Joke(table);
        }
        table = db.getTable(movieTName);
        if (table.isEmpty()) {
            new Movie(table);
        }
        table = db.getTable(serienTName);
        if (table.isEmpty()) {
            new Serie(table);
        }
        table = db.getTable(factTName);
        if (table.isEmpty()) {
            new Fact(table);
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
}
