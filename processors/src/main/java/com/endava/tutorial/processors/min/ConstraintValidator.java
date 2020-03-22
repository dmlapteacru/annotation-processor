/*
 * Classname : com.endava.tutorial.processors.min.ConstraintValidator
 *
 * Created on: 22 Mar 2020
 *
 * Copyright (c) 2000-202 Global Payments, Ltd.
 * Global Payments, The Observatory, 7-11 Sir John Rogerson's Quay, Dublin 2, Ireland
 *
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Global Payments, Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Global Payments.
 *
 */
package com.endava.tutorial.processors.min;

import com.endava.tutorial.annotations.Min;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ConstraintValidator {

    Map<Class<? extends Annotation>, Object> constraints;
    ValidatorResult validatorResult = new ValidatorResult();

    public ConstraintValidator() {
        constraints = new HashMap<>();
        constraints.put(Min.class, new MinValidator());
    }

    public ValidatorResult validate(Object obj) {
        List<Field> fields = new LinkedList<>();
        Collections.addAll(fields, obj.getClass().getDeclaredFields());
        for (Map.Entry<Class<? extends Annotation>, Object> entry: constraints.entrySet()){

            List<Field> filteredFields = fields.stream()
                                               .filter(f -> f.isAnnotationPresent(entry.getKey()))
                                               .collect(Collectors.toList());
            checkFields(entry.getKey(), (Validator) entry.getValue(), filteredFields, obj);
        }

        return this.validatorResult;
    }

    private void checkFields(Class<? extends Annotation> annotation, Validator mapper, List<Field> fields, Object object) {
        for (Field field : fields) {
            if(!mapper.isValid(annotation, field, object)) {
                this.validatorResult.addToViolations(mapper.getErrorMessage(annotation, field));
            }
        }
    }
}
