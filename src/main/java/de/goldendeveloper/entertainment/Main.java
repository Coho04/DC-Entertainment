package de.goldendeveloper.entertainment;

import de.goldendeveloper.dcbcore.DCBot;
import de.goldendeveloper.dcbcore.DCBotBuilder;
import de.goldendeveloper.entertainment.discord.CustomEvents;
import de.goldendeveloper.entertainment.discord.commands.*;
import de.goldendeveloper.entertainment.discord.commands.games.EightBall;
import de.goldendeveloper.entertainment.discord.commands.games.ScissorsRockPaper;
import de.goldendeveloper.entertainment.discord.commands.music.*;
import de.goldendeveloper.entertainment.util.AudioPlayerHelper;
import de.goldendeveloper.mysql.exceptions.NoConnectionException;

import java.sql.SQLException;

public class Main {

    private static MYSQL mysql;
    private static CustomConfig customConfig;
    private static DCBot dcBot;
    private static AudioPlayerHelper audioPlayerHelper;

    public static void main(String[] args) throws NoConnectionException, SQLException {
        customConfig = new CustomConfig();
        DCBotBuilder dcBotBuilder = new DCBotBuilder(args, true);
        dcBotBuilder.registerCommands(
                new Entertainment(),
                new Pause(),
                new Play(),
                new Resume(),
                new ScissorsRockPaper(),
                new Skip(),
                new Stop(),
                new Volume(),
                new YtSearch(),
                new LeaveVoice(),
                new EightBall()
        );
        dcBotBuilder.registerEvents(new CustomEvents());
        dcBot = dcBotBuilder.build();
        audioPlayerHelper = new AudioPlayerHelper();
        mysql = new MYSQL();
    }

    public static MYSQL getMysql() {
        return mysql;
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
