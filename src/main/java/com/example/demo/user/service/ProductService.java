package com.example.demo.user.service;

import com.example.demo.user.domain.Product;
import com.example.demo.user.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(List<Product> product){

        for(Product insertProductData : product) {
            productRepository.findById(insertProductData.getNo())
                .ifPresent(data -> {
                    throw new RuntimeException("이미 존재하는 데이터입니다.");
                });

            productRepository.save(insertProductData);
        }

    }
}
