package com.example.demo.user.service;

import com.example.demo.user.domain.Team;
import com.example.demo.user.dto.TeamDto;
import com.example.demo.user.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public void createTeam(Team team){
        teamRepository.save(team);
    }

    public Team readTeam(int no) {
        return teamRepository.findById(no).get();
    }

    public void updateTeam(int no, Team team){
        Team team1 = teamRepository.findById(no).orElseThrow(RuntimeException::new);
        team1.updateTeamData(team);
        teamRepository.save(team1);
    }

    public void deleteTeam(int no){
        Team deleteTeamData = teamRepository.findById(no).get();
        teamRepository.delete(deleteTeamData);
    }
}
