package com.github.wrdlbrnft.codebuilder.annotations;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.types.Type;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kapeller on 06/07/15.
 */
public interface Annotation extends CodeElement {

    class Builder {

        private Type mType;
        private final Map<String, CodeElement[]> mValuesMap = new LinkedHashMap<>();

        public Builder setType(Type type) {
            mType = type;
            return this;
        }

        public Builder addValue(String key, CodeElement... values) {
            mValuesMap.put(key, values);
            return this;
        }

        public Annotation build() {
            return new AnnotationImpl(mType, mValuesMap);
        }
    }
}
