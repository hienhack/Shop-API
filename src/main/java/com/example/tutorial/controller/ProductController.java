package com.example.tutorial.controller;

import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.enumeration.ProductSortMode;
import com.example.tutorial.service.ProductService;
import com.example.tutorial.util.SortHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductDTO getProduct(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @GetMapping
    public Page<ProductDTO> getAll(
            @RequestParam(name = "sort", defaultValue = "none") List<ProductSortMode> sortModes,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "page_size", defaultValue = "20") Integer pageSize
    ) {
        Pageable pageRequest = sortModes == null ? PageRequest.of(page, pageSize)
                : PageRequest.of(page, pageSize, SortHelper.getFinalSort(sortModes));

        return productService.getAll(pageRequest);
    }

    @GetMapping(value = "/find")
    public Page<ProductDTO> find(
            @RequestParam(name = "key", required = true) String keyword,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "page_size", defaultValue = "20") Integer pageSize,
            @RequestParam(name = "sort") List<ProductSortMode> sortModes
    ) {

        Pageable requestPage = sortModes == null ? PageRequest.of(page, pageSize)
                : PageRequest.of(page, pageSize, SortHelper.getFinalSort(sortModes));

        return productService.findByKeyword(keyword, requestPage);
    }

    @GetMapping(value = "/find-suggest")
    public List<String> findProductSuggestion(
            @RequestParam(name = "key", required = true) String keyword,
            @RequestParam(name = "max-results", defaultValue = "10") Integer maxResults
    ) {
        return productService.getRelatedKeyword(keyword, maxResults);
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO product){
        return productService.create(product);
    }

    @PutMapping(value = "/{id}")
    public ProductDTO update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productService.updateEntireProduct(id, productDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
    /*
     * products create
     * products update
     * products delete
     * products
     */
}
