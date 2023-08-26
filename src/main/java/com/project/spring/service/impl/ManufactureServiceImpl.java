package com.project.spring.service.impl;

import com.project.spring.model.Manufacture;
import com.project.spring.repositories.ManufactureRepository;
import com.project.spring.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class ManufactureServiceImpl implements ManufactureService {
    @Autowired
    ManufactureRepository manufactureRepository;
    @Override
    public Set<Manufacture> findManufactureByNameIn(Collection<String> names) {
        return manufactureRepository.findManufactureByNameIn(names);
    }
}
