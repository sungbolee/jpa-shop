package study.jpashop.domain.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import study.jpashop.api.v1.order.OrderDto;
import study.jpashop.api.v1.order.OrderSearchCondition;
import study.jpashop.api.v1.order.QOrderDto;
import study.jpashop.domain.code.OrderStatus;

import javax.persistence.EntityManager;

import java.util.List;

import static study.jpashop.domain.delivery.QDelivery.delivery;
import static study.jpashop.domain.order.QOrder.order;
import static study.jpashop.domain.user.QUser.user;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderDto> search(OrderSearchCondition condition, Pageable pageable) {
        List<OrderDto> content = queryFactory
                .select(new QOrderDto(order))
                .from(order)
                .leftJoin(order.user, user).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(statusEq(condition.getOrderStatus()),
                        nameLike(condition.getUserName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order);

        return PageableExecutionUtils.getPage(content, pageable,countQuery::fetchOne);
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
