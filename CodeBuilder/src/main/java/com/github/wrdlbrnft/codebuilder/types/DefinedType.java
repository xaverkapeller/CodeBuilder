package com.github.wrdlbrnft.codebuilder.types;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 29/08/16
 */

public interface DefinedType extends Type {
    String getFullClassName();
    String getPackageName();
    String getClassName();
    TypeElement asTypeElement(ProcessingEnvironment processingEnv);
}
