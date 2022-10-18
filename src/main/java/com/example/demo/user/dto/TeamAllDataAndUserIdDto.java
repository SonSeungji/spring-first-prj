package com.example.demo.user.dto;

import com.example.demo.user.domain.Team;
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
    private String userId;

    @Builder
    public TeamAllDataAndUserIdDto(int no, String name, String userId){
        this.no = no;
        this.name = name;
        this.userId = userId;
    }

    public Team toEntity(){
        return Team.builder()
                //value
                .no(this.no)
                .name(this.name)
                .build();
    }

    public static TeamAllDataAndUserIdDto toDto(Team team){
        return TeamAllDataAndUserIdDto.builder()
                //value
                .no(team.getNo())
                .name(team.getName())
                .userId(team.getUser() == null ? "no data":team.getUser().getUserId())
                .build();
    }
}
