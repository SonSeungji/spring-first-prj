package com.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "company_table", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Integer no;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<User> users;

    @Builder
    public Company(Integer no, String name){
        this.no = this == null ? null : this.no;
        this.name = name;
    }

    public void updateCompanyData(Company company) {
        this.name = company.getName();

    }
}
