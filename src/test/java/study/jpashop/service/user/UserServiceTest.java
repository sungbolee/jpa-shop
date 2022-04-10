package study.jpashop.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.jpashop.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void 회원가입() {
        //given
        User user = new User("kim");

        //when
        Long savedId = userService.join(user);

        //then
        User findUser = userService.findOne(savedId);
        assertThat(user.getName()).isEqualTo(findUser.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        User user1 = new User("kim");
        User user2 = new User("kim");

        //when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.join(user2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
