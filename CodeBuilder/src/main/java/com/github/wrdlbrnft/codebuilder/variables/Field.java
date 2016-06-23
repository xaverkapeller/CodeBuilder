package com.github.wrdlbrnft.codebuilder.variables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;
import com.github.wrdlbrnft.codebuilder.executables.Method;
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
public interface Field extends Variable {

    class Builder {

        private Type mType;
        private NameElement mName = Names.random();
        private CodeElement mInitialValue;
        private Set<Modifier> mModifiers = new HashSet<>();
        private final List<Field> mFields = new ArrayList<>();
        private final List<Method> mMethods = new ArrayList<>();
        private final List<Annotation> mAnnotations = new ArrayList<>();

        public Builder setInitialValue(CodeElement value) {
            mInitialValue = value;
            return this;
        }

        public Builder setModifiers(Set<Modifier> modifiers) {
            mModifiers = modifiers;
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

        public Builder addField(Field field) {
            mFields.add(field);
            return this;
        }

        public Builder addMethod(Method method) {
            mMethods.add(method);
            return this;
        }

        public Builder setType(Type type) {
            mType = type;
            return this;
        }

        public Builder setName(String name) {
            mName = Names.fixedName(name);
            return this;
        }

        public Field build() {
            return new FieldImpl(mType, mModifiers, mName, mAnnotations, mInitialValue, mFields, mMethods);
        }
    }
}
