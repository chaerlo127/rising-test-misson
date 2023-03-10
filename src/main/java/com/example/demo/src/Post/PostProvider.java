package com.example.demo.src.Post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.Post.model.GetPostRes;
import com.example.demo.src.Post.model.GetPostidxRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostProvider {

    @Autowired
    private final PostDao postDao;
    @Autowired
    private final JwtService jwtService;

    public PostProvider(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
    }

    public List<GetPostRes> getPostAll() throws BaseException {
        try{
            return this.postDao.getPostAll();
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetPostidxRes> getPostByPostIdx(int postIdx) throws BaseException{
        try{
            return this.postDao.getPostByPostIdx(postIdx);
        }catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetPostidxRes> getPostByUserIdx(int userIdx) throws BaseException{
        try{
            return this.postDao.getPostByUserIdx(userIdx);
        }catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
