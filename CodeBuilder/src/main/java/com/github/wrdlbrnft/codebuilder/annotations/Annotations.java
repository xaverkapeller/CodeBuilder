package com.github.wrdlbrnft.codebuilder.annotations;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;

import java.util.Collections;

import javax.lang.model.element.TypeElement;

/**
 * Created by kapeller on 06/07/15.
 */
public class Annotations {

    private Annotations() {

    }

    public static Annotation forType(String packageName, String className) {
        final Type type = Types.of(packageName, className);
        return forType(type);
    }

    public static Annotation forType(Class<?> cls) {
        final Type type = Types.of(cls);
        return forType(type);
    }

    public static Annotation forType(TypeElement element) {
        final Type type = Types.of(element);
        return forType(type);
    }

    public static Annotation forType(Type type) {
        return new AnnotationImpl(type, Collections.<String, CodeElement[]>emptyMap());
    }
}
