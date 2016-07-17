package com.github.wrdlbrnft.codebuilder.util;

import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 18/07/16
 */

public class ProcessingHelper {

    public static ProcessingHelper from(ProcessingEnvironment processingEnv) {
        return new ProcessingHelper(processingEnv);
    }

    private static final Map<Class<?>, TypeKind> TYPE_KIND_MAP = new MapBuilder<Class<?>, TypeKind>()
            .put(void.class, TypeKind.VOID)
            .put(int.class, TypeKind.INT)
            .put(boolean.class, TypeKind.BOOLEAN)
            .put(byte.class, TypeKind.BYTE)
            .put(double.class, TypeKind.DOUBLE)
            .put(float.class, TypeKind.FLOAT)
            .put(short.class, TypeKind.SHORT)
            .put(char.class, TypeKind.CHAR)
            .put(long.class, TypeKind.LONG)
            .build();

    private final ProcessingEnvironment mProcessingEnvironment;

    public ProcessingHelper(ProcessingEnvironment processingEnvironment) {
        mProcessingEnvironment = processingEnvironment;
    }

    public TypeElement getTypeElement(TypeMirror typeMirror) {
        return (TypeElement) mProcessingEnvironment.getTypeUtils().asElement(typeMirror);
    }

    public TypeMirror getTypeMirror(Class<?> cls) {
        if (cls.isPrimitive()) {
            return getPrimitiveType(TYPE_KIND_MAP.get(cls));
        }

        if (cls.isArray()) {
            final TypeMirror baseType = getTypeMirror(cls.getComponentType());
            return getArrayType(baseType);
        }

        return mProcessingEnvironment.getElementUtils().getTypeElement(cls.getCanonicalName()).asType();
    }

    public PrimitiveType getPrimitiveType(TypeKind kind) {
        return mProcessingEnvironment.getTypeUtils().getPrimitiveType(kind);
    }

    public ArrayType getArrayType(TypeMirror baseType) {
        return mProcessingEnvironment.getTypeUtils().getArrayType(baseType);
    }

    public TypeMirror getErasedType(TypeMirror typeMirror) {
        return mProcessingEnvironment.getTypeUtils().erasure(typeMirror);
    }

    public boolean isSameType(TypeMirror a, TypeMirror b) {
        return mProcessingEnvironment.getTypeUtils().isSameType(
                getErasedType(a),
                getErasedType(b)
        );
    }
}
