package com.github.wrdlbrnft.codebuilder.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by kapeller on 30/09/15.
 */
public class TypeMirrorFactory {

    private final ProcessingEnvironment mProcessingEnvironment;

    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new MapBuilder<Class<?>, Class<?>>()
            .put(boolean.class, Boolean.class)
            .put(byte.class, Byte.class)
            .put(char.class, Character.class)
            .put(double.class, Double.class)
            .put(float.class, Float.class)
            .put(int.class, Integer.class)
            .put(long.class, Long.class)
            .put(short.class, Short.class)
            .build();

    private final Map<String, TypeMirror> mTypeMirrorMap = new HashMap<>();

    public TypeMirrorFactory(ProcessingEnvironment processingEnvironment) {
        mProcessingEnvironment = processingEnvironment;
    }

    public TypeMirror get(Class<?> cls) {
        final String fullClassName = cls.getName();
        if (mTypeMirrorMap.containsKey(fullClassName)) {
            return mTypeMirrorMap.get(fullClassName);
        }

        final TypeMirror mirror = get(mProcessingEnvironment, cls);
        mTypeMirrorMap.put(fullClassName, mirror);
        return mirror;
    }

    public TypeMirror get(String fullClassName) {
        if (mTypeMirrorMap.containsKey(fullClassName)) {
            return mTypeMirrorMap.get(fullClassName);
        }

        final TypeMirror mirror = get(mProcessingEnvironment, fullClassName);
        mTypeMirrorMap.put(fullClassName, mirror);
        return mirror;
    }

    public static TypeMirror get(ProcessingEnvironment processingEnvironment, String fullClassName) {
        return processingEnvironment.getElementUtils().getTypeElement(fullClassName).asType();
    }

    public static TypeMirror get(ProcessingEnvironment processingEnvironment, Class<?> cls) {
        if (cls.isPrimitive()) {
            if(cls.equals(void.class)) {
                return processingEnvironment.getTypeUtils().getNoType(TypeKind.VOID);
            }
            final Class<?> boxedClass = PRIMITIVES_TO_WRAPPERS.get(cls);
            final TypeElement boxedElement = processingEnvironment.getElementUtils().getTypeElement(boxedClass.getName());
            return processingEnvironment.getTypeUtils().unboxedType(boxedElement.asType());
        }

        return processingEnvironment.getElementUtils().getTypeElement(cls.getName()).asType();
    }
}

