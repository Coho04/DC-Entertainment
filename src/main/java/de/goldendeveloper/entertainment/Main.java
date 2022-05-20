package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.discord.Discord;
import de.goldendeveloper.entertainment.util.GalgenGame;

public class Main {


    private static Discord discord;
    private static Config config;
    private static MysqlConnection mysqlConnection;
    private static ServerCommunicator serverCommunicator;

    public static void main(String[] args) {
        config = new Config();
        serverCommunicator = new ServerCommunicator(config.getServerHostname(), config.getServerPort());
        mysqlConnection = new MysqlConnection();
        discord = new Discord(config.getDiscordToken());

        new GalgenGame(MysqlConnection.GalgenGameTable);
        System.out.println("MYSQL Finished");
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

    public static ServerCommunicator getServerCommunicator() {
        return serverCommunicator;
    }
}
