package com.github.wrdlbrnft.codebuilder.elements.forloop.counting;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;
import com.github.wrdlbrnft.codebuilder.variables.Variable;

/**
 * Created by kapeller on 09/07/15.
 */
public interface CountingFor extends CodeElement {

    enum Mode {
        INCREMENTING,
        DECREMENTING
    }

    interface Iteration {
        void onIteration(Block block, Variable index, CodeElement endValue);
        void onCompare(Block block, Variable index, CodeElement endValue);
    }

    class Builder {

        private Type mCounterType = Types.Primitives.INTEGER;
        private CodeElement mStartValue;
        private CodeElement mEndValue;
        private Mode mMode = Mode.INCREMENTING;
        private Iteration mIteration;

        public Builder setCounterType(Type counterType) {
            mCounterType = counterType;
            return this;
        }

        public Builder setValues(CodeElement startValue, CodeElement endValue) {
            mStartValue = startValue;
            mEndValue = endValue;
            return this;
        }

        public Builder setMode(Mode mode) {
            mMode = mode;
            return this;
        }

        public Builder setIteration(Iteration iteration) {
            mIteration = iteration;
            return this;
        }

        public CountingFor build() {
            return new CountingForImpl(mCounterType, mStartValue, mEndValue, mMode, mIteration);
        }
    }
}
