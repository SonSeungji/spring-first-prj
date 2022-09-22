package com.example.demo.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Getter @Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class UserIncludeCompanyDto {

    private MultiValueMap<String, String> userAndCompanyData = new LinkedMultiValueMap<>();

    @Builder
    public UserIncludeCompanyDto(MultiValueMap<String, String> userAndCompanyData) {
        this.userAndCompanyData = userAndCompanyData;
    }

    public static UserIncludeCompanyDto toDto(MultiValueMap company){
        return UserIncludeCompanyDto.builder()
                .userAndCompanyData(company)
                .build();
    }
}