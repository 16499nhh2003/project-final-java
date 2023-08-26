package com.project.spring;

import com.project.spring.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
public class ProjectfinalSummer2023Application {
    public static void main(String[] args) {
        SpringApplication.run(ProjectfinalSummer2023Application.class, args);
    }
} 