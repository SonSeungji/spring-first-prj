package com.example.demo.user.repository.logic;

import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserStore;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserStoreLogic implements UserStore {

    //key : id가 들어가기 때문에 String형
    //value : 실제 저장하는 객체 User를 사용
    private Map<String, User> userMap;

    public UserStoreLogic() {
        // private Map<String, User> userMap = new HashMap<>(); 이렇게 안하고 생성자 안에서 왜 초기화하는지??
        this.userMap = new HashMap<>();
    }

    @Override
    public String create(User newUser) {
        //userMap에 newUser.getUserid()가 존재하지 않기 때문에 신규 추가됨
        this.userMap.put(newUser.getUserId(), newUser);
        return newUser.getUserId();
    }

    @Override
    public void update(User newUser) {
        //userMap에 newUser.getUserid()가 존재하기 때문에, 해당 userid의 정보가 업데이트 됨
        this.userMap.put(newUser.getUserId(), newUser);
    }

    @Override
    public void delete(String userid) {
        this.userMap.remove(userid);
    }

    @Override
    public User retrieve(String userid) {
        return this.userMap.get(userid);
    }

    @Override
    public List<User> retrieveAll() {
        return this.userMap.values().stream().collect(Collectors.toList());
    }
}
