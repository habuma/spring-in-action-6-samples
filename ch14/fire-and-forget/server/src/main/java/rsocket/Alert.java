package rsocket;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alert {

	private Level level;
	private String orderedBy;
	private Instant orderedAt;

	public enum Level {
		YELLOW, ORANGE, RED, BLACK
	}
}
