package study.jpashop.api.order;

import lombok.Data;
import study.jpashop.domain.code.OrderStatus;

@Data
public class OrderSearchCondition {
    private OrderStatus orderStatus;
    private String userName;
}
