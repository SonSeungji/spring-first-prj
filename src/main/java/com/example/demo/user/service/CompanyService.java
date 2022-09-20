package com.example.demo.user.service;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.CompanyRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public void createCompany(Company company){
        companyRepository.save(company);
    }

    public Company readCompany(int no){
        return companyRepository.findById(no).get();
    }

    public MultiValueMap readUserAndCompany(int no){

        Company companyData = companyRepository.findById(no).get();

        MultiValueMap<String, String> userAndCompanyData = new LinkedMultiValueMap<>();
        for(User userData : companyData.getUsers()){
            userAndCompanyData.add(userData.getCompany().getName(), userData.getUserId());
        }

        return userAndCompanyData;
    }
}
