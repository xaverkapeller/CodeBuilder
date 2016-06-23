package com.github.wrdlbrnft.codebuilder.code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapeller on 02/07/15.
 */
public class CodeBuilder {

    private static class Line {
        private final List<String> mElements = new ArrayList<>();
        private final int mIndention;

        private Line(int indention) {
            this.mIndention = indention;
        }
    }

    private final List<Line> mLines = new ArrayList<>();

    private Line mCurrentLine;
    private int mCurrentIndention = 0;

    private boolean mShouldCreateNewLine = true;

    public CodeBuilder newLine() {
        ensureNewLine();
        mShouldCreateNewLine = true;
        return this;
    }

    public CodeBuilder append(String code) {
        ensureNewLine();
        mCurrentLine.mElements.add(code);
        return this;
    }

    private void ensureNewLine() {
        if (mShouldCreateNewLine) {
            mShouldCreateNewLine = false;
            mCurrentLine = new Line(mCurrentIndention);
            mLines.add(mCurrentLine);
        }
    }

    public CodeBuilder addIndention() {
        mCurrentIndention++;
        return this;
    }

    public CodeBuilder removeIndention() {
        mCurrentIndention--;
        return this;
    }

    public void write(StringBuilder builder, int baseIndention) {

        for (int i = 0, count = mLines.size(); i < count; i++) {
            final Line line = mLines.get(i);
            final int currentIndention = line.mIndention + baseIndention;

            appendIndention(builder, currentIndention);
            appendLineContent(builder, line);

            if (i < count - 1) {
                appendNewLine(builder);
            }
        }
    }

    private void appendIndention(StringBuilder builder, int indention) {
        for (int i = 0; i < indention; i++) {
            builder.append("\t");
        }
    }

    private void appendLineContent(StringBuilder builder, Line line) {
        for (String text : line.mElements) {
            builder.append(text);
        }
    }

    private void appendNewLine(StringBuilder builder) {
        builder.append("\n");
    }
}
