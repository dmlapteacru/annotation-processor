/*
 * Classname : com.endava.tutorial.processors.min.ValidatorResult
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

import java.util.LinkedList;
import java.util.List;

public class ValidatorResult {
    private boolean hasErrors;
    private List<String> violations = new LinkedList<>();

    public void addToViolations(String message) {
        hasErrors |= violations.add(message);
    }

    public List<String> getViolations() {
        return violations;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
}
