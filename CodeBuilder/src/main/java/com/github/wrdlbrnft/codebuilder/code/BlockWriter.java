package com.github.wrdlbrnft.codebuilder.code;

/**
 * Created by kapeller on 06/07/15.
 */
public abstract class BlockWriter implements CodeElement {

    private final Block mBlock = new Block();

    private boolean mShouldWrite = true;

    protected abstract void write(Block block);

    @Override
    public void prepare() {
        if (mShouldWrite) {
            mShouldWrite = false;
            write(mBlock);
        }
        mBlock.prepare();
    }

    @Override
    public void resolve(Resolver resolver, NameGenerator generator) {
        mBlock.resolve(resolver, generator);
    }

    @Override
    public void write(CodeBuilder builder) {
        mBlock.write(builder);
    }
}
