package com.endava.tutorial.processors;

import com.endava.tutorial.annotations.Factory;
import com.endava.tutorial.annotations.FactoryElement;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.*;

public class FactoryProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, List<FactoryElementInfo>> result = new HashMap<>();
        for (Element annotatedFactory : roundEnv.getElementsAnnotatedWith(Factory.class)) {
            if (annotatedFactory.getKind() != ElementKind.INTERFACE) {
                error("Only interface can be annotated with Factory", annotatedFactory);
                return true;
            }
            TypeElement typeElement = (TypeElement) annotatedFactory;
            String className = typeElement.getQualifiedName().toString();
            if (!result.containsKey(className)) {
                result.put(className, new LinkedList<>());
            }
        }

        for (Element annotatedFactoryElement : roundEnv.getElementsAnnotatedWith(FactoryElement.class)) {
            if (annotatedFactoryElement.getKind() != ElementKind.CLASS) {
                error("Only class can be annotated with FactoryElement", annotatedFactoryElement);
                return true;
            }
            TypeElement typeElement = (TypeElement) annotatedFactoryElement;
            try {
                Optional<? extends TypeMirror> typeMirror = typeElement.getInterfaces().stream()
                                                                       .filter(i -> result.containsKey(i.toString()))
                                                                       .findFirst();
                if (typeMirror.isPresent()) {
                    FactoryElement annotation = annotatedFactoryElement.getAnnotation(FactoryElement.class);
                    result.get(typeMirror.get().toString()).add(new FactoryElementInfo(annotation.value(), typeElement));
                }
            } catch (Exception e) {
                error(e.toString());
            }
        }

        new FactoryFiler(filer, result, messager).generate();
        return true;
    }

    private void error(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void error(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void note(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(Factory.class.getCanonicalName(), FactoryElement.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
}
