package com.github.wrdlbrnft.codebuilder.elements.names;

/**
 * Created by kapeller on 07/07/15.
 */
public class Names {

    private Names() {
    }

    public static NameElement random() {
        return new RandomNameElementImpl();
    }

    public static NameElement preferredName(String preferredName) {
        return new PreferredNameElementImpl(preferredName);
    }

    public static NameElement fixedName(String fixedName) {
        return new FixedNameElementImpl(fixedName);
    }

}
