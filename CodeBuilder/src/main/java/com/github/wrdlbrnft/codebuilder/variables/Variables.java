package com.github.wrdlbrnft.codebuilder.variables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;
import com.github.wrdlbrnft.codebuilder.types.Type;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 06/07/15.
 */
public class Variables {

    private Variables() {

    }

    public static Variable of(Type type, Modifier... modifiers) {
        final Set<Modifier> modifierSet = new HashSet<>();
        Collections.addAll(modifierSet, modifiers);
        return new Variable.Builder()
                .setModifiers(modifierSet)
                .setType(type)
                .build();
    }

    public static CodeElement set(Variable variable, CodeElement value) {
        return new SetElementImpl(variable, value);
    }

    public static Variable stub(Type type, String name) {
        return new VariableImpl(Collections.<Modifier>emptySet(), Collections.<Annotation>emptyList(), type, Names.fixedName(name), false, false);
    }
}
