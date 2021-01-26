package io.github.pleuvoir;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 动态类加载器
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class DynamicClassLoader extends URLClassLoader {


    private final Map<String, Class<?>> classCache = new ConcurrentHashMap<>();

    //所有需要我们自己加载的类
    private final Map<String /** classname **/, File> fileCache = new ConcurrentHashMap<>();


    public DynamicClassLoader() {
        super(new URL[0]);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            //查找本地classloader命名空间中是否已有存在的class对象
            //  final Class<?> c = findLoadedClass(name);
            final Class<?> c = classCache.get(name);
            if (c == null) {
                if (fileCache.containsKey(name)) {
                    throw new ClassNotFoundException(name);
                } else {
                    //这个类不需要我们加载（如 java.lang.String 或者我们未指定的路径），交给AppClassloader
                    return getSystemClassLoader().loadClass(name);
                }
            }
            return c;
        }
    }


    /**
     * 递归添加指定目录下的文件到类加载器的加载路径中
     */
    public void addURLs(String directory) throws IOException {
        Collection<File> files = FileUtils.listFiles(
                new File(directory),
                new String[]{"class"},
                true);
        for (File file : files) {
            String className = file2ClassName(file);
            fileCache.putIfAbsent(className, file);
        }
    }


    public void load() throws IOException {
        for (Entry<String, File> fileEntry : fileCache.entrySet()) {
            final File file = fileEntry.getValue();
            this.load(file);
        }
    }

    private Class<?> load(File file) throws IOException {
        final byte[] clsByte = file2Bytes(file);
        final String className = file2ClassName(file);
        Class<?> defineClass = defineClass(className, clsByte, 0, clsByte.length);
        classCache.put(className, defineClass);
        return defineClass;
    }


    private byte[] file2Bytes(File file) {
        try {
            return IOUtils.toByteArray(file.toURI());
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return new byte[0];
        }
    }

    private String file2ClassName(File file) throws IOException {
        final String path = file.getCanonicalPath();
        final String className = path.substring(path.lastIndexOf("/classes") + 9);
        return className.replaceAll("/", ".").replaceAll(".class", "");
    }

}
