package com.github.wrdlbrnft.codebuilder.variables;

import com.github.wrdlbrnft.codebuilder.annotations.Annotation;
import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeBuilder;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.executables.Method;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.util.BlockHelper;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Created by kapeller on 06/07/15.
 */
class FieldImpl extends VariableImpl implements Field {

    private final List<Annotation> mAnnotations;
    private final List<Field> mFields;
    private final List<Method> mMethods;
    private final CodeElement mInitialValue;

    public FieldImpl(Type type, Set<Modifier> modifiers, NameElement name, List<Annotation> annotations, CodeElement value, List<Field> fields, List<Method> methods) {
        super(modifiers, annotations, type, name, false);
        mAnnotations = annotations;
        mInitialValue = value;
        mFields = fields;
        mMethods = methods;
    }

    @Override
    public void write(CodeBuilder builder) {
        final NameElement name = getName();
        name.write(builder);
    }

    @Override
    public CodeElement getDeclaration() {
        return new DeclarationElement(this);
    }

    public List<Annotation> getAnnotations() {
        return mAnnotations;
    }

    public CodeElement getInitialValue() {
        return mInitialValue;
    }

    public List<Method> getMethods() {
        return mMethods;
    }

    public List<Field> getFields() {
        return mFields;
    }

    private static class DeclarationElement extends BlockWriter {

        private final FieldImpl mField;

        private DeclarationElement(FieldImpl field) {
            mField = field;
        }

        @Override
        protected void write(Block block) {
            final Type type = mField.getType();
            final Set<Modifier> modifiers = mField.getModifiers();
            final List<Annotation> annotations = mField.getAnnotations();
            final CodeElement initialValue = mField.getInitialValue();
            final List<Field> fields = mField.getFields();
            final List<Method> methods = mField.getMethods();

            BlockHelper.appendAnnotationList(block, annotations);
            BlockHelper.appendModifiers(block, modifiers);

            block.append(type).append(" ").append(mField);

            if (initialValue != null) {
                block.append(" = ").append(initialValue);
            }

            if (!fields.isEmpty() || !methods.isEmpty()) {
                block.append(" {").addIndention().newLine();

                for (Field field : fields) {
                    block.append(field.getDeclaration()).newLine();
                }

                for (Method method : methods) {
                    block.newLine();
                    block.append(method.getDeclaration());
                    block.newLine();
                }

                block.removeIndention().append("}");
            }

            block.append(";");
        }
    }
}
