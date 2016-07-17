# CodeBuilder

Compile time code generation made simple.

 - **Intuitive and powerful API**: Use an API built around the Builder pattern and immutable objects to generate 
 complex code quickly and safely.
 - **Quick to learn and easy to use**: CodeBuilder includes a powerful resolver engine which automatically generates names
  and ensures consistency and formatting across the generated code. All you need to worry about is the generated code itself!

---

## How to add it to your project

You can try it out by adding this dependency to your annotation processor module:

```groovy
compile 'com.github.wrdlbrnft:code-builder:0.1.0.3'
```

## Basic Hello World example

```java
final Implementation implementation = new Implementation.Builder()
        .setName("HelloWorld")
        .setModifiers(EnumSet.of(Modifier.PUBLIC))
        .addMethod(new Method.Builder()
                .setName("main")
                .setModifiers(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC))
                .setCode(new ExecutableBuilder() {
                    @Override
                    protected List<Variable> createParameters() {
                        return new ArrayList<>();
                    }

                    @Override
                    protected void write(Block block) {
                        block.append("System.out.println(")
                                .append(Values.of("Hello World!"))
                                .append(");");
                    }
                })
                .build())
        .build();

final SourceFile sourceFile = SourceFile.create(processingEnv, "com.example");
sourceFile.write(implementation);
sourceFile.flushAndClose();
```
