package com.xfhy.compiler;

import com.xfhy.library.AutoParcel;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.xfhy.library.AutoParcel")
public class AutoParcelProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(AutoParcel.class);
        return true;
    }
}
