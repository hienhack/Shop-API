package com.example.tutorial.service;

import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.dto.Size.SizeDTO;
import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.dto.Type.TypeDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.entity.ProductImage;
import com.example.tutorial.enumeration.Size;
import com.example.tutorial.repository.CategoryRepository;
import com.example.tutorial.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void creatProduct_shouldReturnProduct() throws JsonProcessingException  {
        ProductDTO producDTO = createProductDTOForTesting();

        ProductDTO result = productService.create(producDTO);

//        int a = 1 + 1;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(result);

        System.out.println(json);
    }

    @Test
    void getAllCategory() {
        categoryRepository.save(new Category(null, "jacket"));
        categoryRepository.save(new Category(null, "shirt"));
        List<Category> categories = categoryRepository.findAll();
        int a = 1 + 1;
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
