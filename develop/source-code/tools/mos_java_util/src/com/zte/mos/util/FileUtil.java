package com.zte.mos.util;

import java.io.*;
import java.net.URL;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class FileUtil
{

    public static void saveFile(InputStream is, String target) throws IOException
    {
        try
        {
            if (is == null)
            {
                return;
            }
            File tf = new File(target);
            if (!tf.getParentFile().exists())
            {
                tf.getParentFile().mkdirs();
            }
            FileOutputStream os = new FileOutputStream(target);
            byte[] bytes = new byte[256];
            int len = is.read(bytes);
            while (len >= 0)
            {
                os.write(bytes, 0, len);
                len = is.read(bytes);
            }
            is.close();
            os.flush();
            os.close();
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static boolean isFilePathExist(String filePath)
    {
        File fileTest = new File(filePath);
        if (fileTest.exists() && fileTest.isDirectory())
        {
            return true;
        }
        else
            return false;
    }

    public static Vector<String> readLine(String file, String charset) throws IOException
    {
        try
        {
            Reader reader = new InputStreamReader(new FileInputStream(file), charset);
            int c = reader.read();
            Vector<String> lines = new Vector<String>();
            String line = "";
            while (c > 0)
            {
                if (c == '\n')
                {
                    if (line.charAt(0) != '#')
                    {
                        lines.add(line);
                        line = "";
                    }
                }
                else
                {
                    line += (char) c;
                }
                c = reader.read();
            }
            reader.close();
            return lines;
        }
        catch (UnsupportedEncodingException e)
        {
            throw e;
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static void writeLineFile(String fileName, String buffer)
    {
        try
        {
            Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "GB2312");
            writer.append(buffer);
            writer.flush();
            writer.close();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void copyFile(String srcFile, String target) throws IOException
    {
        copyFile(new File(srcFile), target);
    }

    public static void copyFile(File srcFile, String target) throws IOException
    {
        try
        {
            if (target.trim().length() >= 255)
            {
                throw new IllegalArgumentException(srcFile + " too long!");
            }
            if (srcFile.getAbsolutePath().equalsIgnoreCase(target))
            {
                return;
            }
            if (srcFile.exists())
            {
                File tf = new File(target);
                if (!tf.getParentFile().exists())
                {
                    tf.getParentFile().mkdirs();
                }
                FileInputStream is = new FileInputStream(srcFile);
                FileOutputStream os = new FileOutputStream(target);
                is.getChannel().transferTo(0, srcFile.length(), os.getChannel());
                is.close();
                os.flush();
                os.close();
                tf.setLastModified(srcFile.lastModified());
            }
            else
            {
                // throw new FileNotFoundException(srcFile);
            }
        }
        catch (ClosedByInterruptException e1)
        {
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static void copyFile(String srcFile, File targetDir) throws IOException
    {
        File src = new File(srcFile);
        copyFile(srcFile, targetDir.getAbsolutePath() + "/" + src.getName());
    }

    public static List<File> getFiles(File dir, String suffix, boolean nest)
    {
        return getFiles(dir, suffix, nest, false);
    }

    public static List<File> getFiles(File dir, String suffix, boolean nest, boolean includeDir)
    {
        if (nest)
        {
            List<File> files = new ArrayList<File>();
            FileFilter ff = new SuffixDirFileFilter(suffix);
            getNestFiles(dir, ff, files, includeDir);
            return files;
        }
        else
        {
            return getFiles(dir, suffix);
        }
    }

    public static List<File> getFiles(File dir, String suffix)
    {
        File[] files = dir.listFiles(new SuffixFileFilter(suffix));
        if (files != null)
        {
            Arrays.sort(files);
            return Arrays.asList(files);
        }
        else
        {
            return null;
        }
    }

    public static void getNestFiles(File parentDir, FileFilter ff, List<File> files,
            boolean includeDir)
    {
        File[] fs = parentDir.listFiles(ff);
        for (int i = 0; i < fs.length; i++)
        {
            if (fs[i].isDirectory())
            {
                if (includeDir)
                {
                    files.add(fs[i]);
                }
                getNestFiles(fs[i], ff, files, includeDir);
            }
            else
            {
                files.add(fs[i]);
            }
        }
    }

    public static void searchFile(File parent, List<File> found, FileFilter filter,
            String searchFor)
    {
        File[] files = parent.listFiles(filter);
        List<File> folders = new ArrayList<File>(files.length);
        boolean foundOne = false;
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isDirectory())
            {
                folders.add(files[i]);
            }
            else
            {
                if (searchFor.equalsIgnoreCase(files[i].getName()))
                {
                    found.add(files[i]);
                    foundOne = true;
                    break;
                }
            }
        }
        if (!foundOne)
        {
            for (File dir : folders)
            {
                searchFile(dir, found, filter, searchFor);
            }
        }
    }

    public static boolean ensureDirExists(File file)
    {
        if (!file.exists())
        {
            File pDir = null;
            if (!file.isDirectory())
            {
                pDir = file.getParentFile();
            }
            else
            {
                pDir = file;
            }
            if (!pDir.exists())
            {
                pDir.mkdirs();
            }
            return pDir.exists();
        }
        else
        {
            return true;
        }
    }

    public static boolean deleteFile(String file)
    {
        if (file != null)
        {
            return deleteFile(new File(file));
        }
        else
        {
            return true;
        }
    }

    public static boolean deleteFile(File file)
    {
        if (file.exists())
        {
            if (file.isFile())
            {
                boolean result = file.delete();
                if (result)
                {
                    // System.out.println("Del File:" + file.getAbsolutePath());
                }
                else
                {
                    // System.err.println("Del File Error:" +
                    // file.getAbsolutePath());
                }
                return result;
            }
            else if (file.isDirectory())
            {
                File[] files = file.listFiles();
                if (files != null && files.length > 0)
                {
                    for (File f : files)
                    {
                        if (!deleteFile(f))
                        {
                            return false;
                        }
                    }
                }
                // ɾ��Ŀ¼
                boolean result = file.delete();
                if (result)
                {
                    // System.out.println("Del Folder:" +
                    // file.getAbsolutePath());
                }
                else
                {
                    // System.err.println("Del Folder Error:" +
                    // file.getAbsolutePath());
                }
                return result;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return true;
        }
    }

    public static final StringBuffer readFile(URL url) throws Exception
    {
        if (url == null)
        {
            return null;
        }
        return readFile(url.openStream(), "UTF-8");
    }

    public static final StringBuffer readFile(InputStream is, String charset)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            // byte[] buf = new byte[2048];
            byte[] buf = new byte[is.available()];
            int size = is.read(buf);
            if (size > 0)
            {
                sb.append(new String(buf, 0, size, charset));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (is != null)
            {
            }
        }
        return sb;
    }

    public static void makeFileWritable(File file)
    {
        file.setWritable(true);
    }

    public static void createManifestFile(String mtaMta) throws IOException
    {
        try
        {
            // Replace Create
            File mtaFile = new File(mtaMta);
            FileUtil.ensureDirExists(mtaFile);
            FileWriter writer = new FileWriter(mtaFile, false);
            writer.append("Manifest-Version: 1.0\n");
            // writer.append("Created-By: CMDT " + Preference.getVersion());
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static boolean copyDir(File srcDir, File tgtDir)
    {
        if (srcDir.exists())
        {
            if (!tgtDir.exists())
            {
                if (!tgtDir.mkdirs())
                {
                    return false;
                }
            }
            File[] files = srcDir.listFiles();
            for (File f : files)
            {
                if (f.isFile())
                {
                    try
                    {
                        copyFile(f, tgtDir + "/" + f.getName());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    copyDir(f, new File(tgtDir + "/" + f.getName()));
                }
            }
        }
        return true;
    }

    public static void unzip(InputStream is, File target, long lastModified) throws IOException
    {
        ensureDirExists(target);
        BufferedOutputStream os = null;
        try
        {
            os = new BufferedOutputStream(new FileOutputStream(target));
            byte[] cache = new byte[256];
            int size = 0;
            while ((size = is.read(cache)) > 0)
            {
                os.write(cache, 0, size);
            }
            target.setLastModified(lastModified);
        }
        catch (IOException e2)
        {
            throw e2;
        }
        finally
        {
            if (os != null)
            {
                os.close();
            }
        }
    }

    static class SuffixDirFileFilter implements FileFilter
    {
        private String suffix = null;

        SuffixDirFileFilter(String suffix)
        {
            this.suffix = suffix;
        }

        @Override
        public boolean accept(File pathname)
        {
            return pathname.isDirectory() || pathname.getName().endsWith(suffix);
        }
    }

    static class SuffixFileFilter implements FileFilter
    {
        private String suffix = null;

        SuffixFileFilter(String suffix)
        {
            this.suffix = suffix;
        }

        @Override
        public boolean accept(File pathname)
        {
            return suffix == null ? true : pathname.getName().endsWith(suffix);
        }
    }

}
