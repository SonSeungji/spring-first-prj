package com.example.demo.user.controller;

import com.example.demo.user.domain.Company;
import com.example.demo.user.dto.CompanyDto;
import com.example.demo.user.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create-company")
    public ResponseEntity createCompany(@RequestBody CompanyDto companyDto) {

        Company companyEntity = companyDto.toEntity();
        companyService.createCompany(companyEntity);
        return ResponseEntity.ok().build();
    }

}
