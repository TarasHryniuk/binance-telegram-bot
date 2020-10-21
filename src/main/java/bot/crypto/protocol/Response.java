package bot.crypto.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Taras Hryniuk, created on  21.10.2020
 * email : hryniuk.t@gmail.com
 */
@ToString
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
    @Setter
    @Getter
    private BigDecimal usd;

    @JsonProperty("EUR")
    @Setter
    @Getter
    private BigDecimal eur;

    @JsonProperty("UAH")
    @Setter
    @Getter
    private BigDecimal uah;

    @JsonProperty("BTC")
    @Setter
    @Getter
    private BigDecimal btc;
}
