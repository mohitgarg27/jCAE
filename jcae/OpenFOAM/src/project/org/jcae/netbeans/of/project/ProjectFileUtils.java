/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author mogargaa65
 */
public class ProjectFileUtils 
{
    
    public static boolean makeDir(String basePath, String dirName) {        
        
        File dir = new File(basePath+"/"+dirName);
        if(!dir.exists())
        {
            if(!dir.mkdir())
            {                
                return false;
            }
            else
                return true;
        }
        else
        {
            // empty it 
            String[] children = dir.list();
            for(int i=0; i<children.length; i++) 
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if(!success)
                    return false;;

            }                         
            return true;                
        }
    }
    
    public static boolean makeDir(String dirPath) {
        
        File dir = new File(dirPath);
        if(!dir.exists())
        {
            if(!dir.mkdir())
            {
                return false;
            }
            else
                return true;
        }
        else
        {
            // empty it 
            String[] children = dir.list();
            for(int i=0; i<children.length; i++) 
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if(!success)
                    return false;;
            }                         
            return true;                
        }                
    } 
 
    public static boolean deleteDir(File dir) {
        try
        {
            if(!dir.exists())
                return true;
            if(dir.isDirectory()) 
            {
                String[] children = dir.list();
                for(int i=0; i<children.length; i++) 
                {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if(!success)
                        return false;;
                        
                }
            }            
            return dir.delete();
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    
    public static boolean copyFile(String srcPath, String destPath) {
        try
        {
            File f1 = new File(srcPath);
            File f2 = new File(destPath);
            
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);
            
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf)) > 0) 
            {
                out.write(buf, 0, len);               
            }
            in.close();
            out.close();
            
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }   

    public static boolean copyDir(String srcPath, String destPath) 
    {
        try
        {
            File f1 = new File(srcPath);
            if(f1.isDirectory())
            {
                // request for copy dir
                makeDir(destPath);
                
                String[] children = f1.list();
                for(int i=0; i<children.length; i++) 
                {
                    File child = new File(srcPath, children[i]);
                    if(child.isDirectory())
                    {
                        return copyDir(child.getAbsolutePath(), destPath+"/"+child.getName());
                    }
                    else if(child.isFile())
                    {
                        if(!copyFile(child.getAbsolutePath(), destPath+"/"+child.getName()))
                            return false;
                    }
                    
                }
                return true;
            }
            else if(f1.isFile())
            {
                System.out.println("Error: " +srcPath+ " Not a directory. Select a directory");
                return false;
            }
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }  
    
}
