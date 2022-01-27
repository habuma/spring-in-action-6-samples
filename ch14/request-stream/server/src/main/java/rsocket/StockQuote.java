package rsocket;
import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockQuote {

	private String symbol;
	private BigDecimal price;
	private Instant timestamp;
	
}
