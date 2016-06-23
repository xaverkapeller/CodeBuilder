package com.github.wrdlbrnft.codebuilder.variables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.DeclarableElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;
import com.github.wrdlbrnft.codebuilder.types.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 06/07/15.
 */
public interface Variable extends CodeElement, DeclarableElement {

    class Builder {

        private Set<Modifier> mModifiers = new HashSet<>();
        private final List<Annotation> mAnnotations = new ArrayList<>();
        private NameElement mName = Names.random();
        private boolean mIsVarArgs = false;
        private boolean mShouldInitialize = true;
        private Type mType;

        public Builder setModifiers(Set<Modifier> modifiers) {
            mModifiers = modifiers;
            return this;
        }

        public Builder setName(String name) {
            mName = Names.fixedName(name);
            return this;
        }

        public Builder addAnnotation(Annotation annotation) {
            mAnnotations.add(annotation);
            return this;
        }

        public Builder addAnnotations(Annotation... annotations) {
            Collections.addAll(mAnnotations, annotations);
            return this;
        }

        public Builder setType(Type type) {
            mType = type;
            return this;
        }

        public Builder setVarArgs(boolean varArgs) {
            mIsVarArgs = varArgs;
            return this;
        }

        public Builder setShouldInitialize(boolean initialized) {
            mShouldInitialize = initialized;
            return this;
        }

        public Variable build() {
            return new VariableImpl(mModifiers, mAnnotations, mType, mName, mIsVarArgs, mShouldInitialize);
        }
    }
}
