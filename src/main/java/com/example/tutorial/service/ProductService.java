package com.example.tutorial.service;

import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.dto.Product.ProductInListDTO;
import com.example.tutorial.dto.StockDetail.StockDetailDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.CategoryRepository;
import com.example.tutorial.repository.ProductRepository;
import com.example.tutorial.util.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<ProductInListDTO> getAll(Pageable pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductInListDTO::new);
    }

    public ProductDTO getById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductDTO::new).orElse(null);
    }

    public Page<ProductInListDTO> getAllByCategoryName(String categoryName, Pageable pageRequest) {
        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(),
                        "Can not found category with name = " + categoryName));
        return productRepository.findProductByCategoriesContains(category, pageRequest).map(ProductInListDTO::new);
    }

    public Page<ProductInListDTO> findByKeyword(String keyword, Pageable pageInfo) {
        return productRepository.findRelatedProductByKeywords(keyword, pageInfo).map(ProductInListDTO::new);
    }

    /**
     * Get related keyword for searching offers
     * @param keyword search keyword
     * @param maxResults total result to be returned
     * @return a List of keywords
     */
    public List<String> getRelatedKeyword(String keyword, Integer maxResults) {
        return productRepository.findRelatedProductByKeywords(keyword, maxResults);
    }

    public Page<ProductInListDTO> getByName(String name, Pageable pageRequest) {
        Page<Product> page = productRepository.findProductByNameContainingIgnoreCase(name, pageRequest);
        return page.map(ProductInListDTO::new);
    }

    public ProductDTO create(ProductDTO productDTO) {
        Product product = createProductFromProductDTO(productDTO);
        Product p = productRepository.save(product);
        return new ProductDTO(p);
    }

    public ProductDTO updateEntireProduct(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Not found product with id = " + id));

        productDTO.setId(id);
        Product savedProduct = productRepository.save(createProductFromProductDTO(productDTO));
        return new ProductDTO(savedProduct);
    }

    public ProductDTO updateProductPart(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Not found product with id = " + id));

        // Todo: Updating not null fields to the existed entity

        return null;
    }

    public void delete(Integer id) throws BusinessException {
        if (!productRepository.existsById(id)) {
            throw new BusinessException(404, "Product not found");
        }
        productRepository.deleteById(id);
    }

    private Product createProductFromProductDTO(ProductDTO productDTO) {
        if (ListHelper.hasDuplicatedValues(productDTO.getTypes())) {
            throw new BusinessException("Duplicated types found");
        } else if (ListHelper.hasDuplicatedValues(productDTO.getSizes())) {
            throw new BusinessException("Duplicated size found");
        } else if (ListHelper.hasDuplicatedValues(productDTO.getStock())) {
            throw new BusinessException("Duplicated stock detail found");
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setThumbnail(productDTO.getThumbnail());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImages(productDTO.getImages().stream().map(image -> new ProductImage(image, product)).toList());

        product.setTypes(productDTO.getTypes().stream().map(typeDTO ->
                new Type(typeDTO.getId(), typeDTO.getName(), typeDTO.getImage(), product)
        ).toList());

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
                } else {
                    stock.add(new StockDetail(null, product, type, size, 0));
                }
            }
        }
        product.setStock(stock);

        for (Category category : productDTO.getCategories()) {
            if (category.getId() != null) {
                Category existedCategory = categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new BusinessException("Not found category with id = " + category.getId()));
                product.getCategories().add(existedCategory);
            } else {
                product.getCategories().add(category);
            }
        }

        if (product.getCategories().isEmpty()) {
            throw new BusinessException("No category found, product must belong to at least one category");
        }

        return product;
    }
}
