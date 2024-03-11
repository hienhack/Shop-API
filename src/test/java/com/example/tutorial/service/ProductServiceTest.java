package com.example.tutorial.service;

import com.example.tutorial.controller.ProductController;
import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.dto.Size.SizeDTO;
import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.dto.Type.TypeDTO;
import com.example.tutorial.entity.Category;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductController productController;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    private int createdId;

    @Test
    void creatProduct_shouldReturnProduct() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ProductDTO producDTO = createProductDTO();
        System.out.println(ow.writeValueAsString(producDTO));


        ProductDTO result = productService.create(producDTO);


        System.out.println(ow.writeValueAsString(result));
        productService.delete(result.getId());
    }

    @Test
    void createProduct_ShouldThrowException() {
        ProductDTO productDTO = createProductDTO();
        productDTO.setDescription(null);

//        ProductDTO result = productController.create(productDTO);

//        productService.delete(result.getId());
    }


    @Test
    void updateProduct() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ProductDTO producDTO = createProductDTO();
        ProductDTO result = productService.create(producDTO);

        System.out.println("Created product");
        System.out.println(ow.writeValueAsString(result));

        try {
            ProductDTO changed = getUpdatedProductDTO(result);
            deleteBlackType(changed);
            ProductDTO updated = productService.updateEntireProduct(result.getId(), changed);
            System.out.println("Updated product");
            System.out.println(ow.writeValueAsString(updated));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            productService.delete(result.getId());
        }
    }

    @Test
    void persistProductWithExistedName_ShouldThrowException() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ProductDTO productDTO = createProductDTO();
        ProductDTO result = productService.create(productDTO);

        try {
            ProductDTO duplication = createProductDTO();
            productService.create(productDTO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            productService.delete(result.getId());
        }
    }

    @Test
    void getAllCategory() {
        categoryRepository.save(new Category(null, "jacket"));
        categoryRepository.save(new Category(null, "shirt"));
        List<Category> categories = categoryRepository.findAll();
        int a = 1 + 1;
    }

    @Test
    public void delete() {
//        ProductDTO productDTO = productService.getById(1);
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//
//        System.out.println(ow.writeValueAsString(productDTO));

        System.out.println("Delete function");

        productService.delete(1);

    }

    private ProductDTO createProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Cotton Jacket");
        productDTO.setPrice(5.15f);
        productDTO.setDescription("So fucking beautiful");
        productDTO.setThumbnail("No thumbnail yet");

        productDTO.setImages(Arrays.asList(
                "https://a/hien1.png",
                "https://a/hien2.png"
        ));

        productDTO.setTypes(Arrays.asList(
                new TypeDTO(null, "Black", "http://image/black.jpg"),
                new TypeDTO(null, "White", "http://image/white.jpg")
//                new TypeDTO(null, "White", "http://image/white.jpg")
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

//        productDTO.setCategories(Arrays.asList(
//                new Category(null, "jacket"),
//                new Category(null, "shirt")
//        ));

        return productDTO;
    }

    private ProductDTO getUpdatedProductDTO(ProductDTO productDTO) {
        productDTO.setName("Cotton Jackettttyty");
//        productDTO.setPrice(5.15f);
//        productDTO.setDescription("So fucking beautiful");
        productDTO.setThumbnail("https://a.com/b.png");

//        productDTO.setImages(Arrays.asList(
//                "https://a/hien1.png",
//                "https://a/hien2.png"
//        ));

//        productDTO.setTypes(Arrays.asList(
//                new TypeDTO(null, "Black", "http://image/black_updated.jpg"),
//                new TypeDTO(null, "White", "http://image/white.jpg"),
//                new TypeDTO(null, "Navy", "http://image/added.jpg")
//        ));

//        productDTO.setSizes(Arrays.asList(
//                new SizeDTO(null, Size.S, Map.of("Weight (kg)", "45", "Height (m)", "1.5")),
//                new SizeDTO(null, Size.M, Map.of("Weight (kg)", "55", "Height (m)", "1.6")),
//                new SizeDTO(null, Size.L, Map.of("Weight (kg)", "55", "Height (m)", "1.6"))
//        ));

//        productDTO.setStock(Arrays.asList(
//                new StockDetailDTO(null, "Black", Size.S, 200),
//                new StockDetailDTO(null, "Black", Size.M, 9),
//                new StockDetailDTO(null, "Black", Size.L, 9),
//                new StockDetailDTO(null, "White", Size.S, 18),
//                new StockDetailDTO(null, "White", Size.M, 27)
//                new StockDetailDTO(null, "White", Size.L, 37)
//                new StockDetailDTO(null, "Navy", Size.S, 71)
//                new StockDetailDTO(null, "Navy", Size.M, 76)
//                new StockDetailDTO(null, "Navy", Size.L, 72)
//        ));

//        productDTO.setCategories(Arrays.asList(
//                new Category(null, "jacket"),
//                new Category(null, "shirt")
//        ));

        productDTO.getSizes().add(new SizeDTO(null, Size.L, Map.of("Weight (kg)", "55", "Height (m)", "1.6")));
        productDTO.getTypes().add(new TypeDTO(null, "Navy", "http://image/added.jpg"));
        productDTO.getStock().addAll(List.of(
                new StockDetailDTO(null, "Navy", Size.S, 71),
                new StockDetailDTO(null, "Navy", Size.M, 76),
                new StockDetailDTO(null, "Navy", Size.L, 72),
                new StockDetailDTO(null, "Black", Size.L, 9),
                new StockDetailDTO(null, "White", Size.L, 37)
        ));

        return productDTO;
    }

    private void deleteBlackType(ProductDTO product) {
        product.setTypes(product.getTypes().stream()
                .filter(type -> !type.getName().contains("Black")).collect(Collectors.toList()));
        product.setStock(product.getStock().stream()
                .filter(s -> !s.getType().contains("Black")).collect(Collectors.toList()));
    }
}
