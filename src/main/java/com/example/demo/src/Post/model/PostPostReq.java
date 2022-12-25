package com.example.demo.src.Post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostPostReq {
    private String postTitle;
    private String postContent;
    private int locationIdx;
    private List<PostImgReq> postImg;
}
