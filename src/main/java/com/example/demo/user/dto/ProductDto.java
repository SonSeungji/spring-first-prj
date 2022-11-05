package com.example.demo.user.dto;


import com.example.demo.user.domain.Product;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class ProductDto {

    //key
    private int no;
    private String name;
    private String code;

    @Builder
    public ProductDto(int no, String name, String code){
        this.no = no;
        this.name = name;
        this.code = code;
    }

    public Product toEntity(){
        return Product.builder()
                .no(this.no)
                .name(this.name)
                .code(this.code)
                .build();
    }

    //value

}
