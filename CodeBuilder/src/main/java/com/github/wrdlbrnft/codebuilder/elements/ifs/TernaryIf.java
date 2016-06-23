package com.github.wrdlbrnft.codebuilder.elements.ifs;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 17/07/15.
 */
public interface TernaryIf extends CodeElement {

    class Builder {

        private CodeElement mComparison;
        private CodeElement mTrueBlock;
        private CodeElement mFalseBlock;

        public Builder setComparison(CodeElement comparison) {
            mComparison = comparison;
            return this;
        }

        public Builder setTrueBlock(CodeElement trueBlock) {
            mTrueBlock = trueBlock;
            return this;
        }

        public Builder setFalseBlock(CodeElement falseBlock) {
            mFalseBlock = falseBlock;
            return this;
        }

        public TernaryIf build() {
            return new TernaryIfImpl(mComparison, mTrueBlock, mFalseBlock);
        }
    }
}
