package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;

/**
 * Created by kapeller on 16/07/15.
 */
class TypeParameterImpl extends BlockWriter implements TypeParameter {

    private final CodeElement mName;

    TypeParameterImpl(CodeElement name) {
        mName = name;
    }

    @Override
    protected void write(Block block) {
        block.append(mName);
    }

    @Override
    public CodeElement newInstance(CodeElement... parameters) {
        return Types.createNewInstance(this, parameters);
    }

    @Override
    public CodeElement classObject() {
        return Types.classOf(this);
    }
}
