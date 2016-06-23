package com.github.wrdlbrnft.codebuilder.elements.ifs;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;

import java.util.List;

/**
 * Created by kapeller on 06/07/15.
 */
class IfImpl extends com.github.wrdlbrnft.codebuilder.code.BlockWriter implements If {

    private final List<IfBlock> mBlocks;
    private final CodeElement mElseBlock;

    IfImpl(List<IfBlock> blocks, CodeElement elseBlock) {
        mBlocks = blocks;
        mElseBlock = elseBlock;
    }

    @Override
    protected void write(com.github.wrdlbrnft.codebuilder.code.Block block) {
        if(mBlocks.isEmpty() && mElseBlock != null) {
            block.append(mElseBlock);
            return;
        }

        for (int i = 0, count = mBlocks.size(); i < count; i++) {

            if (i > 0) {
                block.append(" else ");
            }

            final IfBlock ifBlock = mBlocks.get(i);
            block.append("if(").append(ifBlock.getComparison()).append(") {").addIndention().newLine();
            block.append(ifBlock.getBlock());
            block.removeIndention().newLine().append("}");
        }

        if (mElseBlock != null) {
            block.append(" else {").addIndention().newLine();
            block.append(mElseBlock);
            block.removeIndention().newLine().append("}");
        }
    }
}
