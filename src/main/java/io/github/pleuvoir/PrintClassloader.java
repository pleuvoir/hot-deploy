package io.github.pleuvoir;

import com.sun.scenario.effect.Reflection;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import sun.misc.Launcher;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class PrintClassloader {

    public PrintClassloader() throws SQLException {
    }

    public static void main(String[] args) throws Exception {
        System.out.println(PrintClassloader.class.getClassLoader());
        System.out.println(PrintClassloader.class.getClassLoader().getParent());
        System.out.println(PrintClassloader.class.getClassLoader().getParent().getParent());
        System.out.println(Thread.currentThread().getContextClassLoader());

//        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mysqlDB", "root", "root");

        System.out.println(DriverManager.class.getClassLoader());

    }
}