package com.nemo.design.singleton.lazy;

/**
 * @author Nemo
 * 双重检查锁
 */
public class LazyDoubleCheckSingleton {

    private LazyDoubleCheckSingleton() {

    }

    /**
     * volatile 关键字避免指令重排序，防止并发返回一个没构造完全的实例
     */
    private volatile static LazyDoubleCheckSingleton lazy = null;

    public synchronized static LazyDoubleCheckSingleton getInstance() {
        if (lazy == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (lazy == null) {
                    lazy = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazy;
    }

    /**
     * 防止克隆破坏单列
     */
    @Override
    protected LazyDoubleCheckSingleton clone() {
        return lazy;
    }
}
