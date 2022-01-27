package rsocket;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GratuityOut {

	private BigDecimal billTotal;
	private int percent;
	private BigDecimal gratuity;
	
}
