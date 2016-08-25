package com.github.wrdlbrnft.codebuilder.arrays;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.ArrayType;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;

import java.util.Collection;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 25/08/16
 */

public class Arrays {

    public static CodeElement of(Type componentType, CodeElement... values) {
        return new ArrayElementImpl(componentType, java.util.Arrays.asList(values));
    }

    public static CodeElement of(Type componentType, Collection<CodeElement> values) {
        return new ArrayElementImpl(componentType, values);
    }

    private static class ArrayElementImpl extends BlockWriter {

        private final ArrayType mArrayType;
        private final Collection<CodeElement> mValues;

        private ArrayElementImpl(Type componentType, Collection<CodeElement> values) {
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
}
