package io.gothub.pleuvoir.test;

import java.io.File;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ClassFileAlterationListenerTest extends FileAlterationListenerAdaptor {


    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println(observer);
    }

    @Override
    public void onFileChange(File file) {
        System.out.println(file.toString());
        System.out.println(file.getAbsolutePath());
        System.out.println("onFileChange");
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("onFileDelete");
    }
}
