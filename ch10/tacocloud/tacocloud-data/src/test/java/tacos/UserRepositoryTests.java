package tacos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tacos.data.UserRepository;

@DataJpaTest
public class UserRepositoryTests {
	
	@Autowired
	UserRepository repo;
	
	@Test
	public void shouldSaveUserAndFetchByUsername() {
		
		User newUser = new User(
				"testuser", "password", 
				"Joe Test", "1234 North Street", "Notrees", "TX", 
				"79759", "123-123-1234");

		assertThat(repo.count()).isEqualTo(0);
		repo.save(newUser);
		assertThat(repo.count()).isEqualTo(1);
		
		User foundUser = repo.findByUsername("testuser");
		assertThat(foundUser).isEqualTo(newUser);
	}
	
}
