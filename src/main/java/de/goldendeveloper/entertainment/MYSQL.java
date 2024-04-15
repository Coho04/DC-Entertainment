package de.goldendeveloper.entertainment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.sentry.Sentry;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

public class MYSQL {

    public static String dbName = "GD-Entertainment";
    public static String columnName = "Name";
    public static String EightBallTable = "EightBall";

    private final HikariDataSource source;

    public MYSQL() {
        this.source = getConfig();
        try {
            Statement statement = this.source.getConnection().createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS `GD-Entertainment`;");
            statement.execute("USE `GD-Entertainment`;");
            statement.execute("CREATE TABLE IF NOT EXISTS movie (id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, name VARCHAR(255) NULL);");
            statement.execute("CREATE TABLE IF NOT EXISTS series(id INT AUTO_INCREMENT NOT NULL  PRIMARY KEY, name VARCHAR(255) NULL);");
            statement.execute("CREATE TABLE IF NOT EXISTS games(id INT AUTO_INCREMENT NOT NULL  PRIMARY KEY, name VARCHAR(255) NULL);");
            statement.execute("CREATE TABLE IF NOT EXISTS jokes(id INT AUTO_INCREMENT NOT NULL  PRIMARY KEY, name VARCHAR(255) NULL);");
            statement.execute("CREATE TABLE IF NOT EXISTS facts(id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, name VARCHAR(255) NULL);");
            statement.execute("CREATE TABLE IF NOT EXISTS eightball(id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, name VARCHAR(255) NULL);");
            statement.execute("CREATE TABLE IF NOT EXISTS counting_game(id INT AUTO_INCREMENT PRIMARY KEY, channel_id LONG NOT NULL, current_number INT NULL, guild_id LONG NOT NULL);");
            statement.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            Sentry.captureException(exception);
        }
        System.out.println("[MYSQL] Initialized MySQL!");
    }

    private static @NotNull HikariDataSource getConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + Main.getCustomConfig().getMysqlHostname() + ":" + Main.getCustomConfig().getMysqlPort());
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(30));
        config.setIdleTimeout(TimeUnit.MINUTES.toMillis(10));
        config.setMaxLifetime(TimeUnit.MINUTES.toMillis(30));
        config.setInitializationFailTimeout(0);
        config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        config.setUsername(Main.getCustomConfig().getMysqlUsername());
        config.setPassword(Main.getCustomConfig().getMysqlPassword());
        config.setConnectionTestQuery("SELECT 1");
        return new HikariDataSource(config);
    }

    public HikariDataSource getSource() {
        return source;
    }
}
