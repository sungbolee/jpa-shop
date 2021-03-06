package study.jpashop.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.jpashop.domain.BaseEntity;
import study.jpashop.domain.valuetype.Address;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String name;

    private String password;

    @Embedded
    private Address address;

    public User(String name) {
        this.name = name;
    }
}
