package com.example.demo.user.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "team_table")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Integer no;

    @Column(name = "name")
    private String name;

    //mappedBy
    //  : User Entity 에서 변수로 사용하고 있는 team 이름
    //  : team 테이블의 연관 관계 주인이 user 테이블
    @OneToOne(mappedBy = "team", fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Team(Integer no, String name, User user) {
        this.no = this == null ? null : this.no;
        this.name = name;

        this.user = user;
    }

    @Builder(builderMethodName = "teamDtoBuilder")
    public Team(Integer no, String name) {
        this.no = this == null ? null : this.no;
        this.name = name;
    }

    public void updateTeamData(Team team) {
        this.name = team.getName();
    }
}
