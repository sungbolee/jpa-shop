package study.jpashop.domain.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import study.jpashop.api.v1.order.OrderDto;
import study.jpashop.api.v1.order.OrderSearchCondition;
import study.jpashop.api.v1.order.QOrderDto;
import study.jpashop.domain.Querydsl4RepositorySupport;
import study.jpashop.domain.code.OrderStatus;

import java.util.List;

import static study.jpashop.domain.delivery.QDelivery.delivery;
import static study.jpashop.domain.order.QOrder.order;
import static study.jpashop.domain.user.QUser.user;

public class OrderRepositoryCustomImpl extends Querydsl4RepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryCustomImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearchCondition condition) {
        return selectFrom(order)
                .join(order.user, user)
                .where(statusEq(condition.getOrderStatus()),
                        nameLike(condition.getUserName()))
                .limit(1000)
                .fetch();
    }

    @Override
    public Page<OrderDto> search(OrderSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QOrderDto(order))
                .from(order)
                .leftJoin(order.user, user).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(statusEq(condition.getOrderStatus()),
                        nameLike(condition.getUserName())

                ), countQuery -> countQuery
                .select(order.id)
                .from(order)
                .leftJoin(order.user, user).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(statusEq(condition.getOrderStatus()),
                        nameLike(condition.getUserName()))
        );
    }

    private BooleanExpression statusEq(OrderStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return order.status.eq(statusCond);
    }

    private BooleanExpression nameLike(String nameCond) {
        if (!StringUtils.hasText(nameCond)) {
            return null;
        }
        return user.name.like(nameCond);
    }
}
