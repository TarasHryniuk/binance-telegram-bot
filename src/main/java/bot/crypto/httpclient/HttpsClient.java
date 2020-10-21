package bot.crypto.httpclient;

import bot.crypto.protocol.Response;
import bot.crypto.telegram.Bot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Taras Hryniuk, created on  21.10.2020
 * email : hryniuk.t@gmail.com
 */
public class HttpsClient {

    private static final Logger LOGGER = Logger.getLogger(Bot.class);

    static Properties appProps;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Response getResponse(String curr) {

        Response resp = null;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            StringBuilder sb = new StringBuilder();
            sb.append("https://min-api.cryptocompare.com/data/price?fsym=").append(curr).append("&tsyms=USD,EUR,UAH,BTC");

            HttpGet request = new HttpGet(sb.toString());

            HttpsClient httpsClient = new HttpsClient();
            httpsClient.initializeProperties();

            // add request headers
            request.addHeader(HttpHeaders.AUTHORIZATION, appProps.get("api.key.cryptocompare").toString());

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                LOGGER.debug(response.getProtocolVersion());              // HTTP/1.1
                LOGGER.debug(response.getStatusLine().getStatusCode());   // 200
                LOGGER.debug(response.getStatusLine().getReasonPhrase()); // OK
                LOGGER.debug(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();

                resp = objectMapper.readValue(EntityUtils.toString(entity), Response.class);

            } finally {
                response.close();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            try {
                httpClient.close();
            } catch (Exception e){
                LOGGER.error(e);
            }
        }

        return resp;

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
}
