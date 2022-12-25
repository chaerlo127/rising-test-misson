package com.example.demo.src.Post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.Post.model.GetPostRes;
import com.example.demo.src.Post.model.PostPostReq;
import com.example.demo.utils.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;

    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService) {
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    /**
     * [POST] 글 작성
     * BaseResponse<String>
     * @param postPostReq
     * @param userIdx
     * @return
     */
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<String> postnewPost (@RequestBody PostPostReq postPostReq, @PathVariable("userIdx") int userIdx){
        if(postPostReq.getPostTitle() == null || postPostReq.getPostContent() == null || postPostReq.getLocationIdx() == 0) {
            return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
        }
        try {

            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int postIdx = this.postService.postnewPost(postPostReq, userIdx);
            if(postPostReq.getPostImg()!=null){
                this.postService.postnewPostImg(postPostReq, postIdx);
            }
            return new BaseResponse<>("성공적으로 생성되었습니다");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostRes>> getPostAll(){
        try {
            return new BaseResponse<>(this.postProvider.getPostAll());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
