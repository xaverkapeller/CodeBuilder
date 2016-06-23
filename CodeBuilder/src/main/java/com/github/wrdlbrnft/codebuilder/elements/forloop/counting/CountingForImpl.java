package com.github.wrdlbrnft.codebuilder.elements.forloop.counting;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.variables.Variable;
import com.github.wrdlbrnft.codebuilder.variables.Variables;

/**
 * Created by kapeller on 09/07/15.
 */
class CountingForImpl extends BlockWriter implements CountingFor {

    private final Type mCounterType;
    private final CodeElement mStartValue;
    private final CodeElement mEndValue;
    private final Mode mMode;
    private final Iteration mIteration;

    public CountingForImpl(Type counterType, CodeElement startValue, CodeElement endValue, Mode mode, Iteration iteration) {
        mCounterType = counterType;
        mStartValue = startValue;
        mEndValue = endValue;
        mMode = mode;
        mIteration = iteration;
    }

    @Override
    protected void write(Block block) {
        Variable index = Variables.of(mCounterType);

        block.append("for(").set(index, mStartValue).append("; ");

        mIteration.onCompare(block, index, mEndValue);
        block.append("; ");

        if (mMode == Mode.DECREMENTING) {
            block.append(index).append("--");
        } else {
            block.append(index).append("++");
        }

        block.append(") {").addIndention().newLine();
        mIteration.onIteration(block, index, mEndValue);
        block.removeIndention().newLine().append("}");
    }
}
