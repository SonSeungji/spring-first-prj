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
@Table(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private int no;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

//    @ManyToMany
//    @JoinTable(name="user_product", joinColumns = @JoinColumn( name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name="user_id")) // <- 이렇게 테이블 만들면 매핑 테이블(중간 테이블)에 별도 컬럼 추가 불가
//    private List<User> user;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Order> order;

    @Builder
    public Product(int no, String name, String code) {
        this.no = no;
        this.name = name;
        this.code = code;
    }

}