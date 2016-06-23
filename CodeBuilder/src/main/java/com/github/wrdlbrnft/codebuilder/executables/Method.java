package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.variables.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
public interface Method extends Executable {

    class Builder {
        private Type mReturnType;
        private final List<Annotation> mAnnotations = new ArrayList<>();
        private final List<Type> mExceptions = new ArrayList<>();
        private Set<Modifier> mModifiers = new HashSet<>();
        private NameElement mName = Names.random();
        private CodeElement mCode;
        private CodeElement mParameters;

        public Builder setReturnType(Type returnType) {
            mReturnType = returnType;
            return this;
        }

        public Builder addAnnotation(Annotation annotation) {
            mAnnotations.add(annotation);
            return this;
        }

        public Builder addThrownException(Type exception) {
            mExceptions.add(exception);
            return this;
        }

        public Builder setModifiers(Set<Modifier> modifiers) {
            mModifiers = modifiers;
            return this;
        }

        public Builder setName(String name) {
            mName = Names.fixedName(name);
            return this;
        }

        public Builder setCode(List<Variable> parameters, BlockWriter writer) {
            final Block paramBlock = new Block();
            for (int i = 0, count = parameters.size(); i < count; i++) {
                final Variable parameter = parameters.get(i);

                if (i > 0) {
                    paramBlock.append(", ");
                }

                paramBlock.append(parameter.getDeclaration());
            }
            mParameters = paramBlock;
            mCode = writer;
            return this;
        }

        public Builder setCode(Variable parameter, BlockWriter writer) {
            return setCode(Collections.singletonList(parameter), writer);
        }

        public Builder setCode(ExecutableBuilder builder) {
            mParameters = builder.getParameterElement();
            mCode = builder;
            return this;
        }

        public Method build() {
            return new MethodImpl(mReturnType, mName, mModifiers, mAnnotations, mExceptions, mParameters, mCode);
        }
    }
}
