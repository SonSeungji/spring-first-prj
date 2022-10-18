package com.example.demo.user.service;

import com.example.demo.user.domain.Team;
import com.example.demo.user.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

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
                deleteData -> teamRepository.delete(deleteData),
                () -> {
                    throw new RuntimeException("존재하지 않는 데이터 입니다.");
                }
        );
    }
}
