package com.github.wrdlbrnft.codebuilder.elements.switches;

import com.github.wrdlbrnft.codebuilder.elements.values.Value;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kapeller on 07/07/15.
 */
public interface Switch extends com.github.wrdlbrnft.codebuilder.code.CodeElement {

    class Builder {

        private final Map<com.github.wrdlbrnft.codebuilder.elements.values.Value, com.github.wrdlbrnft.codebuilder.code.CodeElement> mCaseMap = new LinkedHashMap<>();
        private com.github.wrdlbrnft.codebuilder.code.CodeElement mDefaultCase;
        private com.github.wrdlbrnft.codebuilder.variables.Variable mVariable;

        public Builder addCase(Value value, com.github.wrdlbrnft.codebuilder.code.BlockWriter block) {
            mCaseMap.put(value, block);
            return this;
        }

        public Builder setDefaultCase(com.github.wrdlbrnft.codebuilder.code.BlockWriter defaultBlock) {
            mDefaultCase = defaultBlock;
            return this;
        }

        public Builder setVariable(com.github.wrdlbrnft.codebuilder.variables.Variable variable) {
            mVariable = variable;
            return this;
        }

        public Switch build() {
            return new SwitchImpl(mCaseMap, mDefaultCase, mVariable);
        }
    }
}
