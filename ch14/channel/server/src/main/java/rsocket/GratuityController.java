package rsocket;
import java.math.BigDecimal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class GratuityController {

	@MessageMapping("gratuity")
	public Flux<GratuityOut> calculate(Flux<GratuityIn> gratuityInFlux) {
		return gratuityInFlux
			.doOnNext(in -> log.info("Calculating gratuity:  {}", in))
			.map(in -> {
				double percentAsDecimal = in.getPercent() / 100.0;
				BigDecimal gratuity = in.getBillTotal()
						.multiply(BigDecimal.valueOf(percentAsDecimal));
				return new GratuityOut(in.getBillTotal(), in.getPercent(), gratuity);
			});
	}

}
