package com.nemo.design.singleton.hungry;

/**
 * @author Nemo
 * 饿汉式静态块单例模式
 */
public class HungryStaticSingleton {

    private static final HungryStaticSingleton hungryStaticSingleton;

    static {
        hungryStaticSingleton = new HungryStaticSingleton();
    }

    private HungryStaticSingleton() {

    }

    public static HungryStaticSingleton getInstance() {
        return hungryStaticSingleton;
    }
}
