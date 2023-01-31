package com.nemo.design.singleton.lazy;

/**
 * @author Nemo
 * 采用静态内部类
 */
public class LazyInnerClassSingleton {

    /**
     * 使用LazyInnerClassSingleton的时候，默认会先初始化内部类
     * 如果没有使用，则内部类时不加载的
     */
    private LazyInnerClassSingleton() {
        // 防止反射破坏单例
        if (LazyHolder.LAZY != null) {
            throw new RuntimeException("不容许创建多个实例");
        }
    }

    /**
     * static是为了使单例空间共享，保证这个方法不会被重写，重载
     */
    public static final LazyInnerClassSingleton getInstance() {
        return LazyHolder.LAZY;
    }

    /**
     * 默认不加载
     */
    private static class LazyHolder {
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }

    /**
     * 防止序列化破坏（没有根本解决，只是创建的新对象没有返回）
     */
    private Object readResolve() {
        return LazyHolder.LAZY;
    }
}
