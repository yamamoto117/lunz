package com.example.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.service.ReviewService;

@Controller
public class HomeController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;
	
	@GetMapping
    public String showHome(Model model) throws Exception {
        Map<String, Double> averageRatings = reviewService.getAverageRatings();
        Map<String, Long> reviewCounts = reviewService.getReviewCounts();
        
        model.addAttribute("averageRatings", averageRatings);
        model.addAttribute("reviewCounts", reviewCounts);
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        
        return "home";
    }
	
	@GetMapping("/api/ratings")
    @ResponseBody
    public ResponseEntity<?> getRatings() {
        try {
            Map<String, Double> averageRatings = reviewService.getAverageRatings();
            Map<String, Long> reviewCounts = reviewService.getReviewCounts();

            Map<String, Object> response = new HashMap<>();
            response.put("averageRatings", averageRatings);
            response.put("reviewCounts", reviewCounts);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
