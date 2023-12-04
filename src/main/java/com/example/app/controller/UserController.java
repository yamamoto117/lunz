package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Login;
import com.example.app.domain.User;
import com.example.app.login.LoginStatus;
import com.example.app.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute(new Login());
		return "auth/login";
	}
	
	@PostMapping("/login")
	public String login(HttpSession session, @Valid Login login, Errors errors) throws Exception {
		if (errors.hasErrors()) {
			return "auth/login";
		}

		User user = service.getUserByLoginId(login.getLoginId());
		if (user == null || !login.isCorrectPassword(user.getLoginPass())) {
			errors.rejectValue("loginId", "error.incorrect_id_or_password");
			return "auth/login";
		}

		LoginStatus loginStatus = new LoginStatus(user.getId(), user.getLoginId(), user.getName());
		session.setAttribute("loginStatus", loginStatus);
		return "redirect:/";
	}
	
	@GetMapping("/guest-login")
	public String guestLogin(HttpSession session) {
	    Integer guestUserId = 1;
	    String guestUserLoginId = "guestuser";
	    String guestUserName = "ゲストユーザー";

	    LoginStatus loginStatus = new LoginStatus(guestUserId, guestUserLoginId, guestUserName);
	    session.setAttribute("loginStatus", loginStatus);
	    return "redirect:/";
	}
	
	@GetMapping("/register")
	public String register(Model model) throws Exception {
		model.addAttribute(new User());
		return "auth/register";
	}
	
	@PostMapping("/register")
	public String register(HttpSession session, @Valid User user, BindingResult bindingResult, Errors errors, RedirectAttributes redirectAttributes) throws Exception {
		if (errors.hasErrors()) {
			return "auth/register";
		}
		
        if (!user.getLoginPass().equals(user.getLoginPassConfirm())) {
            bindingResult.rejectValue("loginPassConfirm", "user.password.mismatch", "パスワードが一致しません。");
            return "auth/register";
        }
		
		String hashedPassword = BCrypt.hashpw(user.getLoginPass(), BCrypt.gensalt());
		user.setLoginPass(hashedPassword);

		service.addUser(user);
		
		User newUser = service.getUserByLoginId(user.getLoginId());
		
		LoginStatus loginStatus = new LoginStatus(newUser.getId(), newUser.getLoginId(), newUser.getName());
		session.setAttribute("loginStatus", loginStatus);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.removeAttribute("loginStatus");
		return "redirect:/";
	}

}

