package com.example.demo.user.dto;

import com.example.demo.user.domain.Team;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor
public class TeamDto {

    //key
    private int no;
    private String name;

    @Builder
    public TeamDto(int no, String name){
        this.no = no;
        this.name = name;
    }

    public Team toEntity(){
        return Team.teamDtoBuilder()
                //value
                .no(this.no)
                .name(this.name)
                .build();
    }

    public static TeamDto toDto(Team team){
        return TeamDto.builder()
                //value
                .no(team.getNo())
                .name(team.getName())
                .build();
    }
}
