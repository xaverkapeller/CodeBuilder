package com.github.wrdlbrnft.codebuilder.implementations;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.executables.Constructor;
import com.github.wrdlbrnft.codebuilder.executables.ConstructorImpl;
import com.github.wrdlbrnft.codebuilder.executables.Method;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.TypeParameter;
import com.github.wrdlbrnft.codebuilder.util.BlockHelper;
import com.github.wrdlbrnft.codebuilder.variables.Field;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
class ClassImplementationImpl extends AbsImplementationImpl implements Implementation {

    private final List<Annotation> mAnnotations;
    private final List<CodeElement> mStaticBlocks;
    private final Set<Modifier> mModifiers;
    private final NameElement mNameElement;
    private final List<TypeParameter> mTypeParameters;
    private final Type mExtendedType;
    private final List<Type> mImplementedTypes;
    private final List<Field> mFields;
    private final List<Constructor> mConstructors;
    private final List<Method> mMethods;
    private final List<Implementation> mNestedTypes;

    public ClassImplementationImpl(List<Annotation> annotations, List<CodeElement> staticBlocks, Set<Modifier> modifiers, NameElement nameElement, List<TypeParameter> typeParameters, Type extendedType, List<Type> implementedTypes, List<Field> fields, List<Constructor> constructors, List<Method> methods, List<Implementation> nestedTypes) {
        super(nameElement, nestedTypes);

        mAnnotations = annotations;
        mStaticBlocks = staticBlocks;
        mModifiers = modifiers;
        mNameElement = nameElement;
        mTypeParameters = typeParameters;
        mExtendedType = extendedType;
        mImplementedTypes = implementedTypes;
        mFields = fields;
        mConstructors = constructors;
        mMethods = methods;
        mNestedTypes = nestedTypes;
    }

    @Override
    protected void writeDeclaration(Block block) {
        BlockHelper.appendAnnotationList(block, mAnnotations);
        BlockHelper.appendModifiers(block, mModifiers);
        block.append("class ").append(mNameElement);

        BlockHelper.appendTypeParameters(block, mTypeParameters);

        if (mExtendedType != null) {
            block.append(" extends ").append(mExtendedType);
        }

        if (!mImplementedTypes.isEmpty()) {
            block.append(" implements ");

            for (int i = 0, count = mImplementedTypes.size(); i < count; i++) {
                final Type type = mImplementedTypes.get(i);

                if (i > 0) {
                    block.append(", ");
                }

                block.append(type);
            }
        }

        block.append(" {").addIndention().newLine();

        if (!mFields.isEmpty()) {
            block.newLine();
            for (Field field : mFields) {
                block.append(field.getDeclaration());
                block.newLine();
            }
        }

        for (CodeElement staticBlock : mStaticBlocks) {
            block.newLine();
            block.append("static {").addIndention().newLine();
            block.append(staticBlock);
            block.removeIndention().newLine().append("}").newLine();
        }

        for (Constructor constructor : mConstructors) {
            block.newLine();

            if (constructor instanceof ConstructorImpl) {
                ((ConstructorImpl) constructor).setName(mNameElement);
            }

            block.append(constructor.getDeclaration());
        }

        for (Method method : mMethods) {
            block.newLine();
            block.append(method.getDeclaration());
        }

        for (Implementation implementation : mNestedTypes) {
            block.newLine();
            block.append(implementation.getDeclaration());
            block.newLine();
        }

        block.removeIndention().append("}");
    }
}
