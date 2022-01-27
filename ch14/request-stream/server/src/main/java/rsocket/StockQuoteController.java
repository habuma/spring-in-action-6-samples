package rsocket;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class StockQuoteController {
	
	@MessageMapping("stock/{symbol}")
	public Flux<StockQuote> getStockPrice(
			@DestinationVariable("symbol") String symbol) {
		return Flux
			.interval(Duration.ofSeconds(1))
			.map(i -> {
				BigDecimal price = BigDecimal.valueOf(Math.random() * 10);
				return new StockQuote(symbol, price, Instant.now());
			});
	}
}
