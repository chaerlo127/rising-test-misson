package com.example.demo.src.Post;

import com.example.demo.src.Post.model.GetPostRes;
import com.example.demo.src.Post.model.PostImgReq;
import com.example.demo.src.Post.model.PostPostReq;
import com.example.demo.src.user.model.GetUserRes;
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

    public List<GetPostRes> getPostAll() {
        String getPostQuery = "select Post.postIdx, postTitle, postContent, L.name, U.userIdx, U.nickName, PI.postImgUrl,\n" +
                "       IF(postLikeCount is null, 0, postLikeCount) as PostLike,\n" +
                "       IF(postComment is null, 0, postComment) as PostComment,\n" +
                "       case when timestampdiff(second, Post.updateAt, current_timestamp) < 60 then concat(timestampdiff(second, Post.updateAt, current_timestamp), '초 전')\n" +
                "       when timestampdiff(minute, Post.updateAt, current_timestamp) < 60 then concat(timestampdiff(minute, Post.updateAt, current_timestamp), '분 전')\n" +
                "       when timestampdiff(hour, Post.updateAt, current_timestamp) < 60 then concat(timestampdiff(hour, Post.updateAt, current_timestamp), '시간 전')\n" +
                "       when timestampdiff(day, Post.updateAt, current_timestamp) < 60 then concat(timestampdiff(day, Post.updateAt, current_timestamp), '일 전')\n" +
                "       else timestampdiff(year, Post.updateAt, current_timestamp) end as updatedAt\n" +
                "from Post\n" +
                "inner join User U on Post.userIdx = U.userIdx\n" +
                "inner join Location L on Post.locationIdx = L.locationIdx\n" +
                "left join PostImg PI on Post.postIdx = PI.postIdx\n" +
                "left join (select postIdx, userIdx, count(userIdx) as postLikeCount from PostLike WHERE status = 'ACTIVE' group by postIdx) PL on Post.postIdx = PL.postIdx\n" +
                "left join (select postIdx, userIdx, count(userIdx) as postComment from PostComment WHERE status = 'ACTIVE' group by postIdx) PC on Post.postIdx = PC.postIdx\n" +
                "group by Post.postIdx;";
        return this.jdbcTemplate.query(getPostQuery,
                (rs,rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getString("postTitle"),
                        rs.getString("postContent"),
                        rs.getString("name"),
                        rs.getInt("userIdx"),
                        rs.getString("nickName"),
                        rs.getString("postImgUrl"),
                        rs.getInt("PostLike"),
                        rs.getInt("PostComment"),
                        rs.getString("updatedAt")
                ));
    }
}
