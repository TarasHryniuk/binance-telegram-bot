package bot.crypto;

import bot.crypto.telegram.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Taras Hryniuk, created on  21.10.2020
 * email : hryniuk.t@gmail.com
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);
    private static Properties appProps;

    public static void main(String[] args) {
        App app = new App();
        app.initializeProperties();
        String appVersion = appProps.getProperty("api.key");
        ApiContextInitializer.init();
        Bot test_habr_bot = new Bot("goover_bot", appVersion);
        test_habr_bot.botConnect();
    }

    private void initializeProperties() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static Properties getAppProps() {
        return appProps;
    }
}
