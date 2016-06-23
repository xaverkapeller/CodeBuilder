package com.github.wrdlbrnft.codebuilder.elements.forloop.item;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.variables.Variable;
import com.github.wrdlbrnft.codebuilder.variables.Variables;

/**
 * Created by kapeller on 09/07/15.
 */
public class ForeachImpl extends BlockWriter implements Foreach {

    private final Type mItemType;
    private final CodeElement mCollection;
    private final Iteration mIteration;

    public ForeachImpl(Type itemType, CodeElement collection, Iteration iteration) {
        mItemType = itemType;
        mCollection = collection;
        mIteration = iteration;
    }

    @Override
    protected void write(Block block) {
        final Variable item = Variables.of(mItemType);

        block.append("for(").append(item).append(" : ").append(mCollection).append(") {").addIndention().newLine();
        mIteration.onIteration(block, item);
        block.removeIndention().newLine().append("}");
    }
}
