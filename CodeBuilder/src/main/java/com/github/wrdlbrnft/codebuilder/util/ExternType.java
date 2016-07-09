package com.github.wrdlbrnft.codebuilder.util;

import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 09/07/16
 */

public class ExternType {

    private final String mPackageName;
    private final String mClassName;

    public ExternType(String packageName, String className) {
        mPackageName = packageName;
        mClassName = className;
    }

    public Type asType() {
        return Types.of(mPackageName, mClassName);
    }

    public String getFullClassName() {
        return mPackageName + "." + mClassName;
    }

    public TypeElement asTypeElement(ProcessingEnvironment processingEnv) {
        final String fullClassName = getFullClassName();
        return processingEnv.getElementUtils().getTypeElement(fullClassName);
    }
}
