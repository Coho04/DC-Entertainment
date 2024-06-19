package de.goldendeveloper.entertainment;


import io.github.coho04.dcbcore.Config;

/**
 * This class extends the Config class and provides methods to retrieve environment variables.
 * It is used to get the configuration values for the MySQL database and the YouTube API key.
 */
public class CustomConfig extends Config {

    /**
     * This method retrieves the MySQL port from the environment variables.
     *
     * @return The MySQL port as an integer.
     */
    public int getMysqlPort() {
        return Integer.parseInt(dotenv.get("MYSQL_PORT"));
    }

    /**
     * This method retrieves the YouTube API key from the environment variables.
     *
     * @return The YouTube API key as a string.
     */
    public String getYtApiKey() {
        return dotenv.get("YT_API_KEY");
    }

    /**
     * This method retrieves the MySQL hostname from the environment variables.
     *
     * @return The MySQL hostname as a string.
     */
    public String getMysqlHostname() {
        return dotenv.get("MYSQL_HOSTNAME");
    }

    /**
     * This method retrieves the MySQL password from the environment variables.
     *
     * @return The MySQL password as a string.
     */
    public String getMysqlPassword() {
        return dotenv.get("MYSQL_PASSWORD");
    }

    /**
     * This method retrieves the MySQL username from the environment variables.
     *
     * @return The MySQL username as a string.
     */
    public String getMysqlUsername() {
        return dotenv.get("MYSQL_USERNAME");
    }
}