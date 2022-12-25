package com.example.demo.src.Post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.Post.model.PostPostReq;
import com.example.demo.src.user.UserDao;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private final PostDao postDao;

    @Autowired
    private final UserDao userDao;

    @Autowired
    private final JwtService jwtService;

    public PostService(PostDao postDao, JwtService jwtService, UserDao userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public int postnewPost(PostPostReq postPostReq, int userIdx) throws BaseException {
        try{
            return this.postDao.createPost(postPostReq, userIdx);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void postnewPostImg(PostPostReq postPostReq, int postIdx) throws BaseException{
        try{
            this.postDao.saveAll(postPostReq, postIdx);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public boolean postUserLikePost(int userIdx, int postIdx) throws BaseException {
        try{
            if(this.userDao.getUsersByIdx(userIdx) == null){
                throw new BaseException(BaseResponseStatus.POST_USERS_DID_NOT_EXISTS);
            }
            if(this.postDao.getPostByPostIdx(postIdx) == null){
                throw new BaseException(BaseResponseStatus.POST_POSTS_DID_NOT_EXISTS);
            }
            if(this.postDao.getPostUserLikeByPostIdxAndUserIdx(userIdx, postIdx) == null){
                return this.postDao.createPostList(userIdx, postIdx);
            } else{
                return this.postDao.deletePostLikeByPostIdxAndUserIdx(userIdx, postIdx);
            }

        } catch(Exception e){
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
