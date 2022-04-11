package study.jpashop.domain.item;

import study.jpashop.api.v1.item.ItemDto;

public interface ItemRepositoryCustom {
    ItemDto findOne(Long itemId);
}
