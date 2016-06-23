package com.github.wrdlbrnft.codebuilder.elements.trycatch;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.variables.Variable;
import com.github.wrdlbrnft.codebuilder.variables.Variables;

import java.util.List;

/**
 * Created by kapeller on 16/07/15.
 */
class TryCatchImpl extends BlockWriter implements TryCatch {

    private final CodeElement mTryBlock;
    private final List<CatchWrapper> mCatchWrappers;
    private final CodeElement mFinallyBlock;

    TryCatchImpl(CodeElement tryBlock, List<CatchWrapper> catchWrappers, CodeElement finallyBlock) {
        mTryBlock = tryBlock;
        mCatchWrappers = catchWrappers;
        mFinallyBlock = finallyBlock;
    }

    @Override
    protected void write(Block block) {
        block.append("try {").addIndention().newLine();
        block.append(mTryBlock).removeIndention().newLine();
        block.append("}");

        for (CatchWrapper wrapper : mCatchWrappers) {
            final Variable exception = Variables.of(wrapper.getExceptionType());
            block.append(" catch(").append(exception).append(") {").addIndention().newLine();
            wrapper.getHandler().onCatch(block, exception);
            block.removeIndention().newLine().append("}");
        }

        if (mFinallyBlock != null) {
            block.append(" finally {").addIndention().newLine();
            block.append(mFinallyBlock).removeIndention().newLine();
            block.append("}");
        }
    }
}
