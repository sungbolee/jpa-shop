package study.jpashop.domain.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import study.jpashop.api.v1.item.ItemDto;
import study.jpashop.api.v1.item.QItemDto;

import javax.persistence.EntityManager;

import static study.jpashop.domain.item.QBook.book;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ItemDto findOne(Long id) {
        return queryFactory
                .select(new QItemDto(
                        book.id,
                        book.name,
                        book.price,
                        book.stockQuantity,
                        book.author,
                        book.isbn))
                .from(book)
                .where(book.id.eq(id))
                .fetchOne();
    }
}
