package com.github.wrdlbrnft.codebuilder.variables;

import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 21/09/15.
 */
public class Fields {

    private Fields() {
    }

    public static Field create(Type type, String name, Modifier... modifiers) {
        final Set<Modifier> modifierSet = new HashSet<>(Arrays.asList(modifiers));
        return new Field.Builder()
                .setType(type)
                .setName(name)
                .setModifiers(modifierSet)
                .build();
    }

    public static Field stub(String name) {
        return new Field.Builder()
                .setType(Types.Primitives.INTEGER)
                .setName(name)
                .build();
    }
}
