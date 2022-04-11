package study.jpashop.api.v1.user;

import lombok.Data;

@Data
public class CreateUserResponse {
    private Long id;

    public CreateUserResponse(Long id) {
        this.id = id;
    }
}
