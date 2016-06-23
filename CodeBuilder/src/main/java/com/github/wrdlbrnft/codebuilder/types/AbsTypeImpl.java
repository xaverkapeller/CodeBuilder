package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;

/**
 * Created by kapeller on 09/07/15.
 */
abstract class AbsTypeImpl implements Type {

    private String mClassName;
    private String mPackageName;
    private String mLocalName;

    protected void setupType(String packageName, String className) {
        mClassName = className;
        mPackageName = packageName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getClassName() {
        return mClassName;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        mLocalName = resolver.registerImport(mPackageName, mClassName);
    }

    @Override
    public void write(CodeBuilder builder) {
        builder.append(mLocalName);
    }

    @Override
    public CodeElement newInstance(CodeElement... parameters) {
        return Types.createNewInstance(this, parameters);
    }

    @Override
    public CodeElement classObject() {
        return Types.classOf(this);
    }
}
