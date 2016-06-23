package com.github.wrdlbrnft.codebuilder.elements.switches;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.values.Value;

import java.util.Map;

/**
 * Created by kapeller on 07/07/15.
 */
class SwitchImpl extends BlockWriter implements Switch {

    private final Map<Value, CodeElement> mCaseMap;
    private final CodeElement mDefaultCase;
    private final com.github.wrdlbrnft.codebuilder.variables.Variable mVariable;

    SwitchImpl(Map<Value, CodeElement> caseMap, CodeElement defaultCase, com.github.wrdlbrnft.codebuilder.variables.Variable variable) {
        mCaseMap = caseMap;
        mDefaultCase = defaultCase;
        mVariable = variable;
    }

    @Override
    protected void write(Block block) {
        block.append("switch(").append(mVariable).append(") {").addIndention().newLine();

        for (Value value : mCaseMap.keySet()) {
            final CodeElement element = mCaseMap.get(value);
            block.append("case ").append(value).append(":").addIndention().newLine();
            block.append(element).removeIndention().newLine();
        }

        if (mDefaultCase != null) {
            block.append("default:").addIndention().newLine();
            block.append(mDefaultCase).removeIndention().newLine();
        }

        block.removeIndention().append("}");
    }
}
