package bot.crypto.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Taras Hryniuk, created on  21.10.2020
 * email : hryniuk.t@gmail.com
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    public Response() {
    }

    public Response(BigDecimal usd, BigDecimal eur, BigDecimal uah, BigDecimal btc) {
        this.usd = usd;
        this.eur = eur;
        this.uah = uah;
        this.btc = btc;
    }

    @JsonProperty(value ="USD")
    private BigDecimal usd;

    @JsonProperty(value ="EUR")
    private BigDecimal eur;

    @JsonProperty(value ="UAH")
    private BigDecimal uah;

    @JsonProperty(value ="BTC")
    private BigDecimal btc;

    @JsonProperty(value = "Message")
    private String message;
}
