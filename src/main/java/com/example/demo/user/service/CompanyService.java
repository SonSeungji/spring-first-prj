package com.example.demo.user.service;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.Team;
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

        //insert하려는 데이터가 DB에 존재하는지 확인
        companyRepository.findByName(company.getName()).
                ifPresent(data -> {
                    throw new RuntimeException("이미 존재하는 데이터입니다.");
                });
        companyRepository.save(company);
    }

    public Company readCompany(int no){

        //조회하려는 데이터가 DB에 존재하지 않으면 예외 처리
        return companyRepository.findById(no).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 데이터 입니다.");
        });
    }

    public MultiValueMap readUserAndCompany(int no){

        //조회하려는 데이터가 DB에 존재하지 않으면 예외 처리
        Company companyData = companyRepository.findById(no).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 데이터 입니다.");
        });

        MultiValueMap<String, String> userAndCompanyData = new LinkedMultiValueMap<>();
        if(companyData.getUsers().isEmpty() == false) {
            for(User userData : companyData.getUsers()){
                userAndCompanyData.add(userData.getCompany().getName(), userData.getUserId());
            }
        }else {
            userAndCompanyData.add(companyData.getName(), "유저 데이터 없음");
        }

        return userAndCompanyData;
    }

    public MultiValueMap readUserAndCompanyAll(){

        List<Company> company = companyRepository.findAll();

        MultiValueMap<String, String> userAndCompanyData = new LinkedMultiValueMap<>();
        for(Company companyData: company){
            if(companyData.getUsers().isEmpty() == false) {
                for(User userData : companyData.getUsers()) {
                    userAndCompanyData.add(userData.getCompany().getName(), userData.getUserId());
                }
            }else{
                userAndCompanyData.add(companyData.getName(), "유저 데이터 없음");
            }
        }

        return userAndCompanyData;
    }

    public void updateCompany(int no, Company company){
        Company targetCompanyData = companyRepository.findById(no).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 데이터 입니다.");
        });
        targetCompanyData.updateCompanyData(company);
        companyRepository.save(targetCompanyData);
    }

    public void deleteCompany(int no){
        System.out.println(companyRepository.findById(no).get().getName());
        companyRepository.findById(no).ifPresentOrElse(
                deleteData -> companyRepository.delete(deleteData),
                () -> {
                    throw new RuntimeException("존재하지 않는 데이터 입니다.");
                }
        );
    }

}
