package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.util.Utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by kapeller on 09/07/15.
 */
class TypeElementImpl extends AbsTypeImpl implements DefinedType {

    private final TypeElement mTypeElement;

    TypeElementImpl(TypeElement element) {
        mTypeElement = element;
    }

    @Override
    public void prepare() {
        if (getClassName() == null) {
            final String className = Utils.getClassName(mTypeElement);
            final String packageName = Utils.getPackageName(mTypeElement);
            setupType(packageName, className);
        }
    }

    @Override
    public String getFullClassName() {
        return mTypeElement.getQualifiedName().toString();
    }

    @Override
    public TypeElement asTypeElement(ProcessingEnvironment processingEnv) {
        return mTypeElement;
    }
}
