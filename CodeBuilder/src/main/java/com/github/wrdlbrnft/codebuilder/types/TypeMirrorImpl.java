package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;
import com.github.wrdlbrnft.codebuilder.util.Utils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by kapeller on 09/07/15.
 */
class TypeMirrorImpl extends AbsTypeImpl implements Type {

    private final TypeMirror mMirror;

    TypeMirrorImpl(TypeMirror mirror) {
        mMirror = mirror;
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        if (getClassName() == null) {
            final TypeElement element = resolver.getTypeElement(mMirror);
            if (element == null) {
                setupType("", mMirror.toString());
            } else {
                final String className = Utils.getClassName(element);
                final String packageName = Utils.getPackageName(element);
                setupType(packageName, className);
            }
        }

        super.resolve(resolver, generator);
    }
}
