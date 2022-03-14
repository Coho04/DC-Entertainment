package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.discord.Discord;
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

    public static String GameTable = "EmojiGame";
    public static String columnGameBegriff = "begriff";
    public static String GameDifficulty = "difficulty";
    public static String GameEmojiOne = "emojione";
    public static String GameEmojiTwo = "emojitwo";

    public static Discord discord;

    public static void main(String[] args) {
        discord = new Discord(ID.token);
        mysqlConnect();
    }

    public static MYSQL getMysql() {
        return mysql;
    }

    public static Discord getDiscord() {
        return discord;
    }

    public static void mysqlConnect() {
        mysql = new MYSQL(ID.hostname, ID.username, ID.password, 3306);
        mysql.connect();
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
        Table table = db.getTable(GameTable);
        if (!table.existsColumn(GameDifficulty)) {
            table.addColumn(GameDifficulty, MysqlTypes.VARCHAR, 50);
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
            FillTableIfisEmpty.fillEmojiGame(table);
        }
        table = db.getTable(gameTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillGameTable(table);
        }
        table = db.getTable(jokeTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillJokeTable(table);
        }
        table = db.getTable(movieTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillMovieTable(table);
        }
        table = db.getTable(serienTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillSerienTable(table);
        }
        table = db.getTable(factTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillFactTable(table);
        }
        mysql.disconnect();
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
}
