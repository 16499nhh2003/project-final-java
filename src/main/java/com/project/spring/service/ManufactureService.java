package com.project.spring.service;

import com.project.spring.model.Manufacture;

import java.util.Collection;
import java.util.Set;

public interface ManufactureService {
    Set<Manufacture> findManufactureByNameIn(Collection<String> names);
}
