/*
 * Classname : com.endava.tutorial.processors.min.MinValidatorForInteger
 *
 * Created on: 22 Mar 2020
 *
 * Copyright (c) 2000-2020 Global Payments, Ltd.
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

import java.lang.reflect.Field;

public class MinValidator implements Validator<Min> {

    @Override
    public boolean isValid(Class<? extends Min> annotationType, Field field, Object object) {
        Min annotation = field.getAnnotation(annotationType);
        try {
            long value = field.getLong(object);
            return value >= annotation.value();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getErrorMessage(Class<? extends Min> annotationType, Field field) {
        return field.getAnnotation(annotationType).message();
    }
}
