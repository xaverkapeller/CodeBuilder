package com.github.wrdlbrnft.codebuilder.types;

import com.github.wrdlbrnft.codebuilder.code.Block;
import com.github.wrdlbrnft.codebuilder.code.BlockWriter;
import com.github.wrdlbrnft.codebuilder.code.CodeElement;
import com.github.wrdlbrnft.codebuilder.elements.names.Names;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 * Created by kapeller on 03/07/15.
 */
public class Types {

    public static final DefinedType OBJECT = of("java.lang", "Object");
    public static final DefinedType LIST = of("java.util", "List");
    public static final DefinedType SET = of("java.util", "Set");
    public static final DefinedType MAP = of("java.util", "Map");
    public static final DefinedType CLASS = of("java.lang", "Class");
    public static final DefinedType CONCURRENT_HASH_MAP = of("java.util.concurrent", "ConcurrentHashMap");
    public static final DefinedType INPUT_STREAM = of("java.io", "InputStream");
    public static final DefinedType HASH_SET = of("java.util", "HashSet");
    public static final DefinedType ARRAY_LIST = of("java.util", "ArrayList");
    public static final DefinedType COLLECTIONS = of("java.util", "Collections");
    public static final DefinedType STRING = of(String.class);
    public static final DefinedType DATE = of(Date.class);
    public static final DefinedType CALENDAR = of(Calendar.class);

    public static final DefinedType FUTURE = of("java.util.concurrent", "Future");

    public static final DefinedType ATOMIC_INTEGER = of("java.util.concurrent.atomic", "AtomicInteger");
    public static final DefinedType RUNTIME = of("java.lang", "Runtime");
    public static final DefinedType THREAD_FACTORY = of("java.util.concurrent", "ThreadFactory");
    public static final DefinedType RUNNABLE = of("java.lang", "Runnable");
    public static final DefinedType THREAD = of("java.lang", "Thread");

    public static final DefinedType BLOCKING_QUEUE = of("java.util.concurrent", "BlockingQueue");
    public static final DefinedType LINKED_BLOCKING_QUEUE = of("java.util.concurrent", "LinkedBlockingQueue");
    public static final DefinedType EXECUTOR = of("java.util.concurrent", "Executor");
    public static final DefinedType THREAD_POOL_EXECUTOR = of("java.util.concurrent", "ThreadPoolExecutor");
    public static final DefinedType TIME_UNIT = of("java.util.concurrent", "TimeUnit");

    public static final DefinedType WEAK_REFERENCE = of("java.lang.ref", "WeakReference");

    private Types() {
    }

    public static DefinedType of(Class<?> cls) {
        return new ClassElementImpl(cls);
    }

    public static DefinedType of(String packageName, String className) {
        return new NamedElementImpl(packageName, className);
    }

    public static DefinedType of(TypeElement element) {
        return new TypeElementImpl(element);
    }

    public static Type of(TypeMirror mirror) {
        if (mirror instanceof DeclaredType) {
            final DeclaredType declaredType = (DeclaredType) mirror;
            final List<? extends TypeMirror> argumentMirrors = declaredType.getTypeArguments();
            if (argumentMirrors.isEmpty()) {
                return new TypeMirrorImpl(mirror);
            }

            final List<Type> argumentTypes = new ArrayList<>();
            for (TypeMirror argument : argumentMirrors) {
                argumentTypes.add(Types.of(argument));
            }

            return new GenericTypeImpl(new TypeMirrorImpl(mirror), argumentTypes.toArray(new Type[argumentTypes.size()]));
        }

        return new TypeMirrorImpl(mirror);
    }

    public static TypeParameter randomTypeParameter() {
        return new TypeParameterImpl(Names.random());
    }

    public static TypeParameter TypeParameter(String name) {
        return new TypeParameterImpl(Names.fixedName(name));
    }

    public static ArrayType arrayOf(Type itemType) {
        return new ArrayTypeImpl(itemType);
    }

    public static TypeParameter wildCard() {
        return new TypeParameterImpl(Names.fixedName("?"));
    }

    public static GenericType generic(Type baseType, Type... typeParameters) {
        return new GenericTypeImpl(baseType, typeParameters);
    }

    public static CodeElement createNewInstance(Type type, CodeElement[] parameters) {
        return new NewInstanceElementImpl(type, parameters);
    }

    public static CodeElement asCast(final Type type) {
        return new BlockWriter() {
            @Override
            protected void write(Block block) {
                block.append("(").append(type).append(")");
            }
        };
    }

    public static class Exceptions {

        public static final DefinedType IO_EXCEPTION = of("java.io", "IOException");
        public static final DefinedType GENERAL_SECURITY_EXCEPTION = of("java.security", "GeneralSecurityException");
        public static final DefinedType JSON_EXCEPTION = of("org.json", "JSONException");
        public static final DefinedType ILLEGAL_STATE_EXCEPTION = of("java.lang", "IllegalStateException");
        public static final DefinedType NO_SUCH_ELEMENT_EXCEPTION = of("java.util", "NoSuchElementException");
        public static final DefinedType INDEX_OUT_OF_BOUNDS_EXCEPTION = of("java.lang", "IndexOutOfBoundsException");

        private Exceptions() {
        }
    }

    public static CodeElement classOf(final Type type) {
        return new BlockWriter() {
            @Override
            protected void write(Block block) {
                block.append(type).append(".class");
            }
        };
    }

    public static class Primitives {

        public static final Type VOID = of(void.class);
        public static final Type FLOAT = of(float.class);
        public static final Type INTEGER = of(int.class);
        public static final Type LONG = of(long.class);
        public static final Type BOOLEAN = of(boolean.class);
        public static final Type DOUBLE = of(double.class);

        private Primitives() {
        }
    }

    public static class Boxed {

        public static final DefinedType VOID = of(Void.class);
        public static final DefinedType FLOAT = of(Float.class);
        public static final DefinedType INTEGER = of(Integer.class);
        public static final DefinedType LONG = of(Long.class);
        public static final DefinedType BOOLEAN = of(Boolean.class);
        public static final DefinedType DOUBLE = of(Double.class);

        private Boxed() {
        }
    }

    public static class Android {

        public static final DefinedType CONTEXT = of("android.content", "Context");
        public static final DefinedType RECYCLERVIEW_VIEWHOLDER = of("android.support.v7.widget.RecyclerView", "ViewHolder");
        public static final DefinedType SHARED_PREFERENCES = of("android.content", "SharedPreferences");
        public static final DefinedType LAYOUT_INFLATER = of("android.view", "LayoutInflater");
        public static final DefinedType DRAWABLE = of("android.graphics.drawable", "Drawable");
        public static final DefinedType BITMAP = of("android.graphics", "Bitmap");
        public static final DefinedType LOG = of("android.util", "Log");
        public static final DefinedType BITMAP_FACTORY = of("android.graphics", "BitmapFactory");
        public static final DefinedType ASYNC_TASK = of("android.os", "AsyncTask");
        public static final DefinedType INTENT = of("android.content", "Intent");

        private Android() {
        }

        public static class Views {

            public static final DefinedType VIEW = of("android.view", "View");
            public static final DefinedType TEXTVIEW = of("android.widget", "TextView");
            public static final DefinedType BUTTON = of("android.widget", "Button");
            public static final DefinedType COMPOUND_BUTTON = of("android.widget", "Button");
            public static final DefinedType CHECKBOX = of("android.widget", "CheckBox");
            public static final DefinedType IMAGEVIEW = of("android.widget", "ImageView");
            public static final DefinedType VIEW_GROUP = of("android.view", "ViewGroup");

            private Views() {
            }
        }
    }

    private static class NewInstanceElementImpl extends BlockWriter {

        private final Type mType;
        private final CodeElement[] mParameters;

        private NewInstanceElementImpl(Type type, CodeElement[] parameters) {
            mType = type;
            mParameters = parameters;
        }

        @Override
        protected void write(Block block) {
            block.append("new ").append(mType).append("(");
            for (int i = 0, count = mParameters.length; i < count; i++) {
                final CodeElement parameter = mParameters[i];

                if (i > 0) {
                    block.append(", ");
                }

                block.append(parameter);
            }
            block.append(")");
        }
    }
}
