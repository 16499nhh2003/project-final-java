package com.project.spring.controllers;

import com.project.spring.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/paypal")
public class PayPalController {
    private final PayPalService payPalService;

    @Autowired
    public PayPalController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @GetMapping("/payment")
    public String showPaymentForm(@RequestParam BigDecimal total, Model model) {
        model.addAttribute("total", total);
        return "payment";
    }

    @PostMapping("/create-payment")
    public String createPayment(@RequestParam BigDecimal total) {
        String redirectUrl = payPalService.createPayment("USD", total);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        } else {
            // Handle error
            return "error";
        }
    }
    @PostMapping("/payment-success")
    public String paymentSuccess() {
        return "/checkoutsuccess";
    }

    // ... Các phương thức xử lý khác ...
}
