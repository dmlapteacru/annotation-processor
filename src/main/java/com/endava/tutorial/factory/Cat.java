/*
 * Classname : com.company.factory.Cat
 *
 * Created on: 21 Mar 2020
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
package com.endava.tutorial.factory;

import com.endava.tutorial.annotations.FactoryElement;

@FactoryElement(value = "CAT")
public class Cat implements Animal {

    public int age;

    public Cat() {}

    public Cat(int age) {
        this.age = age;
    }

    @Override
    public void talk() {
        System.out.println("meow-meow-meow");
    }
}
