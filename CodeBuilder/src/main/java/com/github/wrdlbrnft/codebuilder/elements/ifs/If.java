package com.github.wrdlbrnft.codebuilder.elements.ifs;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapeller on 03/07/15.
 */
public interface If extends CodeElement {

    class Builder {

        private final List<IfBlock> mBlocks = new ArrayList<>();
        private CodeElement mElseBlock;

        public Builder add(CodeElement comparison, CodeElement block) {
            mBlocks.add(new IfBlock(comparison, block));
            return this;
        }

        public Builder setElse(CodeElement elseBlock) {
            mElseBlock = elseBlock;
            return this;
        }

        public If build() {
            return new IfImpl(mBlocks, mElseBlock);
        }
    }
}
