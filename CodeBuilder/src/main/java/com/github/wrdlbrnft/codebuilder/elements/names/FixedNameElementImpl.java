package com.github.wrdlbrnft.codebuilder.elements.names;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 09/07/15.
 */
class FixedNameElementImpl extends CodeElement.Adapter implements NameElement {

    private final String mFixedName;

    FixedNameElementImpl(String fixedName) {
        mFixedName = fixedName;
    }

    @Override
    public void write(CodeBuilder builder) {
        builder.append(mFixedName);
    }

    @Override
    public String getResolvedName() {
        return mFixedName;
    }
}
