package com.example.demo.user.controller;

import com.example.demo.user.domain.Company;
import com.example.demo.user.dto.CompanyDto;
import com.example.demo.user.dto.TeamAllDataAndUserIdDto;
import com.example.demo.user.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/info-company/{no}")
    public ResponseEntity readCompany(@PathVariable int no){

        Company companyEntity = companyService.readCompany(no);
        CompanyDto res = CompanyDto.toDto(companyEntity);
        return ResponseEntity.ok().body(res);
    }
}
