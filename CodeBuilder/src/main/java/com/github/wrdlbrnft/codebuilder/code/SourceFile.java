package com.github.wrdlbrnft.codebuilder.code;

import com.github.wrdlbrnft.codebuilder.elements.names.NameElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;
import com.github.wrdlbrnft.codebuilder.implementations.Implementation;
import com.github.wrdlbrnft.codebuilder.types.Type;
import com.github.wrdlbrnft.codebuilder.types.Types;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by kapeller on 07/07/15.
 */
public class SourceFile {

    private final ProcessingEnvironment mProcessingEnvironment;
    private final String mPackageName;
    private final ImportBlock mImportBlock = new ImportBlock();
    private final NameGenerator mNameGenerator = new NameGeneratorImpl();
    private final List<String> mResolvedSources = new ArrayList<>();

    private Implementation mImplementation;
    private String mFileName;

    private final Resolver mResolver = new Resolver() {

        final Map<String, String> mLocalNameMap = new HashMap<>();

        @Override
        public String registerImport(String packageName, String className) {
            final String fullName = packageName + "." + className;
            if (mLocalNameMap.containsKey(fullName)) {
                return mLocalNameMap.get(fullName);
            } else {
                final boolean requiresFullName = mLocalNameMap.values().contains(className);
                final boolean samePackageName = mPackageName.equals(packageName);

                final String localName = requiresFullName ? fullName : className;
                mLocalNameMap.put(fullName, localName);

                if (!requiresFullName && !samePackageName) {
                    final String importName = evaluateImportName(packageName, className);
                    mImportBlock.addImport(importName);
                }

                return localName;
            }
        }

        private String evaluateImportName(String packageName, String className) {
            if (className.contains(".")) {
                final String primaryClassName = className.substring(0, className.indexOf("."));
                return packageName.length() > 0 ? packageName + "." + primaryClassName : primaryClassName;
            }
            return packageName.length() > 0 ? packageName + "." + className : className;
        }

        @Override
        public TypeElement getTypeElement(String fullName) {
            return mProcessingEnvironment.getElementUtils().getTypeElement(fullName);
        }

        @Override
        public TypeElement getTypeElement(TypeMirror mirror) {
            return (TypeElement) mProcessingEnvironment.getTypeUtils().asElement(mirror);
        }

        @Override
        public NameElement getCurrentClassName() {
            return mImplementation.getName();
        }

        @Override
        public NameElement getPackageName() {
            return Names.fixedName(mPackageName);
        }
    };

    protected SourceFile(ProcessingEnvironment processingEnvironment, String packageName, String fileName) {
        mProcessingEnvironment = processingEnvironment;
        mPackageName = packageName;
        mFileName = fileName;
    }

    public static SourceFile create(ProcessingEnvironment environment, String packageName) {
        return new SourceFile(environment, packageName, null);
    }

    public static SourceFile create(ProcessingEnvironment environment, String packageName, String fileName) {
        return new SourceFile(environment, packageName, fileName);
    }

    public Type write(Implementation implementation) throws IOException {
        mImplementation = implementation;

        final CodeBuilder codeBuilder = new CodeBuilder();

        final CodeElement declaration = implementation.getDeclaration();
        declaration.prepare();
        declaration.resolve(mResolver, mNameGenerator);
        declaration.write(codeBuilder);

        final StringBuilder stringBuilder = new StringBuilder();
        codeBuilder.write(stringBuilder, 0);
        final String sourceCode = stringBuilder.toString();
        mResolvedSources.add(sourceCode);

        final NameElement nameElement = mImplementation.getName();
        final String className = nameElement.getResolvedName();

        if (mFileName == null) {
            mFileName = className;
        }

        return Types.of(mPackageName, className);
    }

    public void flushAndClose() throws IOException {
        final Writer writer = getSourceFileWriter();
        flushAndClose(writer);
    }

    public void flushAndClose(Writer writer) throws IOException {
        try {
            tryFlushAndClose(writer);
        } catch (FilerException e) {
            final String errorMessage = "Could not write generated source file: " + mPackageName + "." + mFileName + "!";
            mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, errorMessage);
            Logger.getAnonymousLogger().log(Level.SEVERE, errorMessage, e);
        }
    }

    private Writer getSourceFileWriter() throws IOException {
        final JavaFileObject fileObject = mProcessingEnvironment.getFiler().createSourceFile(mFileName);
        return fileObject.openWriter();
    }

    private void tryFlushAndClose(Writer writer) throws IOException {
        if (mFileName == null) {
            return;
        }

        final CodeElement headerBlock = new BlockWriter() {
            @Override
            protected void write(Block block) {
                block.append("package ").append(mPackageName).append(";").newLine().newLine();
                block.append(mImportBlock).newLine();
            }
        };

        final CodeBuilder codeBuilder = new CodeBuilder();
        headerBlock.prepare();
        headerBlock.resolve(mResolver, mNameGenerator);
        headerBlock.write(codeBuilder);

        final StringBuilder stringBuilder = new StringBuilder();
        codeBuilder.write(stringBuilder, 0);
        final String headerSourceCode = stringBuilder.toString();

        writer.append(headerSourceCode);
        for (int i = 0, count = mResolvedSources.size(); i < count; i++) {
            String sourceCode = mResolvedSources.get(i);
            writer.write(sourceCode);

            if (i < count - 1) {
                writer.write("\n\n");
            }
        }
        writer.flush();
        writer.close();
    }

    private static class ImportBlock extends CodeElement.Adapter {

        private static final Set<String> IMPORT_BLACKLIST = new HashSet<>(Arrays.asList(
                byte.class.getCanonicalName(),
                short.class.getCanonicalName(),
                int.class.getCanonicalName(),
                long.class.getCanonicalName(),
                float.class.getCanonicalName(),
                double.class.getCanonicalName(),
                boolean.class.getCanonicalName(),
                char.class.getCanonicalName(),
                byte[].class.getCanonicalName(),
                short[].class.getCanonicalName(),
                int[].class.getCanonicalName(),
                long[].class.getCanonicalName(),
                float[].class.getCanonicalName(),
                double[].class.getCanonicalName(),
                boolean[].class.getCanonicalName(),
                char[].class.getCanonicalName(),
                void.class.getCanonicalName()
        ));

        private final Set<String> mImports = new HashSet<>();

        @Override
        public void write(CodeBuilder builder) {
            if (!mImports.isEmpty()) {
                for (String className : mImports) {
                    builder.append("import ").append(className).append(";").newLine();
                }
                builder.newLine();
            }
        }

        public void addImport(String fullClassName) {
            if (IMPORT_BLACKLIST.contains(fullClassName)) {
                return;
            }

            mImports.add(fullClassName);
        }
    }
}