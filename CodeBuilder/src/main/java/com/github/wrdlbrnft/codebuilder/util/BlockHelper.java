package com.github.wrdlbrnft.codebuilder.util;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.TypeParameter;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
public class BlockHelper {

    private BlockHelper() {
    }

    public static void appendAnnotationList(Block block, List<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            block.append(annotation).newLine();
        }
    }

    public static void appendAnnotationListInline(Block block, List<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            block.append(annotation).append(" ");
        }
    }

    public static void appendModifiers(Block block, Set<Modifier> modifiers) {
        for (Modifier modifier : modifiers) {
            final String name = modifier.name().toLowerCase();
            block.append(name).append(" ");
        }
    }

    public static void appendTypeParameters(Block block, List<TypeParameter> typeParameters) {
        if (!typeParameters.isEmpty()) {
            block.append("<");
            for (int i = 0, count = typeParameters.size(); i < count; i++) {
                final TypeParameter parameter = typeParameters.get(i);

                if (i > 0) {
                    block.append(", ");
                }

                block.append(parameter);
            }
            block.append(">");
        }
    }

    public static void appendThrowsDeclaration(Block block, List<Type> exceptions) {
        if (!exceptions.isEmpty()) {
            block.append(" throws ");

            for (int i = 0, count = exceptions.size(); i < count; i++) {
                final Type exception = exceptions.get(i);

                if (i > 0) {
                    block.append(", ");
                }

                block.append(exception);
            }
        }
    }
}
