package com.github.wrdlbrnft.codebuilder.variables;

import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.code.NameGenerator;
import com.github.wrdlbrnft.codebuilder.code.Resolver;

/**
 * Created by kapeller on 09/07/15.
 */
class SetElementImpl implements CodeElement {

    private final Variable mVariable;
    private final CodeElement mValue;

    SetElementImpl(Variable variable, CodeElement value) {
        mVariable = variable;
        mValue = value;
    }

    @Override
    public void prepare() {
        mVariable.prepare();
        mValue.prepare();
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        mVariable.resolve(resolver, generator);
        mValue.resolve(resolver, generator);
    }

    @Override
    public void write(CodeBuilder builder) {
        mVariable.write(builder);
        builder.append(" = ");
        mValue.write(builder);
    }
}
