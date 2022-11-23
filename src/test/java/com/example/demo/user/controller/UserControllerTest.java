package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.model.ActiveFlg;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired // ContentService를 주입받음
    UserService userService;

//    @BeforeAll
//    public static void beforeAll() {
//    }

//    @BeforeEach
//    public void beforeEach() {
//    }

    @Test
    @Order(1)
    @DisplayName("createUser_valid")
    void createUser_valid() throws Exception{

        //유저 데이터 set
        User user = new User();
        user.setUserId("test_userId");
        user.setUserPassword("test_pw");
        user.setActiveFlg(ActiveFlg.ENABLE);

        //유저 데이터 insert
        userService.createUser(user);

        //입력한 데이터가 정상 insert되었는지 확인
        assertThat(user.getNo()).isNotEqualTo(null);
    }

    @Test
    @Order(1)
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

//    @AfterEach
//    public void afterEach() {
//    }

//    @AfterAll
//    public static void afterAll() {
//    }
}