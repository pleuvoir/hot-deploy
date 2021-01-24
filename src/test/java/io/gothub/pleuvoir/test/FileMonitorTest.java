package io.gothub.pleuvoir.test;

import java.io.File;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FileMonitorTest {

    public static void main(String[] args) throws Exception {

        IOFileFilter filter = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt"));
        FileAlterationObserver observer = new FileAlterationObserver(
                new File("/Users/pleuvoir/dev/space/git/hot-deploy/target/classes/io/github/pleuvoir"),
                filter);
        observer.addListener(new ClassFileAlterationListenerTest());
        FileAlterationMonitor monitor = new FileAlterationMonitor(1000, observer);
        monitor.start();


    }

}
