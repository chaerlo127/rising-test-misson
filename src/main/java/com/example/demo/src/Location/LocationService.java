package com.example.demo.src.Location;

import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private final LocationDao locationDao;
    @Autowired
    private final JwtService jwtService;

    public LocationService(LocationDao postDao, JwtService jwtService) {
        this.locationDao = postDao;
        this.jwtService = jwtService;
    }
}
