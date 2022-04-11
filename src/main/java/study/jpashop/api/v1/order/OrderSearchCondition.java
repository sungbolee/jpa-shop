package study.jpashop.api.v1.order;

import lombok.Data;
import study.jpashop.domain.code.OrderStatus;

@Data
public class OrderSearchCondition {
    private OrderStatus orderStatus;
    private String userName;
}
