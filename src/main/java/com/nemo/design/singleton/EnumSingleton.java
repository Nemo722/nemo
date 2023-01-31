package com.nemo.design.singleton;

/**
 * @author Nemo
 * 枚举式单例模式
 * 写法优雅，但是在类加载之时就会将所有的对象初始化放在类内存中
 * <p>
 * 解决反射造成的实例不一致
 * if ((clazz.getModifiers() & Modifier.ENUM) != 0)
 * throw new IllegalArgumentException("Cannot reflectively create enum objects");
 */
public enum EnumSingleton {

    /**
     * 实例
     */
    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
