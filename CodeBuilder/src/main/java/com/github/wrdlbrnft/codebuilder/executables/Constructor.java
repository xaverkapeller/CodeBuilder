package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
public interface Constructor extends Executable {

    class Builder {
        private final List<Annotation> mAnnotations = new ArrayList<>();
        private final List<Type> mExceptions = new ArrayList<>();
        private Set<Modifier> mModifiers = new HashSet<>();
        private CodeElement mCode;
        private CodeElement mParameters;

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

        public Builder setCode(ExecutableBuilder builder) {
            mParameters = builder.getParameterElement();
            mCode = builder;
            return this;
        }

        public Constructor build() {
            return new ConstructorImpl(mModifiers, mAnnotations, mExceptions, mParameters, mCode);
        }
    }
}
