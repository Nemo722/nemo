package com.nemo.design.singleton.hungry;

/**
 * @author Nemo
 * 饿汉式单例模式
 * 饿汉式单例模式在类加载的时候就立即初始化，并且创建单例对象
 * 优点：绝对线程安全,执行效率比价高
 * 缺点：所有对象类加载的时候就实例化
 */
public class HungrySingleton {

    private static final HungrySingleton hungrysingleton = new HungrySingleton();

    private HungrySingleton() {

    }

    public static HungrySingleton getInstance() {
        return hungrysingleton;
    }
}
