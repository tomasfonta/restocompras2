package com.tf.restocompras.service;

import com.tf.restocompras.model.unit.Unit;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UnitService {
    public List<Unit> getAllUnits() {
        return Arrays.asList(Unit.values());
    }
}

