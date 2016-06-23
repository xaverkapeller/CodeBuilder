package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.util.BlockHelper;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 07/07/15.
 */
public class ConstructorImpl extends BlockWriter implements Constructor {

    private final ConstructorNameElement mName = new ConstructorNameElement();

    private final DeclarationElementImpl mDeclaration;

    public ConstructorImpl(Set<Modifier> modifiers, List<Annotation> annotations, List<Type> exceptions, CodeElement parameters, CodeElement code) {
        mDeclaration = new DeclarationElementImpl(annotations, modifiers, exceptions, parameters, code, mName);
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

    public void setName(NameElement name) {
        mName.setNameElement(name);
    }

    private static class DeclarationElementImpl extends BlockWriter {

        private final List<Annotation> mAnnotations;
        private final Set<Modifier> mModifiers;
        private final List<Type> mExceptions;
        private final CodeElement mParameters;
        private final CodeElement mCode;
        private final CodeElement mName;

        private DeclarationElementImpl(List<Annotation> annotations, Set<Modifier> modifiers, List<Type> exceptions, CodeElement parameters, CodeElement code, CodeElement name) {
            mAnnotations = annotations;
            mModifiers = modifiers;
            mExceptions = exceptions;
            mParameters = parameters;
            mCode = code;
            mName = name;
        }

        @Override
        protected void write(Block block) {
            BlockHelper.appendAnnotationList(block, mAnnotations);
            BlockHelper.appendModifiers(block, mModifiers);

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

    private static class ConstructorNameElement implements NameElement {

        private NameElement mNameElement;

        @Override
        public String getResolvedName() {
            return mNameElement.getResolvedName();
        }

        @Override
        public void prepare() {
            mNameElement.prepare();
        }

        @Override
        public void resolve(Resolver resolver, NameGenerator generator) {
            mNameElement.resolve(resolver, generator);
        }

        @Override
        public void write(CodeBuilder builder) {
            mNameElement.write(builder);
        }

        public void setNameElement(NameElement nameElement) {
            mNameElement = nameElement;
        }
    }
}
