package rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSocketReqStreamClientApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RSocketReqStreamClientApplication.class, args);
		
		// Sleep for 10 seconds to keep the main thread alive long enough to send message
		// and receive a handful of streamed responses.
		// In a complete example, this isn't necessary because the Tomcat server or some
		// other component will likely keep the app alive. But this example is too simple
		// and the main thread could end too quickly, before the message can be sent.
		Thread.sleep(10000); 
	}

}
