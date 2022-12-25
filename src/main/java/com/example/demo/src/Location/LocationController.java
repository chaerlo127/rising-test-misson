package com.example.demo.src.Location;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Location.model.GetLocationListRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private final LocationProvider locationProvider;
    @Autowired
    private final LocationService locationService;
    @Autowired
    private final JwtService jwtService;

    public LocationController(LocationProvider postProvider, LocationService postService, JwtService jwtService) {
        this.locationProvider = postProvider;
        this.locationService = postService;
        this.jwtService = jwtService;
    }
    // location 구하는 것

    /**
     * location list 불러오는 API
     * (GET) 127.0.0.1:9000/locations
     * @return BaseResponse<List<GetLocationListRes>>
     */
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/locations
    public BaseResponse<List<GetLocationListRes>> getLocationList(){
        try {
            return new BaseResponse<>(this.locationProvider.getLocationList());
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }
}
