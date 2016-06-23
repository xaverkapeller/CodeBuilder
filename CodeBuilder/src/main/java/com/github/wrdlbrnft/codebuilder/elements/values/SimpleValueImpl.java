package com.github.wrdlbrnft.codebuilder.elements.values;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 09/07/15.
 */
class SimpleValueImpl extends CodeElement.Adapter implements Value {

    private final String mPrefix;
    private final String mValue;
    private final String mPostFix;

    public SimpleValueImpl(String prefix, String value, String postFix) {
        mPrefix = prefix;
        mValue = value;
        mPostFix = postFix;
    }

    @Override
    public void write(CodeBuilder code) {
        code.append(mPrefix).append(mValue).append(mPostFix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleValueImpl that = (SimpleValueImpl) o;

        if (mPrefix != null ? !mPrefix.equals(that.mPrefix) : that.mPrefix != null) {
            return false;
        }
        if (mValue != null ? !mValue.equals(that.mValue) : that.mValue != null) {
            return false;
        }
        return !(mPostFix != null ? !mPostFix.equals(that.mPostFix) : that.mPostFix != null);

    }

    @Override
    public int hashCode() {
        int result = mPrefix != null ? mPrefix.hashCode() : 0;
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        result = 31 * result + (mPostFix != null ? mPostFix.hashCode() : 0);
        return result;
    }
}
