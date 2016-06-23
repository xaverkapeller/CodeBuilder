package com.github.wrdlbrnft.codebuilder.implementations;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.types.Types;

import java.util.List;

/**
 * Created by kapeller on 14/07/15.
 */
abstract class AbsImplementationImpl extends BlockWriter implements Implementation {

    private final RelativeNameElement mNameElement;
    private CodeElement mDeclaration = null;

    protected AbsImplementationImpl(NameElement classNameElement, List<Implementation> nestedTypes) {
        mNameElement = new RelativeNameElement(classNameElement);

        for (Implementation implementation : nestedTypes) {
            if (implementation instanceof AbsImplementationImpl) {
                final AbsImplementationImpl nestedType = (AbsImplementationImpl) implementation;
                nestedType.prepareForNesting(mNameElement);
            }
        }
    }

    @Override
    protected void write(Block block) {
        block.append(mNameElement);
    }

    @Override
    public NameElement getName() {
        return mNameElement;
    }

    @Override
    public CodeElement getDeclaration() {
        if (mDeclaration == null) {
            mDeclaration = new BlockWriter() {
                @Override
                protected void write(Block block) {
                    writeDeclaration(block);
                }
            };
        }

        return mDeclaration;
    }

    protected abstract void writeDeclaration(Block block);

    protected void prepareForNesting(NameElement parentTypeName) {
        mNameElement.setParentClassPrefix(parentTypeName);
    }

    @Override
    public CodeElement newInstance(CodeElement... parameters) {
        return Types.createNewInstance(this, parameters);
    }

    @Override
    public CodeElement classObject() {
        return Types.classOf(this);
    }

    private static class RelativeNameElement extends CodeElement.Adapter implements NameElement {

        private final NameElement mClassNameElement;
        private NameElement mParentClassPrefix;

        private RelativeNameElement(NameElement classNameElement) {
            mClassNameElement = classNameElement;
        }

        @Override
        public String getResolvedName() {

            final StringBuilder builder = new StringBuilder();
            if (mParentClassPrefix != null) {
                builder.append(mParentClassPrefix.getResolvedName());
                builder.append(".");
            }
            builder.append(mClassNameElement.getResolvedName());

            return builder.toString();
        }

        @Override
        public void write(CodeBuilder builder) {
            if (mParentClassPrefix != null) {
                mParentClassPrefix.write(builder);
                builder.append(".");
            }
            mClassNameElement.write(builder);
        }

        public void setParentClassPrefix(NameElement parentClassPrefix) {
            mParentClassPrefix = parentClassPrefix;
        }
    }
}
