package com.example.demo.user.dto;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter @Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class UserIncludeCompanyDto {
    private Set companyName;
    private Collection<User> companyUser;

    @Builder
    public UserIncludeCompanyDto(Set companyName, Collection<User> companyUser) {
        this.companyName = companyName;
        this.companyUser = companyUser;
    }

    public static UserIncludeCompanyDto toDto(MultiValueMap company){
        return UserIncludeCompanyDto.builder()
                .companyName(company.keySet())
                .companyUser(company.values())
                .build();
    }
}