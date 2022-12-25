package com.example.demo.src.Post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.Post.model.PostPostReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private final PostDao postDao;
    @Autowired
    private final JwtService jwtService;

    public PostService(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
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
}
