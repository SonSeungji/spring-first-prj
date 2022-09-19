package com.example.demo.user.dto;

import com.example.demo.user.domain.Team;
import com.example.demo.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class TeamAllDataAndUserIdDto {

    //key
    private int no;
    private String name;
    private UserDto userTableInfo;
    private String userTableInfoUserID;

    @Builder
    public TeamAllDataAndUserIdDto(int no, String name, UserDto userTableInfo, String userTableInfoUserID){
        this.no = no;
        this.name = name;
        this.userTableInfo = userTableInfo;
        this.userTableInfoUserID = userTableInfoUserID;
    }

    public Team toEntity(){
        return Team.builder()
                //value
                .no(this.no)
                .name(this.name)
                .user(this.userTableInfo.toEntity())
                .build();
    }

    public static TeamAllDataAndUserIdDto toDto(Team team){
        return TeamAllDataAndUserIdDto.builder()
                //value
                .no(team.getNo())
                .name(team.getName())
                //.userTableInfo(UserAllDataAndTeamIdDto.toDto(team.getName()))
                //.userTableInfo(team.getUser())
                .userTableInfoUserID(team.getUser().getUserId())
                .build();
    }
}
