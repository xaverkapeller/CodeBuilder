package com.github.wrdlbrnft.codebuilder.types;

/**
 * Created by kapeller on 09/07/15.
 */
class ClassElementImpl extends AbsTypeImpl {

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
}
