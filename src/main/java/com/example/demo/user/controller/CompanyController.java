package com.example.demo.user.controller;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.Team;
import com.example.demo.user.dto.CompanyDto;
import com.example.demo.user.dto.TeamDto;
import com.example.demo.user.dto.UserIncludedCompanyDto;
import com.example.demo.user.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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

    //파라미터로 받은 company에 속해있는 유저 조회
    @GetMapping("/info-company-user/{no}")
    public ResponseEntity readCompanyAndUser(@PathVariable int no){
        MultiValueMap companyEntity = companyService.readUserAndCompany(no);
        UserIncludedCompanyDto res = UserIncludedCompanyDto.toDto(companyEntity);

        return ResponseEntity.ok().body(res);
    }

    //각 company에 속해있는 유저 조회
    @GetMapping("/info-company-user")
    public ResponseEntity readCompanyAndUserAll(){
        MultiValueMap companyEntity = companyService.readUserAndCompanyAll();
        UserIncludedCompanyDto res = UserIncludedCompanyDto.toDto(companyEntity);

        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/update-company/{no}")
    public ResponseEntity updateTeam(@PathVariable Integer no, @RequestBody CompanyDto company){

        Company companyEntity = company.toEntity();
        companyService.updateCompany(no, companyEntity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete-company/{no}")
    public ResponseEntity deleteTeam(@PathVariable Integer no){

        companyService.deleteCompany(no);
        return ResponseEntity.ok().build();
    }
}
