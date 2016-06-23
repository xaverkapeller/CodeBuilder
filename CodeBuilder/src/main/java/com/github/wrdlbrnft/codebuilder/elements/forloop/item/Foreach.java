package com.github.wrdlbrnft.codebuilder.elements.forloop.item;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;
import com.github.wrdlbrnft.codebuilder.variables.Variable;

/**
 * Created by kapeller on 09/07/15.
 */
public interface Foreach extends CodeElement {

    interface Iteration {
        void onIteration(Block block, Variable item);
    }

    class Builder {

        private Type mItemType = Types.OBJECT;
        private CodeElement mCollection;
        private Iteration mIteration;

        public Builder setItemType(Type itemType) {
            mItemType = itemType;
            return this;
        }

        public Builder setCollection(CodeElement collection) {
            mCollection = collection;
            return this;
        }

        public Builder setIteration(Iteration iteration) {
            mIteration = iteration;
            return this;
        }

        public Foreach build() {
            return new ForeachImpl(mItemType, mCollection, mIteration);
        }
    }
}
