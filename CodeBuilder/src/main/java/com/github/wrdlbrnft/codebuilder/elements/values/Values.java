package com.github.wrdlbrnft.codebuilder.elements.values;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.variables.Field;

/**
 * Created by kapeller on 03/07/15.
 */
public class Values {

    private Values() {

    }

    public static Value of(String text) {
        if (text == null) {
            return ofNull();
        }

        return new SimpleValueImpl("\"", text, "\"");
    }

    public static Value of(float value) {
        return new SimpleValueImpl("", String.valueOf(value), "f");
    }

    public static Value of(double value) {
        return new SimpleValueImpl("", String.valueOf(value), "");
    }

    public static Value of(long value) {
        return new SimpleValueImpl("", String.valueOf(value), "l");
    }

    public static Value of(boolean value) {
        return new SimpleValueImpl("", String.valueOf(value), "");
    }

    public static Value of(int value) {
        return new SimpleValueImpl("", String.valueOf(value), "");
    }

    public static Value literal(Object object) {
        return new SimpleValueImpl("", String.valueOf(object), "");
    }

    public static Value of(Type type, Field field) {
        return new FieldValue(type, field);
    }

    public static KeyValue ofThis(Type type) {
        return new ExplicitThisValue(type);
    }

    public static KeyValue ofNull() {
        return new KeywordElement("null");
    }

    public static KeyValue ofThis() {
        return new KeywordElement("this");
    }

    public static KeyValue superClass() {
        return new KeywordElement("super");
    }

    private static class KeywordElement extends BlockWriter implements KeyValue {

        private final String mStringRepresentation;

        private KeywordElement(String stringRepresentation) {
            mStringRepresentation = stringRepresentation;
        }

        @Override
        protected void write(Block block) {
            block.append(mStringRepresentation);
        }

        @Override
        public CodeElement getDeclaration() {
            return new Block();
        }
    }

    private static class ExplicitThisValue extends BlockWriter implements KeyValue {

        private final Type mType;

        private ExplicitThisValue(Type type) {
            mType = type;
        }

        @Override
        protected void write(Block block) {
            block.append(mType).append(".this");
        }

        @Override
        public CodeElement getDeclaration() {
            return new Block();
        }
    }

    private static final class FieldValue extends BlockWriter implements Value {

        private final Type mType;
        private final Field mField;

        private FieldValue(Type type, Field field) {
            mType = type;
            mField = field;
        }

        @Override
        protected void write(Block block) {
            block.append(mType).append(".").append(mField);
        }
    }
}
