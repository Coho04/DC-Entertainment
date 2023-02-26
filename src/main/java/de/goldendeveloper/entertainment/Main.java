package de.goldendeveloper.entertainment;

import de.goldendeveloper.entertainment.discord.Discord;
import io.sentry.Sentry;

public class Main {


    private static Discord discord;
    private static Config config;
    private static MysqlConnection mysqlConnection;
    private static ServerCommunicator serverCommunicator;

    private static Boolean restart = false;
    private static Boolean deployment = true;

    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("restart")) {
            restart = true;
        }
        if (System.getProperty("os.name").split(" ")[0].equalsIgnoreCase("windows")) {
            deployment = false;
        }
        config = new Config();
        Sentry(config.getSentryDNS());
        serverCommunicator = new ServerCommunicator(config.getServerHostname(), config.getServerPort());
        mysqlConnection = new MysqlConnection();
        discord = new Discord(config.getDiscordToken());
    }

    public static void Sentry(String dns) {
        Sentry.init(options -> {
            options.setDsn(dns);
            options.setTracesSampleRate(1.0);
        });
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

    public static Boolean getDeployment() {
        return deployment;
    }

    public static Boolean getRestart() {
        return restart;
    }
}
