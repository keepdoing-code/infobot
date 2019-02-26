package ru.usefulcity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yuri on 07.12.18.
 */

public class BotSettings {
    public static final String PROPERTIES_FILE = "botconfig.properties";
    public static String BOT_USERNAME;
    public static String BOT_TOKEN;
    private static Logger log = LoggerFactory.getLogger(BotSettings.class);

    static {

        String path = new File(BotSettings.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
        File file = new File(path +" /" + PROPERTIES_FILE);

        if (!file.exists()){
            file = new File("./"+PROPERTIES_FILE);
        }

        log.info(file.getPath().toString());

        try (FileInputStream fileInputStream = new FileInputStream(file)) {

            Properties prop = new Properties();
            prop.load(fileInputStream);

            BOT_USERNAME = prop.getProperty("BOT_USERNAME");
            BOT_TOKEN = prop.getProperty("BOT_TOKEN");
        } catch (IOException e) {
            log.error(path);
        }
    }

}
