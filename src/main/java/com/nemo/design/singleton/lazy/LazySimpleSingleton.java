package com.nemo.design.singleton.lazy;

/**
 * @author Nemo
 * 懒汉式单例模式
 * 单例对象要在被使用时才会初始化
 * 存在线程安全隐患
 * synchronized加锁时，在线程数量比较多的情况下，如果CPU分配压力上升，则会导致大批线程阻塞，从而导致程序性能下降
 */
public class LazySimpleSingleton {

    private LazySimpleSingleton() {

    }

    private static LazySimpleSingleton lazy = null;

    public synchronized static LazySimpleSingleton getInstance() {
        if (lazy == null) {
            lazy = new LazySimpleSingleton();
        }
        return lazy;
    }
}
