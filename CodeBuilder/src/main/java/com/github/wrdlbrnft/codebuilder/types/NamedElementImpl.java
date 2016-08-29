package com.github.wrdlbrnft.codebuilder.types;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by kapeller on 09/07/15.
 */
class NamedElementImpl extends AbsTypeImpl implements DefinedType {

    private final String mFullClassName;

    NamedElementImpl(String packageName, String className) {
        setupType(packageName, className);
        mFullClassName = packageName + "." + className;
    }

    @Override
    public String getFullClassName() {
        return mFullClassName;
    }

    @Override
    public TypeElement asTypeElement(ProcessingEnvironment processingEnv) {
        return processingEnv.getElementUtils().getTypeElement(mFullClassName);
    }
}
