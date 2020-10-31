package bot.crypto.services;

import bot.crypto.dao.StatisticDao;
import bot.crypto.httpclient.HttpsClient;
import bot.crypto.protocol.Response;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taras Hryniuk, created on  22.10.2020
 * email : hryniuk.t@gmail.com
 */
public class CreateMessageService {

    private static final String COIN_NOT_FOUND = "Coin not found";
    private static final String WELCOME = "Welcome to Crypto Currencies bot! You can enter name of coin and you will " +
            "receive currency.\nIf you have any ideas to create this bot better, please, write letter to goover.investments@gmail.com";
    private static final String HELP = "/start - welcome\n/crypto xxx - where xxx is name of coin";
    private static final String ERROR = "Error command '%s'";

    private StatisticDao statisticDao;
    private static final Logger LOGGER = Logger.getLogger(CreateMessageService.class);
    private static HttpsClient httpsClient;

    public String createMessage(String command, String userName, Integer userId, Integer messageId){

        statisticDao = new StatisticDao();
        statisticDao.insertUser(command, userId, messageId, userName);

        if(command.contains("/UAH")) return getCoinValueByUAH(command);
        if(command.contains("/XRP")) return getCoinValueByXRP(command);
        if(!isValid(command)) return errorCommand(command);
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

    private String getCoinValueByXRP(String command){
        try{
            httpsClient = new HttpsClient();
            String[] arr = command.split("\\s+");
            Response response = httpsClient.getResponse(arr[0].replace("/",""));
            String message = response.getMessage();
            if(null != message && !message.isEmpty()) return message;
            Float result = Long.parseLong(arr[1].replace("_", "")) * response.getUah().floatValue();
            return result + " uah";
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e);
            return COIN_NOT_FOUND;
        }
    }

    private String errorCommand(String message){
        return String.format(ERROR, message);
    }

    private String getCoinValueByUAH(String command){
        try{
            httpsClient = new HttpsClient();

            String[] arr = command.split("\\s+");

            Response response = httpsClient.getResponse(arr[0].replace("/",""));

            String message = response.getMessage();
            if(null != message && !message.isEmpty()) return message;

            Float result = Long.parseLong(arr[1].replace("_", "")) * response.getXrp().floatValue();

            return String.valueOf(result + " xrp");

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e);
            return COIN_NOT_FOUND;
        }
    }

    private String getCoinCurrency(String command){
        try{
            httpsClient = new HttpsClient();

            Response response = httpsClient.getResponse(command);

            StringBuilder sb = new StringBuilder();

            String message = response.getMessage();
            if(null != message && !message.isEmpty()) return message;

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

            sb.append('\n').append("'")
                    .append(command).append('\'').append(" ").append(sdf.format(new Date())).append('\n').append("USD: ").append(response.getUsd())
                    .append(" $").append('\n').append("EUR: ").append(response.getEur()).append(" €").append('\n')
                    .append("UAH: ").append(response.getUah()).append(" ₴").append('\n').append("BTC: ")
                    .append(response.getBtc()).append(" ฿").append('\n')
                    .append("XRP: ").append(response.getXrp()).append(" X").append("\n");

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e);
            return COIN_NOT_FOUND;
        }
    }

    public boolean isValid(String message){
        if(message.length() > 15) return false;
        if(message.length() < 2) return false;

        Pattern pattern = Pattern.compile("[а-яА-ЯїЇєЄъЪ~#@*+%{}<>\\[\\]|\"\\_^\\s+\n]");
        Matcher matcher = pattern.matcher(message);

        return !matcher.find();
    }

}
