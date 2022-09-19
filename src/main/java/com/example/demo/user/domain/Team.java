package com.example.demo.user.domain;

import com.example.demo.user.dto.TeamDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "team_table")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private int no;

    @Column(name = "name")
    private String name;

    //mappedBy
    //  : User Entity 에서 변수로 사용하고 있는 team 이름
    //  : team 테이블의 연관 관계 주인이 user 테이블
    @OneToOne(mappedBy = "team", fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Team(int no, String name, User user) {
        this.no = no;
        this.name = name;

        this.user = user;
    }

    public void updateTeamData(Team team) {
        this.name = team.getName();

    }
}
