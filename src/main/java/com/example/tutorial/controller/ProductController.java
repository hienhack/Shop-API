package com.example.tutorial.controller;

import com.example.tutorial.dto.Product.ProductDTO;
import com.example.tutorial.dto.Product.ProductInListDTO;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.entity.Category;
import com.example.tutorial.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<Page<ProductInListDTO>> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "page_size", defaultValue = "20") Integer pageSize,
            @RequestParam(name = "sort", defaultValue = "") String sortQuery,
            @RequestParam(name = "category", defaultValue = "") String categoryName
            ) {
        Pageable pageRequest = sortQuery.isBlank() ? PageRequest.of(page, pageSize)
                : PageRequest.of(page, pageSize, getFinalSort(sortQuery));

        return ResponseDTO.of(categoryName.isBlank() ? productService.getAll(pageRequest)
                : productService.getAllByCategoryName(categoryName, pageRequest));
    }

    @GetMapping(value = "/find")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<Page<ProductInListDTO>> find(
            @RequestParam(name = "key") String keyword,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "page_size", defaultValue = "20") Integer pageSize,
            @RequestParam(name = "sort", defaultValue = "") String sortQuery
    ) {
        Pageable requestPage = sortQuery.isBlank() ? PageRequest.of(page, pageSize)
                : PageRequest.of(page, pageSize, getFinalSort(sortQuery));

        return ResponseDTO.of(productService.findByKeyword(keyword, requestPage));
    }

    @GetMapping(value = "/find-suggest")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<List<String>> findProductSuggestionKeyword(
            @RequestParam(name = "key") String keyword,
            @RequestParam(name = "max-results", defaultValue = "10") Integer maxResults
    ) {
        return ResponseDTO.of(productService.getRelatedKeyword(keyword, maxResults));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<ProductDTO> getProduct(@PathVariable Integer id) {
        return ResponseDTO.of(productService.getById(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<ProductDTO> create(@RequestBody @Valid ProductDTO product){
        return ResponseDTO.of(productService.create(product));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return ResponseDTO.of(productService.updateEntireProduct(id, productDTO));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }

    private enum SortableField {
        name,
        price
    }

    private Sort getFinalSort(String sortQuery) {
        var sortModes = sortQuery.split(",");

        List<Sort> sorts = Arrays.stream(sortModes).map(sortMode -> {
            try {
                SortableField field = SortableField.valueOf(sortMode.replace("-", "").toLowerCase());
                int index = sortMode.indexOf('-');
                Sort.Direction direction = index == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
                return Sort.by(direction, field.name());
            } catch (IllegalArgumentException e) {
                return Sort.unsorted();
            }
        }).toList();

        return sorts.stream().reduce(Sort.unsorted(), Sort::and);
    }
}
