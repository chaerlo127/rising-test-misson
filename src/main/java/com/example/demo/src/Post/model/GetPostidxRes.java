package com.example.demo.src.Post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostidxRes {
    private int postIdx;
    private String postTitle;
    private String postContent;
    private String locationName;
    private int userIdx;
    private String nickName;
    private int postLike;
    private int postComment;
    private String updateAt;
    private List<GetPostImgRes> postImg;
}
