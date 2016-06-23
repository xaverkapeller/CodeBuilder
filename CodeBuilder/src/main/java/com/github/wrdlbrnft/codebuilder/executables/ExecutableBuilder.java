package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.variables.Variable;

import java.util.List;

/**
 * Created by kapeller on 07/07/15.
 */
public abstract class ExecutableBuilder extends BlockWriter {

    private final Block mParameters = new Block();

    CodeElement getParameterElement() {
        return mParameters;
    }

    @Override
    public void prepare() {
        final List<Variable> parameters = createParameters();
        for (int i = 0, count = parameters.size(); i < count; i++) {
            final Variable parameter = parameters.get(i);

            if (i > 0) {
                mParameters.append(", ");
            }

            mParameters.append(parameter.getDeclaration());
        }

        mParameters.prepare();
        super.prepare();
    }

    protected abstract List<Variable> createParameters();
}
