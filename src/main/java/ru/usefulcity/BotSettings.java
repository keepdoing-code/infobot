package ru.usefulcity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yuri on 07.12.18.
 */

public class BotSettings {
    public static final String PATH_TO_PROPERTIES = "botconfig.properties";
    public static String BOT_USERNAME;
    public static String BOT_TOKEN;
    private static Logger log = LoggerFactory.getLogger(BotSettings.class);

    static {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            Properties prop = new Properties();
            prop.load(fileInputStream);

            BOT_USERNAME = prop.getProperty("BOT_USERNAME");
            BOT_TOKEN = prop.getProperty("BOT_TOKEN");
        } catch (IOException e) {
            log.info("Empty or no config file...");
        }
    }

}
