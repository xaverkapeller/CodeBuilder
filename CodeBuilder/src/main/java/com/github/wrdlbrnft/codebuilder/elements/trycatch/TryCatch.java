package com.github.wrdlbrnft.codebuilder.elements.trycatch;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.variables.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapeller on 16/07/15.
 */
public interface TryCatch extends CodeElement {

    interface CatchHandler {
        void onCatch(Block block, Variable exception);
    }

    class Builder {

        private final List<CatchWrapper> mCatchWrappers = new ArrayList<>();

        private CodeElement mTryBlock;
        private CodeElement mFinallyBlock;

        public Builder setTryBlock(CodeElement tryBlock) {
            mTryBlock = tryBlock;
            return this;
        }

        public Builder setFinallyBlock(CodeElement finallyBlock) {
            mFinallyBlock = finallyBlock;
            return this;
        }

        public Builder addCatchClause(Type exceptionType, CatchHandler catchHandler) {
            mCatchWrappers.add(new CatchWrapper(exceptionType, catchHandler));
            return this;
        }

        public TryCatch build() {
            return new TryCatchImpl(mTryBlock, mCatchWrappers, mFinallyBlock);
        }
    }
}
