package com.github.wrdlbrnft.codebuilder.types;

/**
 * Created by kapeller on 21/06/16.
 */

public interface ArrayType extends Type {
    Type getItemType();
}
