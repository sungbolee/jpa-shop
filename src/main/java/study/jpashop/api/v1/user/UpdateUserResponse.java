package study.jpashop.api.v1.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponse {
    private Long id;
    private String name;
}
