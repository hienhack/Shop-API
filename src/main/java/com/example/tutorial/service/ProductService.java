package com.example.tutorial.service;

import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.exception.LogicException;
import com.example.tutorial.exception.ResourceNotFountException;
import com.example.tutorial.repository.CategoryRepository;
import com.example.tutorial.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductDTO> getAll(Pageable pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductDTO::new);
    }

    public ProductDTO getById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductDTO::new).orElse(null);
    }

    public Page<ProductDTO> getAllByCategory(List<Category> categories, Pageable pageInfo) {
        return productRepository.findProductByCategoriesIsIn(categories, pageInfo).map(ProductDTO::new);
    }

    public Page<ProductDTO> findByKeyword(String keyword, Pageable pageInfo) {
        return productRepository.findRelatedProductByKeywords(keyword, pageInfo).map(ProductDTO::new);
    }

    public List<String> getRelatedKeyword(String keyword, Integer maxResults) {
        return productRepository.findRelatedProductByKeywords(keyword, maxResults);
    }

    public Page<ProductDTO> getByName(String name, Pageable pageRequest) {
        Page<Product> page = productRepository.findProductByNameContainingIgnoreCase(name, pageRequest);
        return page.map(ProductDTO::new);
    }

    public ProductDTO create(ProductDTO productDTO) {
        Product product = createProductFromProductDTO(productDTO);
        return new ProductDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO updateEntireProduct(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Not found product with id = " + id));

        productDTO.setId(id);
        Product savedProduct = productRepository.save(createProductFromProductDTO(productDTO));
        return new ProductDTO(savedProduct);
    }

    public ProductDTO updateProductPart(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Not found product with id = " + id));

        return null;
    }

    public void delete(Integer id) throws ResourceNotFountException {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFountException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private Product createProductFromProductDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setThumbnail(productDTO.getThumbnail());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImages(productDTO.getImages().stream().map(image -> new ProductImage(image, product)).toList());

        // not checked unique type's name yet
        product.setTypes(productDTO.getTypes().stream().map(typeDTO ->
                new Type(typeDTO.getId(), typeDTO.getName(), typeDTO.getImage(), product)
        ).toList());

        // not checked unique size yet
        product.setSizes(productDTO.getSizes().stream().map(sizeDTO ->
                new ProductSize(sizeDTO.getId(), sizeDTO.getSize(), sizeDTO.getSizeGuide(), product)
        ).toList());

        List<StockDetail> stock = new ArrayList<>();
        for (Type type : product.getTypes()) {
            for (ProductSize size : product.getSizes()) {
                int index = productDTO.getStock().indexOf(
                        StockDetailDTO.builder().type(type.getName()).size(size.getSize()).build());
                if (index != -1) {
                    StockDetailDTO target = productDTO.getStock().get(index);
                    stock.add(new StockDetail(target.getId(), product, type, size, target.getInStock()));
                }
            }
        }
        product.setStock(stock);

        for (Category category : productDTO.getCategories()) {
            if (category.getId() != null) {
                Category existedCategory = categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new ResourceNotFountException("Not found category with id = " + category.getId()));
                product.getCategories().add(existedCategory);
            }
        }

        if (product.getCategories().isEmpty()) {
            throw new LogicException("No category found, product must belong to at least one category");
        }

        return product;
    }
}
