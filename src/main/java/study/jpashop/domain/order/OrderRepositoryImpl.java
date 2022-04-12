package study.jpashop.domain.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;
import study.jpashop.api.v1.order.OrderSearchCondition;
import study.jpashop.domain.code.OrderStatus;

import javax.persistence.EntityManager;

import java.util.List;

import static study.jpashop.domain.delivery.QDelivery.delivery;
import static study.jpashop.domain.order.QOrder.order;
import static study.jpashop.domain.user.QUser.user;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> search(OrderSearchCondition condition) {
        return queryFactory
                .select(order)
                .from(order)
                .join(order.user, user)
                .where(statusEq(condition.getOrderStatus()),
                        nameLike(condition.getUserName()))
                .limit(1000)
                .fetch();
    }

    @Override
    public List<Order> findAllOrder(int offset, int limit) {
        return queryFactory
                .select(order)
                .from(order)
                .join(order.user, user).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .offset(offset)
                .limit(limit)
                .fetch();
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
