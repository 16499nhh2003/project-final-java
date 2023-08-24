package com.project.spring.service;

import com.project.spring.model.Manufacture;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ManufactureService {
    List<Manufacture> findAllManufacture();

    void deleteALl();
}
