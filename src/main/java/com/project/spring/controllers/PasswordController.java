package com.project.spring.controllers;

import com.project.spring.model.AppUser;
import com.project.spring.service.EmailService;
import com.project.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("forgotpassword");
    }

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {
        Optional<AppUser> appUser = this.userService.findUserByEmail(userEmail);
        if (appUser.isEmpty()) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {
            AppUser user = appUser.get();
            user.setResetToken(UUID.randomUUID().toString());
            this.userService.save(user);
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("nguyenhuyhoa2003@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:" + appUrl + "/reset?token=" + user.getResetToken());
            emailService.sendEmail(passwordResetEmail);
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
        }
        modelAndView.setViewName("forgotpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        Optional<AppUser> appUser = userService.findUserByResetToken(token);
        if (appUser.isPresent()) {
            modelAndView.addObject("resetToken", token);
        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("forgotpassword");
            return modelAndView;
        }
        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams) {

        // Find the user associated with the reset token
        Optional<AppUser> user = userService.findUserByResetToken(requestParams.get("token"));
        // This should always be non-null but we check just in case
        if (user.isPresent()) {
            AppUser resetUser = user.get();
            // Set new password
            resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("passwordNew")));
            //Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);
            // Save user
            userService.save(resetUser);
            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            modelAndView.addObject("successMessage", "You have successfully reset your password.  You may now login.");
            modelAndView.setViewName("login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("resetPassword");
        }
        return modelAndView;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }

}
