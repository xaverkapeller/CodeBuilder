package com.github.wrdlbrnft.codebuilder.elements.names;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;

/**
 * Created by kapeller on 09/07/15.
 */
class RandomNameElementImpl extends CodeElement.Adapter implements NameElement {

    private String mName;

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        if (mName == null) {
            mName = generator.generate();
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
