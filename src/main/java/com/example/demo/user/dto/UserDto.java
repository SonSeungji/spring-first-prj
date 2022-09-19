package com.example.demo.user.dto;

import com.example.demo.user.domain.User;
import com.example.demo.user.model.ActiveFlg;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

    //key
    private int no;
    private String name;
    private String userId;
    private String userPassword;
    private String email;
    private ActiveFlg activeFlg;
    private List<UserDto> users;
    private TeamDto teamTableData;
    private CompanyDto companyTableData;

    @Builder
    public UserDto(int no, String name, String userId, String userPassword, String email, ActiveFlg activeFlg,
                   List<UserDto> users, TeamDto teamTableData, CompanyDto companyTableData) {
        //위에서 선언만 하고, 아래와 같이 생성자에서 초기화를 하는 이유는, 해당 클래스가 실행이 되었을 때, 메모리에 초기화된 값을 넣기 위해
        //선언과 동시에 초기화를 하면, 해당 클래스가 실행되지 않아도 초기화된 값이 메모리에 올라감
        this.no = no;
        this.name = name;
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.activeFlg = activeFlg;
        this.users = users;
        this.teamTableData = teamTableData;
        this.companyTableData = companyTableData;
    }

    public User toEntity(){
        //User 엔티티의 builder가 호출됨
        //builder내 선언되어 있는 컬럼에 대한 value 설정 가능
        //컨트롤러가 받은 this.name을, User 엔티티의 builder 내에 넣음
        return User.builder()
                //builder와 이름(column) 일치시켜야함
                .no(this.no)
                .name(this.name)
                .userId(this.userId)
                .userPassword(this.userPassword)
                .email(this.email)
                .activeFlg(this.activeFlg)
                .team(this.teamTableData.toEntity())
                .company(this.companyTableData.toEntity())
                .build();
    }

    public static UserDto toDto(User user){
        return UserDto.builder()
                //value
                .name(user.getName())
                .email(user.getEmail())
                .activeFlg(user.getActiveFlg())
                .teamTableData(TeamDto.toDto(user.getTeam())) //FetchType.LAZY

                //teamDto.toDto에 user정보+team정보가 있는 userDto를 불러와서 아래처럼 나옴
                //                              "no": 0,
                                        //    "name": "default",
                                        //    "user_id": null,
                                        //    "user_password": null,
                                        //    "email": "sonsj@gmail.com",
                                        //    "active_flg": "ENABLE",
                                        //    "users": null,
                                        //    "team_table_data": {
                                        //        "no": 1,                      <- 별도의 dto 만들어서 사용
                                        //        "name": "team name test",
                                        //        "user_table_info": null
                                        //    }
                //}
                .build();
    }
}
