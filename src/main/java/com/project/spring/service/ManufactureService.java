package com.project.spring.service;

import com.project.spring.model.Manufacture;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ManufactureService {
    List<Manufacture> findAllManufacture();

    void deleteALl();

    Optional<Manufacture> findManufactureByName(String name);
}
