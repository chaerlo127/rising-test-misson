package com.example.demo.src.Post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.Post.model.GetPostRes;
import com.example.demo.src.Post.model.GetPostidxRes;
import com.example.demo.src.Post.model.PostCommentReq;
import com.example.demo.src.Post.model.PostPostReq;
import com.example.demo.utils.JwtService;
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


    /**
     * [GET] POST의 모든 정보 불러오기
     * @return BaseResponse<List<GetPostRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostRes>> getPostAll(){
        try {
            return new BaseResponse<>(this.postProvider.getPostAll());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * [POST] POSTIDX로 작성된 글 글 불러오기
     * @param postIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/{postIdx}")
    public BaseResponse<List<GetPostidxRes>> getPostByPostIdx(@PathVariable("postIdx") int postIdx){
        try {
            return new BaseResponse<>(this.postProvider.getPostByPostIdx(postIdx));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // user가 작성한 post 불러오기

    /**
     * [POST] USER가 작성한 글 불러오기
     * @param userIdx
     * @return
     */
    @ResponseBody
    @GetMapping("/user/{userIdx}")
    public BaseResponse<List<GetPostidxRes>> getPostByUserIdx(@PathVariable("userIdx") int userIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>(this.postProvider.getPostByUserIdx(userIdx));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * [POST] 사용자의 POST LIKE
     * @param userIdx
     * @param postIdx
     * @return String
     */
    @ResponseBody
    @PostMapping("/like")
    public BaseResponse<String> postUserLikePost(@RequestParam("userIdx") int userIdx, @RequestParam("postIdx") int postIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            boolean answer = this.postService.postUserLikePost(userIdx, postIdx);
            return new BaseResponse<>("좋아요 " + answer);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * [POST] 댓글 쓰기
     * @param userIdx
     * @param postIdx
     * @param postCommentReq
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    public BaseResponse<String> postComment(@RequestParam("userIdx") int userIdx, @RequestParam("postIdx") int postIdx, @RequestBody PostCommentReq postCommentReq){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>("CommentIdx: " + this.postService.postCommentFirst(userIdx, postIdx, postCommentReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
