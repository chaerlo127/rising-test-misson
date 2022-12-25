package com.example.demo.src.Post;

import com.example.demo.src.Post.model.PostImgReq;
import com.example.demo.src.Post.model.PostPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost(PostPostReq postPostReq, int userIdx) {
        String createPostQuery = "insert into Post (postTitle, postContent, locationIdx, userIdx) VALUES (?,?,?,?)";
        Object[] createPostParams = new Object[]{postPostReq.getPostTitle(), postPostReq.getPostContent(), postPostReq.getLocationIdx(), userIdx};
        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public void saveAll(PostPostReq postPostReq, int postIdx) {
        int batchCount = 0;
        List<PostImgReq> subItems = new ArrayList<>();
        for (int i = 0; i < postPostReq.getPostImg().size(); i++) {
            subItems.add(postPostReq.getPostImg().get(i));
            batchCount = createPostImg(batchCount, subItems, postIdx);
        }
        if (!subItems.isEmpty()) {
            batchCount = createPostImg(batchCount, subItems, postIdx);
        }
        System.out.println("batchCount: " + batchCount);
    }

    public int createPostImg(int batchCount, List<PostImgReq> postPostReq, int postIdx) {
        jdbcTemplate.batchUpdate("insert into PostImg (postIdx, postImgUrl) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, postIdx);
                ps.setString(2, postPostReq.get(i).getPostImgUrl());
            }
            @Override
            public int getBatchSize() {
                return postPostReq.size();
            }
        });
        postPostReq.clear();
        batchCount++;
        return batchCount;
    }
}
