package com.github.wrdlbrnft.codebuilder.types;

/**
 * Created by kapeller on 09/07/15.
 */
class NamedElementImpl extends AbsTypeImpl {

    NamedElementImpl(String packageName, String className) {
        setupType(packageName, className);
    }
}
