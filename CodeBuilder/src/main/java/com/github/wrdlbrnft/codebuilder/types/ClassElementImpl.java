package com.github.wrdlbrnft.codebuilder.types;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by kapeller on 09/07/15.
 */
class ClassElementImpl extends AbsTypeImpl implements DefinedType {

    private final Class<?> mClass;

    ClassElementImpl(Class<?> aClass) {
        mClass = aClass;
    }

    @Override
    public void prepare() {
        if (getClassName() == null) {
            final Package classPackage = mClass.getPackage();
            final String packageName;
            final String className;
            if (classPackage != null) {
                packageName = classPackage.getName();
                final String fullName = mClass.getCanonicalName();
                className = fullName.replace(packageName + ".", "");
            } else {
                className = mClass.getSimpleName();
                packageName = "";
            }
            setupType(packageName, className);
        }
    }

    @Override
    public String getFullClassName() {
        return mClass.getCanonicalName();
    }

    @Override
    public TypeElement asTypeElement(ProcessingEnvironment processingEnv) {
        return processingEnv.getElementUtils().getTypeElement(mClass.getCanonicalName());
    }
}
