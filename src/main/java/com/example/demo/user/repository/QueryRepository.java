package com.example.demo.user.repository;

import com.example.demo.user.domain.QUser;
import com.example.demo.user.domain.User;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.example.demo.user.domain.QUser.user;

@Repository
public class QueryRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> searchUserCompanyCount() {
        JPQLQueryFactory query = new JPAQueryFactory(entityManager);
        QUser searchUserData = user;
        return query.select(searchUserData)
                .from(searchUserData)
                .where(
                        searchUserData.company.no.eq(
                            searchUserData.company.no
                        )
                )
                .fetch();
    }

    public List<User> searchUser(String userID, String email) {
        JPQLQueryFactory query = new JPAQueryFactory(entityManager);
        QUser searchUserData = user;
        return query.select(searchUserData)
                .from(searchUserData)
                .where(
                        searchUserData.userId.startsWith(userID),
                        searchUserData.email.contains(email)
                )
                .orderBy(searchUserData.userId.desc())
                .fetch();
    }
}
