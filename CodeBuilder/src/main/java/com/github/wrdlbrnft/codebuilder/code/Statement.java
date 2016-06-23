package com.github.wrdlbrnft.codebuilder.code;

/**
 * Created by kapeller on 21/09/15.
 */
public class Statement extends BlockWriter implements CodeElement {

    private final CodeElement mElement;

    public Statement(CodeElement code) {
        mElement = code;
    }

    @Override
    protected void write(Block block) {
        block.append(mElement).append(";");
    }
}
