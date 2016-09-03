package com.github.wrdlbrnft.codebuilder.arrays;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.ArrayType;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;
import com.github.wrdlbrnft.codebuilder.variables.Variable;

import java.util.Collection;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 25/08/16
 */

public class ArrayUtils {

    public static CodeElement of(Type componentType, CodeElement... values) {
        return new ArrayInitializerImpl(componentType, java.util.Arrays.asList(values));
    }

    public static CodeElement of(Type componentType, Collection<CodeElement> values) {
        return new ArrayInitializerImpl(componentType, values);
    }

    public static Variable access(Variable arrayVariable, Variable position) {
        return new ArrayAccessImpl(arrayVariable, position);
    }

    private static class ArrayInitializerImpl extends BlockWriter {

        private final ArrayType mArrayType;
        private final Collection<CodeElement> mValues;

        private ArrayInitializerImpl(Type componentType, Collection<CodeElement> values) {
            mArrayType = Types.arrayOf(componentType);
            mValues = values;
        }

        @Override

        protected void write(Block block) {
            block.append("new ").append(mArrayType).append(" { ");
            boolean appendSeparator = false;
            for (CodeElement value : mValues) {

                if (appendSeparator) {
                    block.append(", ");
                } else {
                    appendSeparator = true;
                }

                block.append(value);
            }
            block.append(" }");
        }
    }

    private static class ArrayAccessImpl extends BlockWriter implements Variable {

        private final Variable mArrayVariable;
        private final Variable mPosition;

        private ArrayAccessImpl(Variable arrayVariable, Variable position) {
            mArrayVariable = arrayVariable;
            mPosition = position;
        }

        @Override

        protected void write(Block block) {
            block.append(mArrayVariable).append("[").append(mPosition).append("]");
        }

        @Override
        public CodeElement getDeclaration() {
            return this;
        }
    }
}
