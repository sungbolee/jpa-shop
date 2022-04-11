package study.jpashop.api.v1.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserRequest {
    @NotEmpty
    private String name;
}
