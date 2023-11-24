package com.example.tutorial;

import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.dto.Size.SizeDTO;
import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.dto.Type.TypeDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.entity.ProductImage;
import com.example.tutorial.enumeration.Size;
import com.example.tutorial.service.CategroyService;
import com.example.tutorial.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TutorialApplicationTests {
    @Autowired
    private CategroyService categroyService;

    @Autowired
    private ProductService productService;

    @Test
    void getAllCategory() {
        List<Category> categories = categroyService.getAllCategories();
        int a = 1 + 1;
    }

    @Test
    void creatProduct_shouldReturnProduct() throws JsonProcessingException {
        ProductDTO producDTO = createProductDTOForTesting();

        ProductDTO result = productService.create(producDTO);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(result);

        System.out.println(json);
    }

    @Test
    void addCategory() {
        categroyService.create(new Category(null, "jacket"));
        categroyService.create(new Category(null, "shirt"));
    }

    @Test
    void getProduct() throws JsonProcessingException {
        ProductDTO product = productService.getById(1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(product);

        System.out.println(json);
    }


    private ProductDTO createProductDTOForTesting() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Cotton Jacket");
        productDTO.setPrice(5.15f);
        productDTO.setDescription("So fucking beautiful");
        productDTO.setThumbnail("No thumbnail yet");

        productDTO.setImages(Arrays.asList(
                new ProductImage("https://a/hien1.png", null),
                new ProductImage("https://a/hien2.png", null)
        ));

        productDTO.setTypes(Arrays.asList(
                new TypeDTO(null, "Black", "http://image/black.jpg"),
                new TypeDTO(null, "White", "http://image/white.jpg")
        ));

        productDTO.setSizes(Arrays.asList(
                new SizeDTO(null, Size.S, Map.of("Weight (kg)", "40", "Height (m)", "1.5")),
                new SizeDTO(null, Size.M, Map.of("Weight (kg)", "50", "Height (m)", "1.6"))
        ));

        productDTO.setStock(Arrays.asList(
                new StockDetailDTO(null, "Black", Size.S, 10),
                new StockDetailDTO(null, "Black", Size.M, 9),
                new StockDetailDTO(null, "White", Size.S, 8),
                new StockDetailDTO(null, "White", Size.M, 7)
        ));

        productDTO.setCategories(Arrays.asList(
                new Category(1, "jacket"),
                new Category(2, "shirt")
        ));

        return productDTO;
    }

}
