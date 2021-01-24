package io.gothub.pleuvoir.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FileUtilTest {

    public static void main(String[] args) throws IOException {

        final Collection<File> jars = FileUtils.listFiles(
                new File("/Users/pleuvoir/dev/space/maven"),
                new String[]{"jar"},
                true);
        for (File jar : jars) {
            System.out.println(jar.getCanonicalPath());
        }
    }
}
