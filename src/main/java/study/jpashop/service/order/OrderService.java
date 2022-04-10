package study.jpashop.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.jpashop.domain.delivery.Delivery;
import study.jpashop.domain.item.Item;
import study.jpashop.domain.item.ItemRepository;
import study.jpashop.domain.order.Order;
import study.jpashop.domain.order.OrderRepository;
import study.jpashop.domain.orderitem.OrderItem;
import study.jpashop.domain.user.User;
import study.jpashop.domain.user.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long userId, Long itemId, int count) {

        //엔티티 조회
        User user = userRepository.getById(userId);
        Item item = itemRepository.getById(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(user, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.getById(orderId);
        //주문 취소
        order.cancel();
    }
}
