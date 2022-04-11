package study.jpashop.api.v1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.jpashop.domain.user.User;
import study.jpashop.response.Result;
import study.jpashop.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserService userService;

    @GetMapping
    public Result users() {
        List<User> findUsers = userService.findUsers();
        List<UserDto> collect = findUsers.stream()
                .map(m -> new UserDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest request) {

        User user = new User();
        user.setName(request.getName());

        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    @PutMapping("/{id}")
    public UpdateUserResponse updateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateUserRequest request) {

        userService.update(id, request.getName());
        User findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getName());
    }
}
