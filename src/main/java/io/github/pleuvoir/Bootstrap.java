package io.github.pleuvoir;

import java.lang.reflect.Method;

/**
 * 启动类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class Bootstrap {


    public static void main(String[] args) throws Exception {
        //初始化类加载器
        DynamicClassLoader classLoader = new DynamicClassLoader();
        classLoader.addURLs(Const.HOT_DEPLOY_DIRECTORY);
        classLoader.load();
        start0(classLoader);
    }

    public void start() {
        final Mock mock = new Mock();
        mock.say();
    }

    public static void start0(ClassLoader classLoader) throws Exception {
        //启动类文件监听器
        ClassFileMonitor.start();
        //使用全局委派
        Class<?> startupClass = classLoader.loadClass("io.github.pleuvoir.Bootstrap");
        Object startupInstance = startupClass.getConstructor().newInstance();
        String methodName = "start";
        Method method = startupClass.getMethod(methodName);
        method.invoke(startupInstance);
    }

}
