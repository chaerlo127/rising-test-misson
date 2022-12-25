package com.example.demo.src.Post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostImgRes {
    private int postImgIdx;
    private String postImgUrl;
}
