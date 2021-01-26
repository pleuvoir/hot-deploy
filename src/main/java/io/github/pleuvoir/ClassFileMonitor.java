package io.github.pleuvoir;

import java.io.File;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 文件监听
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ClassFileMonitor {


    public static void start() throws Exception {
        IOFileFilter filter = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".class"));
        FileAlterationObserver observer = new FileAlterationObserver(new File(Const.HOT_DEPLOY_DIRECTORY), filter);
        observer.addListener(new ClassFileAlterationListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(Const.HOT_DEPLOY_CLASS_MONITOR_INTERVAL, observer);
        monitor.start();
        System.out.println("热加载文件监听已启动。");
    }

    private static class ClassFileAlterationListener extends FileAlterationListenerAdaptor {

        @Override
        public void onFileChange(File file) {
            System.out.println("文件变化了" + file.getAbsolutePath());
            try {
                //初始化类加载器
                DynamicClassLoader classLoader = new DynamicClassLoader();
                classLoader.addURLs(Const.HOT_DEPLOY_DIRECTORY);
                classLoader.load();

                Bootstrap.start0(classLoader);

            } catch (Throwable e) {
                e.printStackTrace(System.err);
            } finally {
                System.gc();
            }
        }
    }

}
