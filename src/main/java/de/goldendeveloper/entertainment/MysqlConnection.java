package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.errors.ExceptionHandler;
import de.goldendeveloper.entertainment.util.CountingGame;
import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.Table;

public class MysqlConnection {

    private final MYSQL mysql;
    public static String dbName = "GD-Entertainment";
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

    public MysqlConnection(String hostname,String username,String password,int port) {
        mysql = new MYSQL(hostname, username, password, port, new ExceptionHandler());
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
