package com.github.wrdlbrnft.codebuilder.code;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kapeller on 07/07/15.
 */
class NameGeneratorImpl implements NameGenerator {

    private static final char[] CHARACTERS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    private final Set<String> mUsedNames = new HashSet<>();

    {
        mUsedNames.add("do");
    }


    private AtomicInteger mNameCounter = new AtomicInteger(1);

    @Override
    public String generate() {
        int currentNumber = mNameCounter.getAndIncrement();
        String result = "";
        while (currentNumber > 0) {
            final int index = (currentNumber - 1) % 26;
            result = CHARACTERS[index] + result;
            currentNumber = (currentNumber - 1) / 26;
        }
        return ensureUniqueName(result);
    }

    @Override
    public String generate(String preferredName) {
        return ensureUniqueName(preferredName);
    }

    private String ensureUniqueName(String name) {
        int counter = 1;
        String nameToTest = name;
        while (mUsedNames.contains(nameToTest)) {
            nameToTest = name + counter++;
        }

        mUsedNames.add(nameToTest);
        return nameToTest;
    }
}
