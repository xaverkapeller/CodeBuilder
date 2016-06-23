package com.github.wrdlbrnft.codebuilder.elements.ifs;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 21/06/16.
 */
class IfBlock {

    private final CodeElement mComparison;
    private final CodeElement mBlock;

    public IfBlock(CodeElement comparison, CodeElement block) {
        mComparison = comparison;
        mBlock = block;
    }

    public CodeElement getComparison() {
        return mComparison;
    }

    public CodeElement getBlock() {
        return mBlock;
    }
}
