package study.jpashop.web.item;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class BookForm {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 1_000, max = 1_000_000)
    @NumberFormat(pattern = "###,###")
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer stockQuantity;

    private String author;
    private String isbn;

    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
