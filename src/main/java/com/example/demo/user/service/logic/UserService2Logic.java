package com.example.demo.user.service.logic;

import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserStore;
import com.example.demo.user.service.UserService2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //UserService을 스프링 bean으로 등록하기 위해 어노테이션 사용
@RequiredArgsConstructor
public class UserService2Logic implements UserService2 {

    //final : 반드시 초기화 되어야 하고, 한 번 초기화 되면 값을 바꿀 수 없음
    // -> 선언과 동시에 값이 정해지는 것이 아니라, final이 붙은 변수 등에 값이 들어오면, 들어온 값으로 고정이 된다는 의미
    //아래의 final이 붙은 값을 초기화 시켜주어야 하는데, 어노테이션으로 @RequiredArgsConstructor를 사용하면 생성자가 추가됨
    //생성자는 아래와 같은 형태
    //  public UserService(UserStore userStore){
    //      this.userStore = userStore;
    //  }
    private final UserStore userStore;

    //UserService가 참조하는 데이터는 UserStore내 UserStoreLogic이라는 구현체
    //UserStore를 가지고, 구현체인 UserStoreLogic를 사용하기 위해서는
    //UserStoreLogic를 스프링 bean으로 등록하여 UserStore에 주입해야함 (Spring IOC의 DI를 통해 주입 가능)
    //주입하는 방식으로는 아래의 방법이 있음
    //  1. @Autowired 어노테이션 사용
    //      UserStore라는 인터페이스를 구현하고 있는 구현체인 UserStoreLogic이 주입됨(사용할 수 있게 됨)
    //      ex) @Autowired
    //          private final UserStore userStore;
    //  2. 생성자 사용
    //      // 생성자 parameter를 userStore를 받음
    //      public UserService(UserStore userStore){
    //          //UserService 클래스가 생성될 때, 생성자를 통해서 UserStore라는 인터페이스를 구현한 구현체가 주입됨
    //          //@Autowired를 따로 사용하지 않아도 생성자를 통한 bean 객체의 주입이 이루어짐
    //          this.userStore = userStore;
    //      }
    //  3. lombok 사용
    //      private final UserStore userStore; 선언
    //      final이 붙으면 반드시 초기화가 되어야 함
    //      그래서, 클래스 상단에 @RequiredArgsConstructor 사용
    //      @RequiredArgsConstructor를 사용하면 생성자가 자동 생성됨
    //      생성자의 형태는 <2. 생성자 사용>과 동일한 형태임



    public String register(User newUser) {
        return this.userStore.create(newUser);
    }

    public void modify(User newUser) {
        this.userStore.update(newUser);
    }

    public void remove(String id){
        this.userStore.delete(id);
    }

    public User find(String id) {
        return this.userStore.retrieve(id);
    }

    public List<User> findAll() {
        return this.userStore.retrieveAll();
    }
}