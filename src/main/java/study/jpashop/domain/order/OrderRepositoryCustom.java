package study.jpashop.domain.order;

import study.jpashop.api.v1.order.OrderSearchCondition;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> search(OrderSearchCondition condition);
}
