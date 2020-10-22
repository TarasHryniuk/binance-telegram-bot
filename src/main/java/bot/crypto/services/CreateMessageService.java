package bot.crypto.services;

import bot.crypto.dao.StatisticDao;
import bot.crypto.httpclient.HttpsClient;
import bot.crypto.protocol.Response;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Taras Hryniuk, created on  22.10.2020
 * email : hryniuk.t@gmail.com
 */
public class CreateMessageService {

    private static final String COIN_NOT_FOUND = "Coin not found";
    private static final String WELCOME = "Welcome to Crypto Currencies bot! You can enter name of coin and you will " +
            "receive currency.\nIf you have any ideas to create this bot better, please, write letter to goover.investments@gmail.com";
    private static final String HELP = "/start - welcome\n/crypto xxx - where xxx is name of coin";

    private StatisticDao statisticDao;
    private static final Logger LOGGER = Logger.getLogger(CreateMessageService.class);
    private static HttpsClient httpsClient;

    public String createMessage(String command, String userName, Integer userId, Integer messageId){

        statisticDao = new StatisticDao();
        statisticDao.insertUser(command, userId, messageId, userName);

        if(command.equalsIgnoreCase("/start")) return startCommand();
        if(command.equalsIgnoreCase("/help")) return helpCommand();
        if(!command.contains("/")) return getCoinCurrency(command);

        return "";
    }

    private String startCommand(){
        return WELCOME;
    }

    private String helpCommand(){
        return HELP;
    }

    private String getCoinCurrency(String command){
        try{
            httpsClient = new HttpsClient();

            Response response = httpsClient.getResponse(command);

            StringBuilder sb = new StringBuilder();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

            sb.append('\n').append("'")
                    .append(command).append('\'').append(" ").append(sdf.format(new Date())).append('\n').append("USD: ").append(response.getUsd())
                    .append(" $").append('\n').append("EUR: ").append(response.getEur()).append(" €").append('\n')
                    .append("UAH: ").append(response.getUah()).append(" ₴").append('\n').append("BTC: ")
                    .append(response.getBtc()).append(" ฿").append('\n');

            return sb.toString();

        } catch (Exception e) {
            LOGGER.error(e);
            return COIN_NOT_FOUND;
        }
    }
}
