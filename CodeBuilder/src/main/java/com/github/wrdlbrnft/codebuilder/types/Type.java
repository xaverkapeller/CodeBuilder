package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;

/**
 * Created by kapeller on 06/07/15.
 */
public interface Type extends CodeElement {
    CodeElement newInstance(CodeElement... parameters);
    CodeElement classObject();
}
