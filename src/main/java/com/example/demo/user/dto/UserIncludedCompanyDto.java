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
public class UserIncludedCompanyDto {

    private MultiValueMap<String, String> userAndCompanyData = new LinkedMultiValueMap<>();

    @Builder
    public UserIncludedCompanyDto(MultiValueMap<String, String> userAndCompanyData) {
        this.userAndCompanyData = userAndCompanyData;
    }

    public static UserIncludedCompanyDto toDto(MultiValueMap company){
        return UserIncludedCompanyDto.builder()
                .userAndCompanyData(company)
                .build();
    }
}