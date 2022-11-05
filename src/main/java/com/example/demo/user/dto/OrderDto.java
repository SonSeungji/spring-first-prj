package com.example.demo.user.dto;

import com.example.demo.user.domain.Order;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class OrderDto {

    private int no;
    private UserDto userNo;
    private ProductDto productNo;

    @Builder
    public OrderDto(int no, UserDto userNo, ProductDto productNo){
        this.no = no;
        this.userNo = userNo;
        this.productNo = productNo;
    }

    public Order toEntity(){
        return Order.builder()
                .user(this.userNo == null ? null : this.userNo.toEntity())
                .product(this.productNo == null ? null : this.productNo.toEntity())
                .build();
    }
}
