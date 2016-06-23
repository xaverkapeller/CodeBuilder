package com.github.wrdlbrnft.codebuilder.code;

import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by kapeller on 06/07/15.
 */
public interface Resolver {
    String registerImport(String packageName, String className);
    TypeElement getTypeElement(String fullName);
    TypeElement getTypeElement(TypeMirror mirror);
    NameElement getCurrentClassName();
    NameElement getPackageName();
}
