package com.github.wrdlbrnft.codebuilder.types;

/**
 * Created by kapeller on 08/07/15.
 */
public interface GenericType extends Type {
    Type getBaseType();
    Type[] getTypeArguments();
}
