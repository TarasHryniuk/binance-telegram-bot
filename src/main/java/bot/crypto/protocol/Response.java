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

    @JsonProperty(value ="USD", required = false)
    private BigDecimal usd;

    @JsonProperty(value ="EUR", required = false)
    private BigDecimal eur;

    @JsonProperty(value ="UAH", required = false)
    private BigDecimal uah;

    @JsonProperty(value ="BTC", required = false)
    private BigDecimal btc;

    @JsonProperty(value = "Message", required = false)
    private String message;
}
