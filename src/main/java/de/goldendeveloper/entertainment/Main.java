package de.goldendeveloper.entertainment;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.DCBotBuilder;
import de.goldendeveloper.entertainment.discord.CustomEvents;
import de.goldendeveloper.entertainment.discord.commands.*;
import de.goldendeveloper.entertainment.util.AudioPlayerHelper;

public class Main {

    private static MysqlConnection mysqlConnection;
    private static CustomConfig customConfig;
    private static DCBot dcBot;
    private static AudioPlayerHelper audioPlayerHelper;


    public static void main(String[] args) {
        customConfig = new CustomConfig();
        DCBotBuilder dcBotBuilder = new DCBotBuilder(args, true);
        dcBotBuilder.registerCommands(
                new CountingGame(),
                new DeleteCountingGame(),
                new Entertainment(),
                new Pause(),
                new Play(),
                new Resume(),
                new ScissorsRockPaper(),
                new Skip(),
                new Stop(),
                new Volume(),
                new YtSearch()
        );
        dcBotBuilder.registerEvents(new CustomEvents());
        dcBot = dcBotBuilder.build();
        audioPlayerHelper = new AudioPlayerHelper();
        mysqlConnection = new MysqlConnection(customConfig.getMysqlHostname(), customConfig.getMysqlUsername(), customConfig.getMysqlPassword(), customConfig.getMysqlPort());
    }

    public static MysqlConnection getMysqlConnection() {
        return mysqlConnection;
    }

    public static CustomConfig getCustomConfig() {
        return customConfig;
    }

    public static DCBot getDcBot() {
        return dcBot;
    }

    public static AudioPlayerHelper getAudioPlayerHelper() {
        return audioPlayerHelper;
    }
}
