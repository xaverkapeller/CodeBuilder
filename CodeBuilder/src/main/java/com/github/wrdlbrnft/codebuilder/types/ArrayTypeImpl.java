package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 21/06/16.
 */

class ArrayTypeImpl extends BlockWriter implements ArrayType {

    private final Type mItemType;

    ArrayTypeImpl(Type itemType) {
        mItemType = itemType;
    }

    @Override
    protected void write(Block block) {
        block.append(mItemType).append("[]");
    }

    @Override
    public Type getItemType() {
        return mItemType;
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
