package com.github.wrdlbrnft.codebuilder.implementations;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
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
class InterfaceImplementationImpl extends AbsImplementationImpl {

    private final List<Annotation> mAnnotations;
    private final List<CodeElement> mStaticBlocks;
    private final Set<Modifier> mModifiers;
    private final NameElement mNameElement;
    private final List<TypeParameter> mTypeParameters;
    private final Type mExtendedType;
    private final List<Field> mFields;
    private final List<Method> mMethods;
    private final List<Implementation> mNestedTypes;

    public InterfaceImplementationImpl(List<Annotation> annotations, List<CodeElement> staticBlocks, Set<Modifier> modifiers, NameElement nameElement, List<TypeParameter> typeParameters, Type extendedType, List<Field> fields, List<Method> methods, List<Implementation> nestedTypes) {
        super(nameElement, nestedTypes);

        mAnnotations = annotations;
        mStaticBlocks = staticBlocks;
        mModifiers = modifiers;
        mNameElement = nameElement;
        mTypeParameters = typeParameters;
        mExtendedType = extendedType;
        mFields = fields;
        mMethods = methods;
        mNestedTypes = nestedTypes;
    }

    @Override
    protected void writeDeclaration(Block block) {
        BlockHelper.appendAnnotationList(block, mAnnotations);
        BlockHelper.appendModifiers(block, mModifiers);
        block.append("interface ").append(mNameElement);

        BlockHelper.appendTypeParameters(block, mTypeParameters);

        if (mExtendedType != null) {
            block.append(" extends ").append(mExtendedType);
        }

        block.append(" {").addIndention().newLine();

        if (!mFields.isEmpty()) {
            block.newLine();
            for (Field field : mFields) {
                block.append(field.getDeclaration());
                block.newLine();
            }
            block.newLine();
        }

        for (CodeElement staticBlock : mStaticBlocks) {
            block.append("static {").addIndention().newLine();
            block.append(staticBlock);
            block.removeIndention().newLine().append("}").newLine();
            block.newLine();
        }

        for (Method method : mMethods) {
            block.append(method.getDeclaration());
            block.newLine();
        }

        if (!mNestedTypes.isEmpty()) {
            block.newLine();
            for (int i = 0, count = mNestedTypes.size(); i < count; i++) {
                final Implementation implementation = mNestedTypes.get(i);
                block.append(implementation.getDeclaration());
                block.newLine();

                if (i < count - 1) {
                    block.newLine();
                }
            }
        }

        block.removeIndention().append("}");
    }
}
