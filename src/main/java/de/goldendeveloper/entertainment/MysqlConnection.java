package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.enums.EntertainmentType;
import de.goldendeveloper.entertainment.errors.CustomExceptionHandler;
import de.goldendeveloper.entertainment.util.helpers.games.CountingGameHelper;
import de.goldendeveloper.mysql.MYSQL;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.Table;
import de.goldendeveloper.mysql.exceptions.NoConnectionException;

import java.sql.SQLException;
import java.util.Arrays;

public class MysqlConnection {

    private final MYSQL mysql;
    public static String dbName = "GD-Entertainment";
    public static String columnName = "Name";
    public static String GalgenGameTable = "GalgenGame";
    public static String GameTable = "EmojiGame";
    public static String EightBallTable = "EightBall";
    public static String DiscordTable = "DiscordServer";

    public MysqlConnection(String hostname, String username, String password, int port) throws NoConnectionException, SQLException {
        mysql = new MYSQL(hostname, username, password, port, new CustomExceptionHandler());
        if (!mysql.existsDatabase(dbName)) {
            mysql.createDatabase(dbName);
        }
        Database db = mysql.getDatabase(dbName);
        EntertainmentType.getAllEntertainmentTypes().forEach(
                type -> createTable(db, type.getName(), new String[]{"name"})
        );
        createTable(db, CountingGameHelper.tableName, new String[]{CountingGameHelper.channelColumn, CountingGameHelper.numberColumn, CountingGameHelper.guildColumn});

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
        Arrays.stream(columns).forEach(column -> {
            if (!table.existsColumn(column))
                table.addColumn(column);
        });
    }

    public MYSQL getMysql() {
        return mysql;
    }
}
