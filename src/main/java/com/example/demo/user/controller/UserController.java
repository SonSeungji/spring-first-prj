package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.dto.TeamDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.model.ActiveFlg;
import com.example.demo.user.model.ListWrapper;
import com.example.demo.user.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {

//    @RequestMapping(value = "/home")
//    public String home(){
//
//        //return "index.html";
//        return "index!!";
//    }

    private final UserService userService;

    //@RequestMapping("/create-user")  <- /create-user가 post인지 get인지 보지 않고, /create-user에 접속하면 해당 메소드 실행
    @PostMapping("/create-user") // /create-user가 post방식으로 되어 있으면 해당 메소드 실행
    public ResponseEntity createUser(@RequestBody UserDto user) { // 1. UserDto로 부터 api에서 json값을 받음

        // 2. db에 알맞은 형태로 값을 넣기 위해서는, 1에서 받은 json 형태의 값을 entity 형태로 변환해야함, 그 역할을 user.toEntity()에서 함
        User userEntity = user.toEntity();

        // 3. userService내 createUser에 2에서 변환한 엔티티 데이터를 넘겨줌
        userService.createUser(userEntity);

        // 4. 해당 컨트롤러 실행되면 200 반환?
        return ResponseEntity.ok().build();
    }


    @PostMapping("/create-users")
    public ResponseEntity createUsers(@RequestBody ListWrapper<UserDto> users) {

        System.out.println(users.getUsers());
        List<User> insertUserData = users.getUsers().stream()
                .map(userData -> userData.toEntity())
                //.filter(user -> user.getUserId().equals(1))
                .collect(Collectors.toList());

        List<UserListDto> duplicateUserData = new ArrayList();
        for (User userTempData : userService.createUsers(insertUserData)){
            duplicateUserData.add(UserListDto.toDto(userTempData));
        }
        return ResponseEntity.ok().body(duplicateUserData);
    }


    @PostMapping("/create-users-and-company")
    public ResponseEntity createManyUserOneCompany(@RequestBody ListWrapper<UserDto> users) {

        List<User> insertUserData = users.getUsers().stream()
                .map(userData -> userData.toEntity())
                .collect(Collectors.toList());

        List<UserListDto> duplicateUserData = new ArrayList();
        for (User userTempData : userService.createManyUserOneCompany(insertUserData)){
            duplicateUserData.add(UserListDto.toDto(userTempData));
        }
        return ResponseEntity.ok().body(duplicateUserData);
    }


    @PutMapping("/update-user")
    public ResponseEntity updateUser (@RequestBody UserDto user) {

        User userEntity = user.toEntity();
        userService.updateUser(userEntity);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/info-user/{no}")
    //                                              int로 하면 값이 무조건 들어와야함
    //                                              Integer(wrapper class)로 하면 null 값이 들어올 수도 있다는 의미
    //                                               ex) 유저 필터 기능이라고 하면, 검색할 유저의 아이디는 입력하면 값이 반환되지만, 미입력해도 에러가 나는 것은 아니기 때문에, wrapper class 사용하는 것
    public ResponseEntity readUser (@PathVariable Integer no) {

        //DB로 부터 유저 데이터 받아옴
        User userData = userService.readUser(no);

        //받은 데이터 중, response로 보낼 값만 선별? (response로 보낼 값은 dto에서 선정)
        UserDto userInfoData = UserDto.toDto(userData);

        return ResponseEntity.ok().body(userInfoData);
        //return ResponseEntity.ok().body(new Gson().toJson(userData));
    }


    @GetMapping("/info-user/active-status")
    public ResponseEntity updateUserActiveFlg (@RequestParam(name = "active_flg") ActiveFlg activeFlg) {
        List<User> userData = userService.readDisableUser(activeFlg);

        List<UserListDto> userInfoData = new ArrayList();
        for(User disabledUser:userData){
            userInfoData.add(UserListDto.toDto(disabledUser));
        }
        return ResponseEntity.ok().body(userInfoData);
    }


    @PutMapping("/delete-user/{no}")
    //                                                  required = false -> null 값 허용됨 (true로 하면 null체크됨)
    public ResponseEntity deleteUser (@RequestParam(name = "active_flg", required = false) ActiveFlg active_flg, @PathVariable int no) {
        userService.deleteUser(active_flg, no);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/hard-delete-user/{no}")
    public ResponseEntity hardDeleteUser(@PathVariable Integer no){

        userService.hardDeleteUser(no);
        return ResponseEntity.ok().build();
    }

}