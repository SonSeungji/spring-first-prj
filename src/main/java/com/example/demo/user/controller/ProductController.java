package com.example.demo.user.controller;

import com.example.demo.user.domain.Product;
import com.example.demo.user.dto.ProductDto;
import com.example.demo.user.model.ListWrapper;
import com.example.demo.user.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity createProduct(@RequestBody ListWrapper<ProductDto> productDto) {

        List<Product> insertProductData = productDto.getDataList().stream()
                .map(productData -> productData.toEntity())
                .collect(Collectors.toList());

        productService.createProduct(insertProductData);

        return ResponseEntity.ok().build();
    }
}
