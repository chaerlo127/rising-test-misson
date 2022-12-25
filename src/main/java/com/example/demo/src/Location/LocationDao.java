package com.example.demo.src.Location;

import com.example.demo.src.Location.model.GetLocationListRes;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LocationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetLocationListRes> getLocationList() {
        String getUsersQuery = "select locationIdx,name from Location";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetLocationListRes(
                        rs.getInt("locationIdx"),
                        rs.getString("name")
                ));
    }
}
