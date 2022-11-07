package com.example.demo.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter @Setter
@RequiredArgsConstructor
public class UserOrderProductListDto {

    private MultiValueMap<String, String> userOrderProductList = new LinkedMultiValueMap<>();

    @Builder
    public UserOrderProductListDto(MultiValueMap userOrderProductList){
        this.userOrderProductList = userOrderProductList;
    }

    public static UserOrderProductListDto toDto(MultiValueMap resData){
        return UserOrderProductListDto.builder()
                .userOrderProductList(resData)
                .build();
    }
}
