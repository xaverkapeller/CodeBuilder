package com.github.wrdlbrnft.codebuilder.code;

import com.github.wrdlbrnft.codebuilder.variables.Variable;
import com.github.wrdlbrnft.codebuilder.variables.Variables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapeller on 06/07/15.
 */
public class Block implements CodeElement {

    private final List<CodeElement> mElements = new ArrayList<>();

    public Block append(CodeElement element) {
        mElements.add(element);
        return this;
    }

    public Block append(String code) {
        mElements.add(new StringElementImpl(code));
        return this;
    }

    public Block set(Variable variable, CodeElement value) {
        mElements.add(Variables.set(variable, value));
        return this;
    }

    @Override
    public void prepare() {
        for (CodeElement element : mElements) {
            if (element == null) {
                System.out.println(mElements.toString());
                continue;
            }
            element.prepare();
        }
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        for (CodeElement element : mElements) {
            if (element == null) {
                continue;
            }
            element.resolve(resolver, generator);
        }
    }

    @Override
    public void write(CodeBuilder builder) {
        for (CodeElement element : mElements) {
            if (element == null) {
                continue;
            }
            element.write(builder);
        }
    }

    public Block addIndention() {
        mElements.add(new AddIndentionElement());
        return this;
    }

    public Block removeIndention() {
        mElements.add(new RemoveIndentionElement());
        return this;
    }

    public Block newLine() {
        mElements.add(new NewLineElement());
        return this;
    }

    private static class StringElementImpl extends Adapter {

        private final String mString;

        private StringElementImpl(String string) {
            mString = string;
        }

        @Override
        public void write(CodeBuilder builder) {
            builder.append(mString);
        }
    }

    private static class AddIndentionElement extends Adapter {

        @Override
        public void write(CodeBuilder builder) {
            builder.addIndention();
        }
    }

    private static class RemoveIndentionElement extends Adapter {

        @Override
        public void write(CodeBuilder builder) {
            builder.removeIndention();
        }
    }

    private static class NewLineElement extends Adapter {

        @Override
        public void write(CodeBuilder builder) {
            builder.newLine();
        }
    }
}
