package study.jpashop.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired UserRepository userRepository;

    @Test
    void basicTest() {
        User user = new User("user1");
        userRepository.save(user);

        List<User> result = userRepository.findByName(user.getName());
        assertThat(result).containsExactly(user);
    }
}
