package com.example.demo.user.domain;

import com.example.demo.user.dto.TeamDto;
import com.example.demo.user.model.ActiveFlg;
import com.google.gson.Gson;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter // 입력한 값 받아옴
@Setter
@RequiredArgsConstructor
//@Data // getter setter 포함되어잇는 어노테이션
@Table(name = "user_table", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //index (자동 증가)
    @Column(name = "no", nullable = false)
    private int no;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_flg")
    private ActiveFlg activeFlg;

    //cascade > team 테이블에 데이터가 먼저 처리(insert 등)된 이후에 user 테이블의 데이터가 처리됨
    //FetchType.LAZY > getTeam()을 통해 team을 호출할 때만 조인됨
    //  FetchType.EAGER (default) : user와 매핑되어 있는 Team 테이블의 데이터를 항상 조인해서 가져옴
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no") // 외래키
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no") // 외래키
    private Company company;

    // @ManyToMany를 사용하면, 나중에 연결 테이블(중간 테이블)에 컬럼 추가 불가
    //@ManyToMany(mappedBy = "user")
    //private List<Product> product;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> order;

    @Builder //생성자에 parameter가 많아지면 사용하는 어노테이션..?
    public User(int no, String name, String userId, String userPassword, String email, ActiveFlg activeFlg, Team team, Company company) {
        this.no = no;
        this.name = name;
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.activeFlg = activeFlg;

        this.team = team;
        this.company = company;
    }

    public void updateUser(User user){
        this.userId = user.getUserId();
        this.userPassword = user.getUserPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.activeFlg = user.getActiveFlg();

        this.team.updateTeamData(user.getTeam());
        this.company.updateCompanyData(user.getCompany());
    }

    public void deleteUser(ActiveFlg active_flg){
        this.activeFlg = active_flg;
    }

    public void updateUserForDelete(){
        this.team = null;
    }








    public User(int no, String userId, String userPassword) {
        this.userId = UUID.randomUUID().toString();
    }

    public User(String name, String email) {
        //this(); //parameter가 없는 생성자 호출
        this.name = name;
        this.email = email;
    }

    public static User sample(){
        return new User("son", "sonsj@gmail.com");
    }

    public static void main(String[] args) {
        User user = new User("kim", "kimsj@gmail.com");

        //user 정보를 json 형태로 출력
        System.out.println(new Gson().toJson(user));
    }
}
