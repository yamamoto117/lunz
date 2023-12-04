package com.example.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Review;
import com.example.app.login.LoginStatus;
import com.example.app.service.ReviewService;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	private ReviewService service;
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @ModelAttribute
    public void commonAttributes(@PathVariable String placeId, Model model) {
        model.addAttribute("placeId", placeId);
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
    }
    
	@GetMapping("/{placeId}")
	public String detailRestaurant(@PathVariable String placeId, Model model) throws Exception {
		List<Review> reviews = service.getReviewsByPlaceId(placeId);

		double averageRating = reviews.stream()
		    .mapToDouble(Review::getRating)
		    .average()
		    .orElse(0.0);
		
		averageRating = Math.round(averageRating * 10) / 10.0;
		
		int reviewCount = reviews.size();

		model.addAttribute("averageRating", averageRating);
		model.addAttribute("reviews", service.getReviewsByPlaceId(placeId));
		model.addAttribute("reviewCount", reviewCount);
	    return "restaurant-detail";
	}
	
	@GetMapping("/{placeId}/reviews/add")
	public String addGet(Model model) throws Exception {
	    model.addAttribute("title", "口コミ作成");
	    model.addAttribute("review", new Review());
	    return "reviews/save";
	}
	
	@PostMapping("/{placeId}/reviews/add")
	public String addPost(@Valid Review review, Errors errors, RedirectAttributes rd, Model model, HttpSession session, @PathVariable String placeId) throws Exception {
		if (errors.hasErrors()) {
			model.addAttribute("title", "口コミ作成");
			return "reviews/save";
		}
		LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
	    
	    if (loginStatus != null) {
	        review.setUserId(loginStatus.getId());
	    }
	    
	    review.setPlaceId(placeId);
	    
		service.addReview(review);
		return "redirect:/restaurant/{placeId}";
	}
	
	@GetMapping("/{placeId}/reviews/edit/{id}")
	public String editGet(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("title", "口コミ編集");
		model.addAttribute("review", service.getReviewById(id));
		return "reviews/save";
	}

	@PostMapping("/{placeId}/reviews/edit/{id}")
	public String editPost(@PathVariable Integer id, @Valid Review review, Errors errors, RedirectAttributes rd,
			Model model) throws Exception {
		if (errors.hasErrors()) {
			model.addAttribute("title", "口コミ編集");
			return "reviews/save";
		}
		service.editReview(review);
		return "redirect:/restaurant/{placeId}";
	}
	
	@GetMapping("/{placeId}/reviews/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws Exception {
		service.deleteReview(id);
		return "redirect:/restaurant/{placeId}";
	}

}