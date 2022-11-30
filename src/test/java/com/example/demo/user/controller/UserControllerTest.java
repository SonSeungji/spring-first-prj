package com.example.demo.user.controller;

import com.example.demo.user.domain.*;
import com.example.demo.user.model.ActiveFlg;
import com.example.demo.user.repository.CompanyRepository;
import com.example.demo.user.repository.TeamRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired // ContentService를 주입받음
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CompanyRepository companyRepository;

//    @BeforeAll
//    static void beforeAll() {
//    }

//    @BeforeEach
//    void beforeEach() {
//    }

    @Test
    @Order(1)
    @DisplayName("createUser_valid")
    void createUser_valid(){

        //유저 데이터 set
        User user = new User();
        user.setUserId("createTest_userId");
        user.setUserPassword("createTest_userPw");
        user.setActiveFlg(ActiveFlg.ENABLE);

        //유저 데이터 insert
        userService.createUser(user);

        //입력한 데이터가 정상 insert되었는지 확인
        assertThat(user.getNo()).isNotEqualTo(null);
    }

    @Test
    @Order(2)
    @DisplayName("createUser_invalid_duplicate")
    void createUser_invalid_duplicate() {

        //첫번쨰 유저 데이터 set
        User user1 = new User();
        user1.setUserId("test_userId1");
        user1.setUserPassword("test_pw1");
        user1.setActiveFlg(ActiveFlg.ENABLE);

        //두번째 유저 데이터 set
        User user2 = new User();
        user2.setUserId("test_userId1");
        user2.setUserPassword("test_pw2");

        //첫번째 유저를 insert
        userService.createUser(user1);

        //첫번째 유저와 같은 값으로 두번째 유저를 insert시, 예상한 예외 처리가 실행되는지 확인
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> {
                    userService.createUser(user2);
                });
        assertEquals(runtimeException.getMessage(), "이미 존재");
    }

    @Test
    @Order(3)
    @DisplayName("readUser_valid")
    void readUser_valid(){

        //유저 데이터 set
        User user = new User();
        user.setUserId("test_userId");
        user.setUserPassword("test_pw");

        //유저 데이터 insert
        userRepository.save(user);

        //유저 데이터 read
        User readUserData = userService.readUser(user.getNo());

        assertThat(readUserData.getNo()).isEqualTo(user.getNo());
        assertThat(readUserData.getUserId()).isEqualTo(user.getUserId());
        assertThat(readUserData.getUserPassword()).isEqualTo(user.getUserPassword());
    }

    @Test
    @Order(4)
    @DisplayName("readUser_invalid_notExistUser")
    void readUser_invalid_notExistUser(){

        //존재하지 않는 유저 데이터 read
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class,
                () -> {
                    userService.readUser(9999);
                });
        assertEquals(noSuchElementException.getMessage(), "존재하지 않는 유저입니다.");
    }

    @Test
    @Order(5)
    @DisplayName("updateUser_valid")
    void updateUser_valid(){

        String UserPwBeforeData = "test_pw_before";
        String UserPwAfterData = "test_pw_after";
        String UserNameAfterData = "test_name_after";

        //유저 데이터 set
        User user = new User();
        user.setUserId("updateTest_userId");
        user.setUserPassword(UserPwBeforeData);

        //유저 데이터 insert
        userRepository.save(user);

        //유저 데이터 update
        user.setUserPassword(UserPwAfterData);
        user.setName(UserNameAfterData);
        userService.updateUser(user);

        //데이터가 정상 변경되었는지 확인
        assertThat(user.getUserPassword()).isEqualTo(UserPwAfterData);
        assertThat(user.getName()).isEqualTo(UserNameAfterData);
    }

    @Test
    @Order(6)
    @DisplayName("updateUser_invalid_notExistUser")
    void updateUser_invalid_notExistUser(){

    }

    @Test
    @Order(7)
    @DisplayName("hardDeleteUser_valid_teamAndCompanyExist")
    void hardDeleteUser_valid_teamAndCompanyExist(){

        Team team = Team.builder()
                .name("deleteTest_teamName")
                .build();

        Company company = Company.builder()
                .name("deleteTest_companyName")
                .build();

        //유저 데이터 set
        User user = User.builder()
                .userId("deleteTest_userId")
                .userPassword("deleteTest_userPw")
                .team(team)
                .company(company)
                .build();

        //유저 데이터 insert
        userRepository.save(user);    //<-- 영속성에만 저장

        //////////entity manager flush
        //entityManager.flush();   //<-- db, 영속성에 모두 저장

        //////////entity manager clean
        //entityManager.clear();  //<-- 영속성 컨텍스트에 데이터 삭제

        //유저 데이터 delete
        userService.hardDeleteUser(user.getNo());

        //db에서 insert한 데이터 조회 후 조회한 데이터가 정상 삭제되었는지 확인
        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        assertThat(byUserId).isEmpty();
    }

    @Test
    @Order(8)
    @DisplayName("hardDeleteUser_valid_teamAndDuplicateCompanyExist")
    void hardDeleteUser_valid_teamAndDuplicateCompanyExist(){

        Team team = Team.builder()
                .name("deleteTest_teamName")
                .build();

        Company company = Company.builder()
                .name("deleteTest_companyName")
                .build();

        //첫번째 유저 데이터 set
        User userFirst = User.builder()
                .userId("deleteTest_userId1")
                .userPassword("deleteTest_userPw1")
                .team(team)
                .company(company)
                .build();

        //첫번째 유저 데이터 insert
        userRepository.save(userFirst);

        //두번째 유저 데이터 set
        User userSecond = User.builder()
                .userId("deleteTest_userId2")
                .userPassword("deleteTest_userPw2")
                .build();

        //두번째 유저 데이터 insert
        userRepository.save(userSecond);

        //두번째 유저에 첫번째 유저와 같은 company 데이터 set
        userSecond.setCompany(company);
        userRepository.save(userSecond);

        //유저 데이터 delete
        userService.hardDeleteUser(userFirst.getNo());

        //첫번째 유저가 정상 삭제되었는지 확인
        Optional<User> byFirstUserData = userRepository.findByUserId(userFirst.getUserId());
        assertThat(byFirstUserData).isEmpty();

        //두번째 유저의 company 데이터가 삭제되지 않고 존재하는지 확인
        Optional<User> bySecondUserData = userRepository.findByUserId(userSecond.getUserId());
        assertThat(bySecondUserData.get().getCompany()).isNotNull();
    }

    @Test
    @Order(9)
    @DisplayName("hardDeleteUser_valid_teamAndCompanyNotExist")
    void hardDeleteUser_valid_teamAndCompanyNotExist(){

        //유저 데이터 set
        User user = User.builder()
                .userId("deleteTest_userId")
                .userPassword("deleteTest_userPw")
                .build();

        //유저 데이터 insert
        userRepository.save(user);

        //유저 데이터 delete
        userService.hardDeleteUser(user.getNo());

        //db에서 insert한 데이터 조회 후 조회한 데이터가 정상 삭제되었는지 확인
        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        assertThat(byUserId).isEmpty();
    }

    @Test
    @Order(10)
    @DisplayName("hardDeleteUser_invalid_notExistUser")
    void hardDeleteUser_invalid_notExistUser(){
        //존재하지 않는 유저 데이터 delete시, 예상 에러 발생되는지 확인
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> {
                    userService.hardDeleteUser(9999);
                });
        assertEquals(runtimeException.getMessage(), "존재하지 않는 유저입니다.");
    }

//    @AfterEach
//    void afterEach() {
//    }

//    @AfterAll
//    static void afterAll() {
//    }
}