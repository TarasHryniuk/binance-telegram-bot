package bot.crypto.telegram;

import bot.crypto.httpclient.HttpsClient;
import bot.crypto.protocol.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Taras Hryniuk, created on  21.10.2020
 * email : hryniuk.t@gmail.com
 */
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(Bot.class);

    final int RECONNECT_PAUSE = 10000;

    private HttpsClient httpsClient;

    @Setter
    @Getter
    String userName;
    @Setter
    @Getter
    String token;

    public Bot(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("Receive new Update. updateID: " + update.getUpdateId());

        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText().toUpperCase();

        if (inputText.equalsIgnoreCase("/start") || inputText.equalsIgnoreCase("/help")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Welcome to Crypto Currencies bot! You can enter name of coin and you will receive currency");
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(sb.toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                LOGGER.error(e);
            }
        } else {
            httpsClient = new HttpsClient();
            Response response = httpsClient.getResponse(inputText);

            StringBuilder sb = new StringBuilder();
            sb.append("-----------------------------------------");
            sb.append('\n');
            sb.append("          You entered         '").append(inputText).append('\'');
            sb.append('\n');
            sb.append("USD: ").append(response.getUsd()).append(" $");
            sb.append('\n');
            sb.append("EUR: ").append(response.getEur()).append(" €");
            sb.append('\n');
            sb.append("UAH: ").append(response.getUah()).append(" ₴");
            sb.append('\n');
            sb.append("BTC: ").append(response.getBtc()).append(" ฿");
            sb.append('\n');
            sb.append("-----------------------------------------");

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(sb.toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
            LOGGER.info("TelegramAPI started. Look for messages");
        } catch (TelegramApiRequestException e) {
            LOGGER.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                LOGGER.error(e1);
                return;
            }
            botConnect();
        }
    }
}
