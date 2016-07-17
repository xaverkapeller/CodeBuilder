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
        return new NewArrayInstanceElementImpl(mItemType, parameters);
    }

    @Override
    public CodeElement classObject() {
        return Types.classOf(this);
    }

    private static class NewArrayInstanceElementImpl extends BlockWriter {

        private final Type mType;
        private final CodeElement[] mParameters;

        private NewArrayInstanceElementImpl(Type type, CodeElement[] parameters) {
            mType = type;
            mParameters = parameters;
        }

        @Override
        protected void write(Block block) {
            block.append("new ").append(mType).append("[");
            for (int i = 0, count = mParameters.length; i < count; i++) {
                final CodeElement parameter = mParameters[i];

                if (i > 0) {
                    block.append(", ");
                }

                block.append(parameter);
            }
            block.append("]");
        }
    }
}
