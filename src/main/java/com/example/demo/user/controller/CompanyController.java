package com.example.demo.user.controller;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.CompanyDto;
import com.example.demo.user.dto.TeamAllDataAndUserIdDto;
import com.example.demo.user.dto.UserIncludeCompanyDto;
import com.example.demo.user.service.CompanyService;
import com.example.demo.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

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

    //company만 조회
    @GetMapping("/info-company/{no}")
    public ResponseEntity readCompany(@PathVariable int no){

        Company companyEntity = companyService.readCompany(no);
        CompanyDto res = CompanyDto.toDto(companyEntity);
        return ResponseEntity.ok().body(res);
    }

    //company에 속해있는 유저까지 조회
    @GetMapping("/info-company-user/{no}")
    public ResponseEntity readCompanyAndUser(@PathVariable int no){
        MultiValueMap companyEntity = companyService.readUserAndCompany(no);
        UserIncludeCompanyDto res = UserIncludeCompanyDto.toDto(companyEntity);

        return ResponseEntity.ok().body(res);
    }
}
