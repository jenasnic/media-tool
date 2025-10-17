package media.tool.service;

import java.io.File;

public class CurrentPathResolver
{
    protected static String path = null;

    public static String getCurrentPath()
    {
        if (null != path) {
            return path;
        }

        try {
            File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").toURI());
            path = jarDir.getAbsolutePath();
        } catch (Exception e) {
        }

        return path;
    }
}
