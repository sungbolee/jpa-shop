package study.jpashop.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.jpashop.api.v1.order.OrderDto;
import study.jpashop.api.v1.order.OrderSearchCondition;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> search(OrderSearchCondition condition);
    Page<OrderDto> search(OrderSearchCondition condition, Pageable pageable);
}
