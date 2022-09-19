package com.example.demo.user.service;

import com.example.demo.user.domain.Company;
import com.example.demo.user.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void createCompany(Company company){
        companyRepository.save(company);
    }

    public Company readCompany(int no){
        return companyRepository.findById(no).get();
    }
}
