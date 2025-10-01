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

    public List<Unit> getFamilyOfUnits(Unit unit) {

        return switch (unit) {
            case G, KG -> List.of(Unit.G, Unit.KG);
            case ML, L -> List.of(Unit.ML, Unit.L);
            case UNIT -> List.of(Unit.UNIT);
            default -> throw new IllegalArgumentException("Unknown unit: " + unit);
        };
    }

    public Unit getSmallestUnit(Unit unit) {

        return switch (unit) {
            case G, KG -> Unit.G;
            case ML, L -> Unit.ML;
            case UNIT -> Unit.UNIT;
            default -> throw new IllegalArgumentException("Unknown unit: " + unit);
        };
    }

    public double getConversionFactor(Unit sourceUnit, Unit targetUnit) {

        if (sourceUnit == targetUnit) {
            return 1.0;
        }

        return switch (sourceUnit) {
            case G -> {
                if (targetUnit == Unit.KG) {
                    yield 0.001;
                } else {
                    throw new IllegalArgumentException("Incompatible units: " + sourceUnit + " and " + targetUnit);
                }
            }
            case KG -> {
                if (targetUnit == Unit.G) {
                    yield 1000.0;
                } else {
                    throw new IllegalArgumentException("Incompatible units: " + sourceUnit + " and " + targetUnit);
                }
            }
            case ML -> {
                if (targetUnit == Unit.L) {
                    yield 0.001;
                } else {
                    throw new IllegalArgumentException("Incompatible units: " + sourceUnit + " and " + targetUnit);
                }
            }
            case L -> {
                if (targetUnit == Unit.ML) {
                    yield 1000.0;
                } else {
                    throw new IllegalArgumentException("Incompatible units: " + sourceUnit + " and " + targetUnit);
                }
            }
            case UNIT -> 1.0;
        };

    }
}

