package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;
import com.github.wrdlbrnft.codebuilder.util.Utils;

import javax.lang.model.element.TypeElement;

/**
 * Created by kapeller on 09/07/15.
 */
class DelayedTypeElementImpl extends AbsTypeImpl {

    private final String mFullClassName;
    private TypeElement mTypeElement;

    DelayedTypeElementImpl(String fullClassName) {
        mFullClassName = fullClassName;
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        if (mTypeElement == null) {
            mTypeElement = resolver.getTypeElement(mFullClassName);
            final String className = Utils.getClassName(mTypeElement);
            final String packageName = Utils.getPackageName(mTypeElement);
            setupType(packageName, className);
        }

        super.resolve(resolver, generator);
    }
}
