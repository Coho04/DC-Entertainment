package de._coho04_.discord.entertainment;

import de._Coho04_.mysql.MYSQL;
import de._Coho04_.mysql.entities.Database;
import de._Coho04_.mysql.entities.MysqlTypes;
import de._Coho04_.mysql.entities.Table;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA bot;
    public static MYSQL mysql;
    public static String dbName = "Discord_Entertainment_Bot";
    public static String serienTName = "Serien";
    public static String movieTName = "Filme";
    public static String gameTName = "Games";
    public static String jokeTName = "Jokes";

    public static void main(String[] args) {
        BotCreate();
        mysqlConnect();
    }

    public static void BotCreate() {
        try {
            bot = JDABuilder.createDefault(ID.token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.ROLE_TAGS, CacheFlag.EMOTE, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_EMOJIS,
                            GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_PRESENCES,
                            GatewayIntent.GUILD_BANS, GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_INVITES, GatewayIntent.DIRECT_MESSAGE_TYPING,
                            GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.GUILD_WEBHOOKS, GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGE_TYPING)
                    .addEventListeners(
                            new Events()
                    )
                    .setAutoReconnect(true)
                    .build();
            bot.upsertCommand("random", "Wähle aus welches Entertainment du haben möchtest!").queue();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static void mysqlConnect() {
        mysql = new MYSQL(ID.hostname, ID.username, ID.password, 3306);

        if (!mysql.existsDatabase(dbName)) {
            mysql.createDatabase(dbName);
        }
        Database db = mysql.getDatabase(dbName);

        createTables(db, serienTName);
        createTables(db, movieTName);
        createTables(db, jokeTName);
        createTables(db, gameTName);

        Table table = db.getTable(gameTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillGameTable(db, table);
        }
        table = db.getTable(jokeTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillJokeTable(db, table);
        }
        table = db.getTable(movieTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillMovieTable(db, table);
        }
        table = db.getTable(serienTName);
        if (table.isEmpty()) {
            FillTableIfisEmpty.fillSerienTable(db, table);
        }
        System.out.println("MYSQL Fertig");
    }

    public static void createTables(Database db, String tableName) {
        if (!db.existsTable(tableName)) {
            db.createTable(tableName, "name", MysqlTypes.VARCHAR, 250);
        }
        Table table = db.getTable(tableName);
        if (!table.existsColumn("name")) {
            table.addColumn("name", MysqlTypes.VARCHAR, 250);
        }
    }
}
