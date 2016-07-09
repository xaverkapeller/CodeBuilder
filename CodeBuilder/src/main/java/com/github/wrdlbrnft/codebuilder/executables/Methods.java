package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;

import javax.lang.model.element.ExecutableElement;

/**
 * Created by kapeller on 09/07/15.
 */
public class Methods {

    public static final Method HASH_CODE = Methods.stub("hashCode");
    public static final Method EQUALS = Methods.stub("equals");
    public static final Method TO_STRING = Methods.stub("toString");
    public static final Method GET_CLASS = Methods.stub("getClass");
    public static final Method SUPER = Methods.stub("super");
    public static final Method THIS = Methods.stub("this");

    private Methods() {

    }

    public static CodeElement call(final ExecutableElement method, final CodeElement target, final CodeElement... parameters) {
        return new BlockWriter() {
            @Override
            protected void write(Block block) {
                final String methodName = method.getSimpleName().toString();
                if (target != null) {
                    block.append(target).append(".");
                }
                block.append(methodName).append("(");
                for (int i = 0, count = parameters.length; i < count; i++) {
                    final CodeElement parameter = parameters[i];

                    if (i > 0) {
                        block.append(", ");
                    }

                    block.append(parameter);
                }
                block.append(")");
            }
        };
    }

    public static CodeElement call(final Executable method, final CodeElement target, final CodeElement... parameters) {
        return new BlockWriter() {
            @Override
            protected void write(Block block) {
                if (target != null) {
                    block.append(target).append(".");
                }
                block.append(method).append("(");
                for (int i = 0, count = parameters.length; i < count; i++) {
                    final CodeElement parameter = parameters[i];

                    if (i > 0) {
                        block.append(", ");
                    }

                    block.append(parameter);
                }
                block.append(")");
            }
        };
    }

    public static Method stub(String name) {
        return new Method.Builder()
                .setName(name)
                .build();
    }
}
