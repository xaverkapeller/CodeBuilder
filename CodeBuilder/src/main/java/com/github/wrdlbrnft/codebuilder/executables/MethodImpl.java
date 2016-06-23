package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.util.BlockHelper;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
class MethodImpl extends BlockWriter implements Method {

    private final NameElement mName;
    private final DeclarationElementImpl mDeclaration;

    public MethodImpl(Type returnType, NameElement name, Set<Modifier> modifiers, List<Annotation> annotations, List<Type> exceptions, CodeElement parameters, CodeElement code) {
        mName = name;
        mDeclaration = new DeclarationElementImpl(returnType, name, annotations, modifiers, exceptions, parameters, code);
    }

    @Override
    protected void write(Block block) {
        block.append(mName);
    }

    @Override
    public CodeElement getDeclaration() {
        return mDeclaration;
    }

    @Override
    public CodeElement callOnTarget(CodeElement target, CodeElement... parameters) {
        return Methods.call(this, target, parameters);
    }

    @Override
    public CodeElement call(CodeElement... parameters) {
        return Methods.call(this, null, parameters);
    }

    private static class DeclarationElementImpl extends BlockWriter {

        private final Type mReturnType;
        private final NameElement mName;
        private final List<Annotation> mAnnotations;
        private final Set<Modifier> mModifiers;
        private final List<Type> mExceptions;
        private final CodeElement mParameters;
        private final CodeElement mCode;

        private DeclarationElementImpl(Type returnType, NameElement name, List<Annotation> annotations, Set<Modifier> modifiers, List<Type> exceptions, CodeElement parameters, CodeElement code) {
            mReturnType = returnType;
            mName = name;
            mAnnotations = annotations;
            mModifiers = modifiers;
            mExceptions = exceptions;
            mParameters = parameters;
            mCode = code;
        }

        @Override
        protected void write(Block block) {
            BlockHelper.appendAnnotationList(block, mAnnotations);
            BlockHelper.appendModifiers(block, mModifiers);

            if (mReturnType != null) {
                block.append(mReturnType).append(" ");
            } else {
                block.append("void").append(" ");
            }

            block.append(mName).append("(");
            if (mParameters != null) {
                block.append(mParameters);
            }
            block.append(")");

            BlockHelper.appendThrowsDeclaration(block, mExceptions);

            if (mCode != null) {
                block.append(" {").addIndention().newLine();
                block.append(mCode);
                block.removeIndention().newLine().append("}").newLine();
            } else {
                block.append(";");
            }
        }
    }
}
