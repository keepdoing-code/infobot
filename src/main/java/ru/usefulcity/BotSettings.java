package ru.usefulcity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by yuri on 07.12.18.
 */

public class BotSettings {
    public static final String PROPERTIES_FILE = "./botconfig.properties";
    public static String BOT_USERNAME;
    public static String BOT_TOKEN;
    private static Logger log = LoggerFactory.getLogger(BotSettings.class);

    static {

////        String path = new File(BotSettings.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
////        File file = new File(path + File.separator + PROPERTIES_FILE);
//
           File file = new File(".");
           log.info("Current dir {}", file.getAbsolutePath());



        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE)) {

            Properties prop = new Properties();
            prop.load(fileInputStream);

            BOT_USERNAME = prop.getProperty("BOT_USERNAME");
            BOT_TOKEN = prop.getProperty("BOT_TOKEN");
        } catch (IOException e) {
            log.error("No config file {}", PROPERTIES_FILE);
        }
    }

}
