package com.endava.tutorial.processors;

import javax.lang.model.element.TypeElement;

public class FactoryElementInfo {
    private String tagValue;
    private TypeElement clazz;

    public FactoryElementInfo(String tagValue, TypeElement classElement) {
        this.tagValue = tagValue;
        this.clazz = classElement;
    }

    public String getTagValue() {
        return tagValue;
    }

    public TypeElement getClassElement() {
        return clazz;
    }

    @Override
    public String toString() {
        return "FactoryElementInfo{" + "tagValue='" + tagValue + '\'' + ", clazz=" + clazz.getQualifiedName() + '}';
    }
}
