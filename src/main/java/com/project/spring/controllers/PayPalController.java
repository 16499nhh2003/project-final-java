package com.project.spring.controllers;

import com.project.spring.model.Order;
import com.project.spring.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "/payment"; // Điều hướng đến trang hiển thị thông tin thanh toán
    }

    @PostMapping("/create-payment")
    public String createPayment(@RequestParam BigDecimal total) {
        String redirectUrl = payPalService.createPayment("USD", total);
        if (redirectUrl != null) {
            return "redirect:" + redirectUrl;
        } else {
            // Xử lý lỗi
            return "/error";
        }
    }

    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("orderId") Long orderId) {
        // Xử lý thành công thanh toán và chuyển hướng đến trang thành công
        return "/checkoutsuccess";
    }

    // ... Các phương thức khác ...
}
