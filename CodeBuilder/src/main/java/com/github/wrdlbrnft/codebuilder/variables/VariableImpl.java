package com.github.wrdlbrnft.codebuilder.variables;

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
 * Created by kapeller on 06/07/15.
 */
class VariableImpl implements Variable {

    private final List<Annotation> mAnnotations;
    private final Set<Modifier> mModifiers;
    private final Type mType;
    private final NameElement mName;
    private final boolean mIsVarArgs;

    private boolean mShouldInitialize;
    private final DeclarationImpl mDeclaration = new DeclarationImpl();

    public VariableImpl(Set<Modifier> modifiers, List<Annotation> annotations, Type type, NameElement name, boolean isVarArgs) {
        this(modifiers, annotations, type, name, isVarArgs, true);
    }

    public VariableImpl(Set<Modifier> modifiers, List<Annotation> annotations, Type type, NameElement name, boolean isVarArgs, boolean shouldInitialize) {
        mAnnotations = annotations;
        mType = type;
        mModifiers = modifiers;
        mName = name;
        mIsVarArgs = isVarArgs;
        mShouldInitialize = shouldInitialize;
    }

    @Override
    public void prepare() {
        mName.prepare();
        mType.prepare();
        mDeclaration.prepare();
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        mType.resolve(resolver, generator);
        mName.resolve(resolver, generator);
        mDeclaration.resolve(resolver, generator);
    }

    @Override
    public void write(CodeBuilder builder) {
        if (mShouldInitialize) {
            mShouldInitialize = false;
            writeInitialization(builder);
        } else {
            writeName(builder);
        }
    }

    private void writeInitialization(CodeBuilder builder) {
        mDeclaration.write(builder);
    }

    private void writeName(CodeBuilder builder) {
        mName.write(builder);
    }

    public Type getType() {
        return mType;
    }

    public Set<Modifier> getModifiers() {
        return mModifiers;
    }

    public NameElement getName() {
        return mName;
    }

    @Override
    public CodeElement getDeclaration() {
        mShouldInitialize = false;
        return mDeclaration;
    }

    private class DeclarationImpl extends BlockWriter {

        @Override
        protected void write(Block block) {
            BlockHelper.appendAnnotationListInline(block, mAnnotations);
            BlockHelper.appendModifiers(block, mModifiers);
            block.append(mType);
            if (mIsVarArgs) {
                block.append("...");
            }
            block.append(" ").append(mName);
        }
    }
}
