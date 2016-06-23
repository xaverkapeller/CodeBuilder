package com.github.wrdlbrnft.codebuilder.elements.trycatch;

import com.github.wrdlbrnft.codebuilder.types.Type;

/**
 * Created by kapeller on 16/07/15.
 */
class CatchWrapper {
    private final Type mExceptionType;
    private final TryCatch.CatchHandler mHandler;

    CatchWrapper(Type exceptionType, TryCatch.CatchHandler handler) {
        mExceptionType = exceptionType;
        mHandler = handler;
    }

    public Type getExceptionType() {
        return mExceptionType;
    }

    public TryCatch.CatchHandler getHandler() {
        return mHandler;
    }
}
