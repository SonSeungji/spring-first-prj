package com.example.demo.user.dto;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter @Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class CompanyDto {

    //key
    private Integer no;
    private String name;

    @Builder
    public CompanyDto(Integer no, String name){
        this.no = no;
        this.name = name;
    }

    public Company toEntity(){
        return Company.builder()
                .no(this.no)
                .name(this.name)
                .build();
    }

    public static CompanyDto toDto(Company company){
        return CompanyDto.builder()
                .no(company.getNo())
                .name(company.getName())
                .build();
    }
}
