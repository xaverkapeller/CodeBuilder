package com.github.wrdlbrnft.codebuilder.code;

/**
 * Created by kapeller on 02/07/15.
 */
public interface CodeElement {

    void prepare();
    void resolve(Resolver resolver, NameGenerator generator);
    void write(CodeBuilder builder);

    abstract class Adapter implements CodeElement {

        @Override
        public void prepare() {

        }

        @Override
        public void resolve(Resolver resolver, NameGenerator generator) {

        }

        @Override
        public void write(CodeBuilder builder) {

        }
    }
}
