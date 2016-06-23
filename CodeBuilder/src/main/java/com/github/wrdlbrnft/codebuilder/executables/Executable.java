package com.github.wrdlbrnft.codebuilder.executables;

import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.DeclarableElement;

/**
 * Created by kapeller on 09/07/15.
 */
public interface Executable extends DeclarableElement {
    CodeElement callOnTarget(CodeElement target, CodeElement... parameters);
    CodeElement call(CodeElement... parameters);
}
