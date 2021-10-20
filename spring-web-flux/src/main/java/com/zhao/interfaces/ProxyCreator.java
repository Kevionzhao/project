package com.zhao.interfaces;

/**
 * @author Admin
 */
public interface ProxyCreator {
    /**
     * create proxy class
     * @param clazz
     * @return
     */
    Object createProxy(Class<?> clazz);
}
