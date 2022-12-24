package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String name;
    private String nickName;
    private String email;
    private String pwd;
    private String gender;
    private String phone;
}
