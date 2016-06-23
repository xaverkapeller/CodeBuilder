package com.github.wrdlbrnft.codebuilder.annotations;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;

import java.util.Map;

/**
 * Created by kapeller on 06/07/15.
 */
class AnnotationImpl extends BlockWriter implements Annotation {

    final Type mType;
    private final Map<String, CodeElement[]> mValuesMap;

    AnnotationImpl(Type type, Map<String, CodeElement[]> valuesMap) {
        mType = type;
        mValuesMap = valuesMap;
    }

    @Override
    protected void write(Block block) {
        block.append("@").append(mType);

        if (mValuesMap.isEmpty()) {
            return;
        }

        boolean shouldOpen = true;
        boolean shouldClose = false;

        boolean insertKeySeparator = false;

        for (String key : mValuesMap.keySet()) {

            final CodeElement[] values = mValuesMap.get(key);
            if (values.length == 0) {
                return;
            }

            if (shouldOpen) {
                shouldOpen = false;
                shouldClose = true;
                block.append("(");
            }

            if (insertKeySeparator) {
                block.append(", ");
            } else {
                insertKeySeparator = true;
            }

            block.append(key).append(" = ");

            if (values.length == 1) {
                block.append(values[0]);
                continue;
            }

            boolean insertValueSeparator = false;

            block.append("{");
            for (CodeElement value : values) {

                if (insertValueSeparator) {
                    block.append(", ");
                } else {
                    insertValueSeparator = true;
                }

                block.append(value);
            }
            block.append("}");
        }

        if (shouldClose) {
            block.append(")");
        }
    }
}
