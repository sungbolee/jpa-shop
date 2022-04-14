package study.jpashop.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.jpashop.domain.user.User;
import study.jpashop.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}
