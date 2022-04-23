package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.discord.Discord;
import de.goldendeveloper.entertainment.util.GalgenGame;

public class Main {


    private static Discord discord;
    private static Config config;
    private static MysqlConnection mysqlConnection;

    public static void main(String[] args) {
        config = new Config();
        mysqlConnection = new MysqlConnection();
        discord = new Discord(config.getDiscordToken());

        new GalgenGame(MysqlConnection.GalgenGameTable);
    }

    public static Discord getDiscord() {
        return discord;
    }

    public static Config getConfig() {
        return config;
    }

    public static MysqlConnection getMysqlConnection() {
        return mysqlConnection;
    }
}
