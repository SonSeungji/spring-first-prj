package com.example.demo.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private int no;

    // User 테이블이 PK를 가지고 Order 테이블이 FK를 가지므로 Order 테이블에 @ManyToOne 적용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no") // 외래키
    private User user;

    // Product 테이블이 PK를 가지고 Order 테이블이 FK를 가지므로 Product 테이블에 @ManyToOne 적용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no") // 외래키
    private Product product;

    @Column(name = "order_date")
    private Date orderDate;

    @Builder
    public Order(int no, User user, Product product, Date orderDate){
        this.no = no;
        this.user = user;
        this.product = product;
        this.orderDate = orderDate;
    }

}
