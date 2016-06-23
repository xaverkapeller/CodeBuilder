package com.github.wrdlbrnft.codebuilder.implementations;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.DeclarableElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;
import com.github.wrdlbrnft.codebuilder.executables.Constructor;
import com.github.wrdlbrnft.codebuilder.executables.Method;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.TypeParameter;
import com.github.wrdlbrnft.codebuilder.variables.Field;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
public interface Implementation extends Type, DeclarableElement {

    NameElement getName();

    enum Kind {
        INTERFACE,
        CLASS
    }

    class Builder {

        private final List<Annotation> mAnnotations = new ArrayList<>();
        private final List<Field> mFields = new ArrayList<>();
        private final List<Constructor> mConstructors = new ArrayList<>();
        private final List<Method> mMethods = new ArrayList<>();
        private final List<CodeElement> mStaticBlocks = new ArrayList<>();
        private final List<Implementation> mNestedImplementation = new ArrayList<>();
        private final List<Type> mImplementedTypes = new ArrayList<>();
        private final List<TypeParameter> mTypeParameters = new ArrayList<>();
        private Kind mKind = Kind.CLASS;
        private Set<Modifier> mModifiers = EnumSet.noneOf(Modifier.class);
        private NameElement mNameElement = Names.random();
        private Type mExtendedType;

        public Builder setExtendedType(Type type) {
            mExtendedType = type;
            return this;
        }

        public Builder setName(String name) {
            mNameElement = Names.fixedName(name);
            return this;
        }

        public Builder setModifiers(Set<Modifier> modifiers) {
            mModifiers = modifiers;
            return this;
        }

        public Builder setKind(Kind kind) {
            mKind = kind;
            return this;
        }

        public Builder addStaticBlock(CodeElement block) {
            mStaticBlocks.add(block);
            return this;
        }

        public Builder addImplementedType(Type type) {
            mImplementedTypes.add(type);
            return this;
        }

        public Builder addNestedImplementation(Implementation implementation) {
            mNestedImplementation.add(implementation);
            return this;
        }

        public Builder addMethod(Method method) {
            mMethods.add(method);
            return this;
        }

        public Builder addTypeParameter(TypeParameter parameter) {
            mTypeParameters.add(parameter);
            return this;
        }

        public Builder addConstructor(Constructor constructor) {
            mConstructors.add(constructor);
            return this;
        }

        public Builder addField(Field field) {
            mFields.add(field);
            return this;
        }

        public Builder addAnnotation(Annotation annotation) {
            mAnnotations.add(annotation);
            return this;
        }

        public Implementation build() {

            switch (mKind) {

                case INTERFACE:
                    return new InterfaceImplementationImpl(mAnnotations, mStaticBlocks, mModifiers, mNameElement, mTypeParameters, mExtendedType, mFields, mMethods, mNestedImplementation);

                case CLASS:
                    return new ClassImplementationImpl(mAnnotations, mStaticBlocks, mModifiers, mNameElement, mTypeParameters, mExtendedType, mImplementedTypes, mFields, mConstructors, mMethods, mNestedImplementation);

                default:
                    throw new UnsupportedOperationException("Encountered Unknown Implementation.Kind: " + mKind);
            }
        }
    }
}
