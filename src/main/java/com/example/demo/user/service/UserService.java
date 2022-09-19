package com.example.demo.user.service;

import com.example.demo.user.domain.Company;
import com.example.demo.user.domain.Team;
import com.example.demo.user.domain.User;
import com.example.demo.user.model.ActiveFlg;
import com.example.demo.user.repository.CompanyRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Service //UserService을 스프링 bean으로 등록하기 위해 어노테이션 사용
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    //유저로 부터 받은 정보를 디비에 저장
    public void createUser(User user) {

        // todo: 1. User가 이미 등록되어 있는지 확인하고 이미 존재하면 exception을 발생시킨다 (아무거나)
        // todo: 1-1 User 확인 시 Repository 에서 Optional 사용해보기
        userRepository.findByUserId(user.getUserId())
            .ifPresent(m -> {
                throw new RuntimeException("이미 존재");
             });

       companyRepository.findByName(user.getCompany().getName())
               .ifPresent(m ->{
                   //user 테이블에서는 company_id만 외래키로 사용하지만,
                   // setCompany에 set하는 company는 오브젝트 형이여서, company에 들어있는 id, name을 모두 넘겨줘야함
                   user.setCompany(m);
               });

        // todo: 2. User를 저장한다.
        userRepository.save(user);

        //userRepository가 상속받고 있는 jpaRepository내 save 기능을 사용하여, user 데이터를 저장
        //userRepository.save(user);

    }


    public List<User> createUsers(List<User> user) {

        // todo: 유저 여러개 받아서 등록/업데이트 처리 (bulk)
        //  중복체크 (stream) <- 리스트로 받아온 값이여서 중복되는 값이 있을 수 있음
        //  중복 데이터를 response로 보여주기

        List<User> duplicateUserData = new ArrayList();
        for(User userData: user){
            userRepository.findByUserId(userData.getUserId())
                    ////save -> 영속성 컨텍스트로 등록됨(디비에 넣을려고하는값과 동일한 값이 들어가있음) -> 디비 등록
                    //.orElseGet(() -> userRepository.save(userData));
                    .ifPresentOrElse(
                            userDataRes -> duplicateUserData.add(userData),
                            () -> userRepository.save(userData)
                    );
        }

        return duplicateUserData;
    }


    // todo: 유저 여러개 받아서 등록/업데이트 처리 (bulk)
    //  User(N) : Company(1)
    public List<User> createManyUserOneCompany(List<User> user) {

        List<User> duplicateUserData = new ArrayList();
        for(User userData: user){
            userRepository.findByUserId(userData.getUserId())
                    .ifPresentOrElse(
                            userDataRes -> duplicateUserData.add(userData),
                            () -> {
                                companyRepository.findByName(userData.getCompany().getName())
                                    .ifPresent(m -> {
                                        userData.setCompany(m);
                                    });
                                userRepository.save(userData);
                            }
                    );
        }

        return duplicateUserData;
    }


    public void updateUser(User userInputData) {

        // todo: 1. 입력받은 User 정보를 통하여 변경을 진행한다. (table ID를 사용하여 검색하지 않는다.)
        // todo: 1-1 User 확인 시 Repository 에서 Optional 사용해보기
        User userId = userRepository.findByUserId(userInputData.getUserId())
                .orElseThrow(RuntimeException::new);

        // todo: 2. UserID를 이용해서 정보가 존재하는지 확인하고 존재하지 않는 경우에는 exception을 발생시킨다.
        userId.updateUser(userInputData);

        userRepository.save(userId);


        // 변경이 될 오브젝트 (디비에 저장되어 있는 값 가져옴)
        //      userRepository는 optional로 정의되어 있어, 널 값 처리 가능( 아래의 처리는, id가 없으면 NullPointerException 발생)
        //User userTableData = userRepository.findById(no).orElseThrow(NullPointerException::new);

        // 오브젝트 데이터 변경
        //userTableData.updateUser(userInputData);

        // 변경 내용 저장
        //      repository.save()는 insert문과 update문 모두 실행 가능
        //      id 가 없으면 Transient 상태임을 인지하고, save로 저장시 persist() 메소드를 사용하게 된다.
        //      id 가 기존에 존재하면 Detached 상태임을 인지하고, save로 저장시 merge() 메소드를 사용한다.
        //userRepository.save(userTableData);
    }


    public User readUser(int no) {
        return userRepository.findById(no).get();
    }


    //todo) activeFlg가 enabled인 데이터만 가져와서 조회
    //      (파라메터로 입력받은 activeFlg를 db에서 조회해서 동일한 activeFlg인 데이터만 나오게 처리)
    public List<User> readDisableUser(ActiveFlg activeFlg) {

        // todo 1) db where문 (repository 사용)
        //List<User> disabledUser = userRepository.findByActiveFlg(activeFlg);

        // todo 2) service에서 stream 처리해서 activeFlg(입력받은 파라메터)와 동일한 값을 가진 유저 데이터가 나오게
        List<User> disabledUser = userRepository.findAll().stream()
                .filter(disabledUserFilter -> disabledUserFilter.getActiveFlg().equals(activeFlg))
                .collect(Collectors.toList());

        return disabledUser;
    }


    public void deleteUser(ActiveFlg active_flg, int no) {
        Optional<User> userTableData = userRepository.findById(no);
        userTableData.orElseThrow(RuntimeException::new).deleteUser(active_flg);

        userRepository.save(userTableData.get());
    }

    public void hardDeleteUser(int no){
        User deleteUserData = userRepository.findById(no).get();
        userRepository.delete(deleteUserData);
    }

}