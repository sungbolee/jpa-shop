package study.jpashop.api.v1.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.jpashop.domain.order.OrderRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping
    public Page<OrderDto> search(OrderSearchCondition condition, @PageableDefault(size = 5) Pageable pageable) {
        return orderRepository.search(condition, pageable);
    }
}
