package study.jpashop.service.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.jpashop.domain.code.OrderStatus;
import study.jpashop.domain.item.Book;
import study.jpashop.domain.item.Item;
import study.jpashop.domain.order.Order;
import study.jpashop.domain.order.OrderRepository;
import study.jpashop.domain.user.User;
import study.jpashop.domain.valuetype.Address;
import study.jpashop.exception.NotEnoughStockException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void 상품주문() {
        //given
        User user = createUser();

        Book book = createBook("시골 JPA", 10_000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(user.getId(), book.getId(), orderCount);

        em.flush();
        em.clear();

        //then
        Order getOrder = orderRepository.getById(orderId);

        //상품 주문시 상태는 ORDER
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);

        //주문한 상품 종류 수가 정확해야 한다.
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);

        //주문 가격은 가격 * 수량이다.
        assertThat(getOrder.getTotalPrice()).isEqualTo(10_000 * orderCount);

        //주문 수량만큼 재고가 줄어야 한다.
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        User user = createUser();
        Item item = createBook("시골 JPA", 10_000, 10);

        int orderCount = 11;

        //when
        NotEnoughStockException e = assertThrows(NotEnoughStockException.class, () -> orderService.order(user.getId(), item.getId(), orderCount));

        em.flush();
        em.clear();

        //then
        assertThat(e.getMessage()).isEqualTo("need more stock");
    }

    @Test
    void 주문취소() {
        //given
        User user = createUser();
        Item item = createBook("시골 JPA", 10_000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(user.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        em.flush();
        em.clear();

        //then
        Order getOrder = orderRepository.getById(orderId);

        //주문 취소시 상태는 CANCEL 이다.
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);

        //주문이 취소된 상품은 그만큼 재고가 증가해야 한다.
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private User createUser() {
        User user = new User("회원1");
        user.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(user);
        return user;
    }
}
