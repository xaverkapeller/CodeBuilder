package com.github.wrdlbrnft.codebuilder.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Created by kapeller on 08/07/15.
 */
public class Utils {

    private Utils() {

    }

    public static AnnotationValue getAnnotationValue(Element element, String className, String name) {
        final AnnotationMirror mirror = getAnnotationMirror(element, className);
        if (mirror != null) {
            return getAnnotationValue(mirror, name);
        }

        return null;
    }

    public static AnnotationValue getAnnotationValue(AnnotationMirror mirror, String name) {
        final Map<? extends ExecutableElement, ? extends AnnotationValue> map = mirror.getElementValues();
        for (ExecutableElement key : map.keySet()) {
            if (key.getSimpleName().toString().equals(name)) {
                return map.get(key);
            }
        }
        return null;
    }

    public static AnnotationMirror getAnnotationMirror(Element element, String className) {
        final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotationMirrors) {
            final String annotationClassName = mirror.getAnnotationType().toString();
            if (annotationClassName.equals(className)) {
                return mirror;
            }
        }

        return null;
    }

    public static List<AnnotationMirror> getAnnotations(Element element, String[] annotationClassNames) {
        final List<AnnotationMirror> mirrors = new ArrayList<>();

        final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotationMirrors) {
            final String annotationClassName = mirror.getAnnotationType().toString();

            for (String className : annotationClassNames) {
                if (annotationClassName.equals(className)) {
                    mirrors.add(mirror);
                }
            }
        }

        return mirrors;
    }

    public static boolean hasAnnotation(Element element, String annotationClass) {
        final List<? extends AnnotationMirror> mirrors = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : mirrors) {
            if (mirror.getAnnotationType().toString().equals(annotationClass)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasOneAnnotationOf(Element element, String[] annotations) {
        final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotationMirrors) {
            final DeclaredType declaredType = mirror.getAnnotationType();
            final String annotation = declaredType.toString();
            for (String className : annotations) {
                if (className.equals(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSameType(TypeMirror mirror, Class<?> cls) {
        final String mirrorFullName = mirror.toString();
        final String classFullName = cls.getCanonicalName();
        return mirrorFullName.equals(classFullName);
    }

    public static boolean isSameType(TypeMirror mirror, String fullClassName) {
        final String mirrorFullName = mirror.toString();
        return mirrorFullName.equals(fullClassName);
    }

    public static String getClassName(TypeElement element) {
        Element enclosingElement = element.getEnclosingElement();
        String className = element.getSimpleName().toString();
        while (enclosingElement.getKind() != ElementKind.PACKAGE) {
            className = enclosingElement.getSimpleName() + "." + className;
            enclosingElement = enclosingElement.getEnclosingElement();
        }
        return className;
    }

    public static String getPackageName(TypeElement element) {
        final String fullName = element.getQualifiedName().toString();
        final String className = getClassName(element);
        if (fullName.length() > className.length()) {
            return fullName.substring(0, fullName.length() - className.length() - 1);
        }
        return "";
    }

    public static String createGeneratedClassName(TypeElement source, String prefix, String postfix) {
        final String baseName = Utils.getClassName(source).replace(".", "$");
        return prefix + baseName + postfix;
    }

    public static TypeElement getTypeElement(ProcessingEnvironment environment, Class<?> cls) {
        return getTypeElement(environment, cls.getCanonicalName());
    }

    public static TypeElement getTypeElement(ProcessingEnvironment environment, String fullClassName) {
        return environment.getElementUtils().getTypeElement(fullClassName);
    }

    public static boolean isSubTypeOf(ProcessingEnvironment environment, TypeMirror child, TypeMirror parent) {
        final TypeMirror childErasure = environment.getTypeUtils().erasure(child);
        final TypeMirror parentErasure = environment.getTypeUtils().erasure(parent);
        return environment.getTypeUtils().isSubtype(childErasure, parentErasure);
    }

    public static boolean isSubTypeOf(ProcessingEnvironment environment, TypeMirror child, Class<?> cls) {
        final boolean childIsPrimitive = Utils.isPrimitive(child);
        final boolean classIsPrimitive = cls.isPrimitive();

        if (classIsPrimitive != childIsPrimitive) {
            return false;
        }

        if (classIsPrimitive) {
            return cls.getCanonicalName().equals(child.toString());
        }

        final TypeMirror parent = getTypeElement(environment, cls).asType();
        return isSubTypeOf(environment, child, parent);
    }

    public static boolean isPrimitive(TypeMirror mirror) {
        return mirror instanceof PrimitiveType;
    }

    public static TypeElement getTypeElementFromList(Collection<? extends TypeElement> typeElements, String fullClassName) {
        for (TypeElement element : typeElements) {
            if (element.getQualifiedName().toString().equals(fullClassName)) {
                return element;
            }
        }

        return null;
    }

    public static List<Element> getAllDeclaredInterfaceElements(ProcessingEnvironment environment, TypeElement element) {
        final Set<Element> elements = new LinkedHashSet<>();
        elements.addAll(element.getEnclosedElements());

        final List<? extends TypeMirror> interfaces = element.getInterfaces();
        for (TypeMirror mirror : interfaces) {
            final TypeElement implementedType = (TypeElement) environment.getTypeUtils().asElement(mirror);
            if (implementedType != null) {
                elements.addAll(getAllDeclaredInterfaceElements(environment, implementedType));
            }
        }
        return new ArrayList<>(elements);
    }

    public static boolean isOneOf(TypeMirror mirror, Class<?>[] classes) {
        for (Class<?> cls : classes) {
            if (Utils.isSameType(mirror, cls)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOneOf(TypeMirror mirror, String[] fullClassNames) {
        for (String fullClassName : fullClassNames) {
            if (Utils.isSameType(mirror, fullClassName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSameType(ProcessingEnvironment processingEnvironment, TypeMirror a, TypeMirror b) {
        return processingEnvironment.getTypeUtils().isSameType(a, b);
    }

    public static List<TypeMirror> getTypeParameters(TypeMirror mirror) {
        if (mirror instanceof DeclaredType) {
            final DeclaredType declaredType = (DeclaredType) mirror;
            return new ArrayList<>(declaredType.getTypeArguments());
        }

        return new ArrayList<>();
    }

    public static List<TypeMirror> getTypeParametersOfInterface(ProcessingEnvironment processingEnvironment, TypeMirror compositeType, TypeMirror interfaceType) {
        final DeclaredType declaredType = (DeclaredType) compositeType;
        final TypeElement compositeElement = (TypeElement) declaredType.asElement();
        final DeclaredType declaredInterfaceType = findInterfaceType(processingEnvironment, compositeElement, interfaceType);
        if (declaredInterfaceType != null) {
            return new ArrayList<>(declaredInterfaceType.getTypeArguments());
        }

        return Collections.emptyList();
    }

    public static DeclaredType findInterfaceType(ProcessingEnvironment processingEnvironment, TypeElement compositeType, TypeMirror interfaceType) {
        for (TypeMirror implementedInterfaceType : compositeType.getInterfaces()) {
            final DeclaredType declaredType = (DeclaredType) implementedInterfaceType;
            if (isSameType(processingEnvironment, declaredType.asElement().asType(), interfaceType)) {
                return declaredType;
            }
        }

        final TypeElement superElement = getSuperElement(compositeType);
        if (superElement != null) {
            return findInterfaceType(processingEnvironment, superElement, interfaceType);
        }

        return null;
    }

    public static TypeElement getSuperElement(TypeElement element) {
        final TypeMirror superClassMirror = element.getSuperclass();
        if (superClassMirror == null || superClassMirror instanceof NoType) {
            return null;
        }

        final DeclaredType declaredType = (DeclaredType) superClassMirror;
        return (TypeElement) declaredType.asElement();
    }

    public static void logException(ProcessingEnvironment processingEnvironment, String message, Throwable throwable) {
        Logger.getAnonymousLogger().log(Level.SEVERE, message, throwable);
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    public static void logException(ProcessingEnvironment processingEnvironment, String message, Element element, Throwable throwable) {
        Logger.getAnonymousLogger().log(Level.SEVERE, message, throwable);
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
