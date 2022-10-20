package com.example.demo.user.controller;

import com.example.demo.user.domain.Team;
import com.example.demo.user.dto.TeamDto;
import com.example.demo.user.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TeamController {

    //todo 1 : User Table <-> Team Table
    //  1:1 mapping
    //  양방향(user에서도 team조회 가능, team에서도 user조회 가능)
    //  연관관계의 주인은 User
    //      User 에서 Team의 CRUD 가능
    //      Team 에서 User의 R만 가능 (C, U, D 불가)
    // user삭제되면 team도 삭제되게 (cascade)
    private final TeamService teamService;


    @PostMapping("/create-team")
    public ResponseEntity createTeam(@RequestBody TeamDto team) {

        Team teamEntity = team.toEntity();
        teamService.createTeam(teamEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info-team/{no}")
    public ResponseEntity readTeam(@PathVariable Integer no){

        Team teamData = teamService.readTeam(no);
        TeamDto res = TeamDto.toDto(teamData);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/update-team/{no}")
    public ResponseEntity updateTeam(@PathVariable Integer no, @RequestBody TeamDto team){

        Team teamEntity = team.toEntity();
        teamService.updateTeam(no, teamEntity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete-team/{no}")
    public ResponseEntity deleteTeam(@PathVariable Integer no){

        teamService.deleteTeam(no);
        return ResponseEntity.ok().build();
    }
}