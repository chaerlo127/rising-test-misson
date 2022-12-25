package com.example.demo.src.Location;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.Location.model.GetLocationListRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationProvider {

    @Autowired
    private final LocationDao locationDao;
    @Autowired
    private final JwtService jwtService;

    public LocationProvider(LocationDao locationDao, JwtService jwtService) {
        this.locationDao = locationDao;
        this.jwtService = jwtService;
    }

    public List<GetLocationListRes> getLocationList() throws BaseException {
        try{
            return this.locationDao.getLocationList();
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
