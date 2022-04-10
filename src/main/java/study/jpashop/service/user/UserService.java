package study.jpashop.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.jpashop.domain.user.User;
import study.jpashop.domain.user.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user); //중복 회원 검증
        return userRepository.save(user).getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findByName(user.getName());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public User findOne(Long userId) {
        return userRepository.getById(userId);
    }

    /**
     * 회원 단건 수정
     */
    @Transactional
    public void update(Long id, String name) {
        User user = userRepository.getById(id);
        user.setName(name);
    }
}
