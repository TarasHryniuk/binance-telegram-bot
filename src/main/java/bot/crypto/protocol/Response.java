package bot.crypto.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Taras Hryniuk, created on  21.10.2020
 * email : hryniuk.t@gmail.com
 */
@Data
public class Response {

    public Response() {
    }

    public Response(BigDecimal usd, BigDecimal eur, BigDecimal uah, BigDecimal btc) {
        this.usd = usd;
        this.eur = eur;
        this.uah = uah;
        this.btc = btc;
    }

    @JsonProperty("USD")
    private BigDecimal usd;

    @JsonProperty("EUR")
    private BigDecimal eur;

    @JsonProperty("UAH")
    private BigDecimal uah;

    @JsonProperty("BTC")
    private BigDecimal btc;
}
