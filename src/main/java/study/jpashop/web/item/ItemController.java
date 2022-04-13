package study.jpashop.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.jpashop.api.v1.item.ItemDto;
import study.jpashop.domain.item.Book;
import study.jpashop.domain.item.Item;
import study.jpashop.domain.item.ItemRepository;
import study.jpashop.service.item.ItemService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("book") BookForm form, BindingResult bindingResult) {

        //특정 필드가 아닌 복합 룰 검증
        if (form.getPrice() != null && form.getStockQuantity() != null) {
            int resultPrice = form.getPrice() * form.getStockQuantity();
            if (resultPrice < 10_000) {
                bindingResult.reject("totalPriceMin", new Object[]{10_000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "items/createItemForm";
        }

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        ItemDto itemDto = itemRepository.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(itemDto.getId());
        form.setName(itemDto.getName());
        form.setPrice(itemDto.getPrice());
        form.setStockQuantity(itemDto.getStockQuantity());
        form.setAuthor(itemDto.getAuthor());
        form.setIsbn(itemDto.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(form.getId(), form.getName(), form.getPrice());
        return "redirect:/items";
    }
}
