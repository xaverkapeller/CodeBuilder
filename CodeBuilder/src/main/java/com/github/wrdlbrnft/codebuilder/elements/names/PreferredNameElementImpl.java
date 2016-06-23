package com.github.wrdlbrnft.codebuilder.elements.names;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;

/**
 * Created by kapeller on 09/07/15.
 */
class PreferredNameElementImpl extends CodeElement.Adapter implements NameElement {

    private final String mPreferredName;

    private String mName;

    PreferredNameElementImpl(String preferredName) {
        mPreferredName = preferredName;
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        if (mName == null) {
            mName = generator.generate(mPreferredName);
        }
    }

    @Override
    public void write(CodeBuilder builder) {
        builder.append(mName);
    }

    @Override
    public String getResolvedName() {
        return mName;
    }
}
