package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.util.EmojiGame;
import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.MysqlTypes;
import de.goldendeveloper.mysql.entities.Table;

import java.util.ArrayList;
import java.util.List;

public class MysqlConnection {

    private final MYSQL mysql;
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
    public static String GameErrors = "Errors";
    public static String GameEmojiOne = "emojione";
    public static String GameHint = "hint";
    public static String GalgenBuchstaben = "galgenbuchstaben";
    public static String GameEmojiTwo = "emojitwo";
    public static String EmojiGameActive = "Emoji";
    public static String GalgenGameActive = "Galgen";

    public MysqlConnection() {
        mysql = new MYSQL(Main.getConfig().getMysqlHostname(), Main.getConfig().getMysqlUsername(), Main.getConfig().getMysqlPassword(), Main.getConfig().getMysqlPort());
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
        } else {
            db.getTable(GalgenGameTable).drop();
            db.createTable(GalgenGameTable);
        }

        Table table = db.getTable(DiscordTable);
        createGame(table, DiscordID, emojiGameChannelID, galgenGameChannelID, EmojiGameActive, GalgenGameActive);

        table = db.getTable(GameTable);
        createGame(table, GameDifficulty, GameHint, columnGameBegriff, GameEmojiOne, GameEmojiTwo);

        if (table.isEmpty()) {
            new EmojiGame(table);
        }
        System.out.println("MYSQL Fertig");
    }

    private void createTables(Database db, String tableName) {
        if (!db.existsTable(tableName)) {
            db.createTable(tableName);
        }
        Table table = db.getTable(tableName);
        if (!table.existsColumn("name")) {
            table.addColumn("name");
        }
    }

    private void createGame(Table table, String ColumnOne, String ColumnTwo, String ColumnThree, String ColumnFour, String ColumnFive) {
        List<String> columns = new ArrayList<>();
        columns.add(ColumnOne);
        columns.add(ColumnTwo);
        columns.add(ColumnThree);
        columns.add(ColumnFour);
        columns.add(ColumnFive);

        for (String clm : columns) {
            if (!table.existsColumn(clm)) {
                table.addColumn(clm);
            }
        }
    }

    public MYSQL getMysql() {
        return mysql;
    }
}
