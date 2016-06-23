package com.github.wrdlbrnft.codebuilder.util;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;

/**
 * Created by kapeller on 03/07/15.
 */
public class Operators {

    private Operators() {

    }

    public static CodeElement operate(final CodeElement left, final String operator, final CodeElement right) {
        return new ComparisonElementImpl(left, operator, right);
    }

    private static class ComparisonElementImpl implements CodeElement {

        private final CodeElement mLeft;
        private final CodeElement mRight;
        private final String mOperator;

        private ComparisonElementImpl(CodeElement left, String operator, CodeElement right) {
            mLeft = left;
            mRight = right;
            mOperator = operator;
        }

        @Override
        public void prepare() {
            mLeft.prepare();
            mRight.prepare();
        }

        @Override
        public void resolve(Resolver resolver, NameGenerator generator) {
            mLeft.resolve(resolver, generator);
            mRight.resolve(resolver, generator);
        }

        @Override
        public void write(CodeBuilder code) {
            mLeft.write(code);
            code.append(" ").append(mOperator.trim()).append(" ");
            mRight.write(code);
        }
    }
}
