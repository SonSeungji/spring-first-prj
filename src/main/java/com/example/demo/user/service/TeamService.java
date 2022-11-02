package com.example.demo.user.service;

import com.example.demo.user.domain.Team;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.TeamRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public void createTeam(Team team){

        //insert하려는 데이터가 DB에 존재하는지 확인
        teamRepository.findByName(team.getName()).
                ifPresent(data -> {
                    throw new RuntimeException("이미 존재하는 데이터입니다.");
                });
        teamRepository.save(team);
    }

    public Team readTeam(int no) {
        //조회하려는 데이터가 DB에 존재하지 않으면 예외 처리
        return teamRepository.findById(no).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 데이터 입니다.");
        });
    }

    public void updateTeam(int no, Team team){
        Team targetTeamData = teamRepository.findById(no).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 데이터 입니다.");
        });
        targetTeamData.updateTeamData(team);
        teamRepository.save(targetTeamData);
    }

    public void deleteTeam(int no){
        teamRepository.findById(no).ifPresentOrElse(
                deleteData -> {
                    // user 테이블의 team_id(fk)를 null로 업데이트
                    User byTeamNo = userRepository.findByTeamNo(no);
                    if(byTeamNo != null){
                        byTeamNo.updateUserForDelete();
                        userRepository.save(byTeamNo);
                    }

                    // team 테이블의 데이터 삭제
                    teamRepository.deleteById(no);
                },
                () -> {
                    throw new RuntimeException("존재하지 않는 데이터 입니다.");
                }
        );
    }
}
