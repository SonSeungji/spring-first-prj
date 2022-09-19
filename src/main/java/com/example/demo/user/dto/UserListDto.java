package com.example.demo.user.dto;

import com.example.demo.user.domain.User;
import com.example.demo.user.model.ActiveFlg;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserListDto {

    //key
    private String userId;
    private ActiveFlg activeFlg;

    @Builder
    private UserListDto(String userId, ActiveFlg activeFlg) {
        this.userId = userId;
        this.activeFlg = activeFlg;
    }

    public User toEntity(){
        return User.builder()
                .userId(this.userId)
                .activeFlg(this.activeFlg)
                .build();
    }

    public static UserListDto toDto(User user){
        return UserListDto.builder()
                //value
                .userId(user.getUserId())
                .activeFlg(user.getActiveFlg())
                .build();
    }
}
