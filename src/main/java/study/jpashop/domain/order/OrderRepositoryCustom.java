package study.jpashop.domain.order;

import org.springframework.data.domain.Pageable;
import study.jpashop.api.order.OrderSearchCondition;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> search(OrderSearchCondition condition, Pageable pageable);
}
