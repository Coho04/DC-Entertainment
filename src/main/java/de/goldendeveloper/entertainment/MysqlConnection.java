package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.util.CountingGame;
import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
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
    public static String GalgenGameTable = "GalgenGame";
    public static String GameTable = "EmojiGame";
    public static String DiscordTable = "DiscordServer";

    public MysqlConnection() {
        mysql = new MYSQL(Main.getConfig().getMysqlHostname(), Main.getConfig().getMysqlUsername(), Main.getConfig().getMysqlPassword(), Main.getConfig().getMysqlPort());
        if (!mysql.existsDatabase(dbName)) {
            mysql.createDatabase(dbName);
        }
        Database db = mysql.getDatabase(dbName);
        createTable(db, serienTName, new String[]{"name"});
        createTable(db, movieTName, new String[]{"name"});
        createTable(db, jokeTName, new String[]{"name"});
        createTable(db, eightBallTName, new String[]{"name"});
        createTable(db, gameTName, new String[]{"name"});
        createTable(db, factTName, new String[]{"name"});


        createTable(db, CountingGame.TableName, new String[]{CountingGame.ChannelColumn, CountingGame.NumberColumn, CountingGame.GuildColumn});

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
        System.out.println("MYSQL Fertig");
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

    private void createTable(Database db, String tableName, String[] columns) {
        if (!db.existsTable(tableName)) {
            db.createTable(tableName);
        }
        Table table = db.getTable(tableName);
        for (String column : columns) {
            if (!table.existsColumn(column)) {
                table.addColumn(column);
            }
        }
    }

    public MYSQL getMysql() {
        return mysql;
    }
}
