package com.elick.pluginlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflector {
    public static final String LOG_TAG = "Reflector";
    protected Class<?> mType;
    protected Object mCaller;
    protected Constructor mConstructor;
    protected Field mField;
    protected Method mMethod;

    public static Reflector on( String var0) {
        return on(var0, true, Reflector.class.getClassLoader());
    }

    public static Reflector on( String var0, boolean var1) {
        return on(var0, var1, Reflector.class.getClassLoader());
    }

    public static Reflector on( String var0, boolean var1,  ClassLoader var2) {
        try {
            return on(Class.forName(var0, var1, var2));
        } catch (Throwable var4) {
            throw new ReflectedException("Oops!", var4);
        }
    }

    public static Reflector on( Class<?> var0) {
        Reflector var1 = new Reflector();
        var1.mType = var0;
        return var1;
    }

    public static Reflector with( Object var0) {
        return on(var0.getClass()).bind(var0);
    }

    protected Reflector() {
    }

    public Reflector constructor( Class<?>... var1) {
        try {
            this.mConstructor = this.mType.getDeclaredConstructor(var1);
            this.mConstructor.setAccessible(true);
            this.mField = null;
            this.mMethod = null;
            return this;

        } catch (Throwable var3) {
            throw new ReflectedException("Oops!", var3);
        }
    }

    public <R> R newInstance( Object... var1) {
        if (this.mConstructor == null) {
            throw new ReflectedException("Constructor was null!");
        } else {
            try {
                return (R) this.mConstructor.newInstance(var1);
            } catch (InvocationTargetException var3) {
                throw new ReflectedException("Oops!", var3.getTargetException());
            } catch (Throwable var4) {
                throw new ReflectedException("Oops!", var4);
            }
        }
    }

    protected Object checked( Object var1) {
        if (var1 != null && !this.mType.isInstance(var1)) {
            throw new ReflectedException("Caller [" + var1 + "] is not a instance of type [" + this.mType + "]!");
        } else {
            return var1;
        }
    }

    protected void check( Object var1,  Member var2,  String var3) {
        if (var2 == null) {
            throw new ReflectedException(var3 + " was null!");
        } else if (var1 == null && !Modifier.isStatic(var2.getModifiers())) {
            throw new ReflectedException("Need a caller!");
        } else {
            this.checked(var1);
        }
    }

    public Reflector bind( Object var1) {
        this.mCaller = this.checked(var1);
        return this;
    }

    public Reflector unbind() {
        this.mCaller = null;
        return this;
    }

    public Reflector field( String var1) {
        try {
            this.mField = this.findField(var1);
            this.mField.setAccessible(true);
            this.mConstructor = null;
            this.mMethod = null;
            return this;
        } catch (Throwable var3) {
            throw new ReflectedException("Oops!", var3);
        }
    }

    protected Field findField( String var1) throws NoSuchFieldException {
        try {
            return this.mType.getField(var1);
        } catch (NoSuchFieldException var6) {
            Class var3 = this.mType;

            while(var3 != null) {
                try {
                    return var3.getDeclaredField(var1);
                } catch (NoSuchFieldException var5) {
                    var3 = var3.getSuperclass();
                }
            }

            throw var6;
        }
    }

    public <R> R get() {
        return this.get(this.mCaller);
    }

    public <R> R get( Object var1) {
        this.check(var1, this.mField, "Field");

        try {
            return (R) this.mField.get(var1);
        } catch (Throwable var3) {
            throw new ReflectedException("Oops!", var3);
        }
    }

    public Reflector set( Object var1) {
        return this.set(this.mCaller, var1);
    }

    public Reflector set( Object var1,  Object var2) {
        this.check(var1, this.mField, "Field");

        try {
            this.mField.set(var1, var2);
            return this;
        } catch (Throwable var4) {
            throw new ReflectedException("Oops!", var4);
        }
    }

    public Reflector method( String var1,  Class<?>... var2) {
        try {
            this.mMethod = this.findMethod(var1, var2);
            this.mMethod.setAccessible(true);
            this.mConstructor = null;
            this.mField = null;
            return this;
        } catch (Exception var4) {
            throw new ReflectedException("Oops!", var4);
        }
    }

    protected Method findMethod( String var1,  Class<?>... var2) {
        try {
            return this.mType.getMethod(var1, var2);
        } catch (NoSuchMethodException var7) {
            Class var4 = this.mType;

            while(var4 != null) {
                try {
                    return var4.getDeclaredMethod(var1, var2);
                } catch (NoSuchMethodException var6) {
                    var4 = var4.getSuperclass();
                }
            }

            throw new ReflectedException("Oops!",var7);
        }
    }

    public <R> R call( Object... var1) {
        return this.callByCaller(this.mCaller, var1);
    }

    public <R> R callByCaller( Object var1,  Object... var2) {
        this.check(var1, this.mMethod, "Method");

        try {
            return (R) this.mMethod.invoke(var1, var2);
        } catch (InvocationTargetException var4) {
            throw new ReflectedException("Oops!", var4.getTargetException());
        } catch (Throwable var5) {
            throw new ReflectedException("Oops!", var5);
        }
    }
    static class ReflectedException extends RuntimeException{
        public ReflectedException(String message) {
            super(message);
        }

        public ReflectedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

