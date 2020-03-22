/*
 * Classname : com.endava.tutorial.processors.min.Validator
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface Validator<A extends Annotation> {
    boolean isValid(Class<? extends A> annotationType, Field field, Object object);
    String getErrorMessage(Class<? extends A> annotationType, Field field);
}
