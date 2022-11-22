package com.example.demo.user.repository;

import com.example.demo.user.domain.User;
import com.example.demo.user.model.ActiveFlg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Spring Data JPA가 메서드 이름을 분석해서 JPQL을 생함래의 구문과 같은 의미)
    //"메서드 쿼리"로, select * from User where userid = ?의 역할을 함
    //받을 객체(User) 메서드 쿼리(findByUserid(String userid))
    //User findByUserId(String userId);
    User findByUserIdAndNo(String userId, int no);

    Optional<User> findByUserId(String userId);

    List<User> findByActiveFlg(ActiveFlg activeFlg);

    User findByTeamNo(int no);

    List<User> findByCompanyNo(int no);
}