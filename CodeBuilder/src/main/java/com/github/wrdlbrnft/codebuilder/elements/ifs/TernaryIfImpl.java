package com.github.wrdlbrnft.codebuilder.elements.ifs;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 17/07/15.
 */
class TernaryIfImpl extends BlockWriter implements TernaryIf {

    private final CodeElement mComparison;
    private final CodeElement mTrueBlock;
    private final CodeElement mFalseBlock;

    TernaryIfImpl(CodeElement comparison, CodeElement trueBlock, CodeElement falseBlock) {
        mComparison = comparison;
        mTrueBlock = trueBlock;
        mFalseBlock = falseBlock;
    }

    @Override
    protected void write(Block block) {
        block.append(mComparison).append(" ? ").append(mTrueBlock).append(" : ").append(mFalseBlock);
    }
}
