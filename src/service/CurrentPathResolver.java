package service;

import java.io.File;

public class CurrentPathResolver
{
    protected static String path = null;

    public static String getCurrentPath()
    {
        if (null != path) {
            return path;
        }

        File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
        path = jarDir.getAbsolutePath();

        return path;
    }
}
