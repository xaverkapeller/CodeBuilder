package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;

/**
 * Created by kapeller on 09/07/15.
 */
class GenericTypeImpl implements GenericType {

    private final Type mBaseType;
    private final Type[] mTypeParameters;

    GenericTypeImpl(Type baseType, Type[] typeParameters) {
        mBaseType = baseType;
        mTypeParameters = typeParameters;
    }

    @Override
    public void prepare() {
        mBaseType.prepare();
        for (Type type : mTypeParameters) {
            type.prepare();
        }
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        mBaseType.resolve(resolver, generator);
        for (Type type : mTypeParameters) {
            type.resolve(resolver, generator);
        }
    }

    @Override
    public void write(CodeBuilder builder) {
        mBaseType.write(builder);
        builder.append("<");
        for (int i = 0, count = mTypeParameters.length; i < count; i++) {
            final Type type = mTypeParameters[i];

            if (i > 0) {
                builder.append(", ");
            }

            type.write(builder);
        }
        builder.append(">");
    }

    @Override
    public Type getBaseType() {
        return mBaseType;
    }

    @Override
    public Type[] getTypeArguments() {
        return mTypeParameters;
    }

    @Override
    public CodeElement newInstance(CodeElement... parameters) {
        return Types.createNewInstance(this, parameters);
    }

    @Override
    public CodeElement classObject() {
        return mBaseType.classObject();
    }
}
