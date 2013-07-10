/*
 * Project Info:  http://jcae.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * (C) Copyright 2005-2009, by EADS France
 */


package project.org.jcae.netbeans.of.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.jcae.mesh.xmldata.MeshExporter;
import org.jcae.netbeans.options.OptionNode;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 *
 * @author mohit
 */
public class BrepToSTL 
{
    File fo;
    
    public BrepToSTL(File f)
    {
        fo = f;        
    }
    
    protected static class Redirector extends Thread {

            private final BufferedReader in;
            private final PrintWriter out;

            public Redirector(InputStream in, PrintWriter out) {
                    this.in = new BufferedReader(new InputStreamReader(in));
                    this.out = out;
            }

            @Override
            public void run() {
                    try {
                            String line = in.readLine();
                            while (line != null) {
                                    out.println(line);
                                    line = in.readLine();
                            }
                    } catch (IOException ex) {
                            //the child process has been killed
                            out.println("End of stream");
                    }
            }
    }

    private String join(List<String> l)
    {
            StringBuilder sb = new StringBuilder();
            for(String s:l)
            {
                    sb.append(s);
                    sb.append(' ');
            }
            return sb.toString();
    }

    private void runProcess(ProcessBuilder pb, final InputOutput io) throws IOException 
    {
            io.getOut().println("Running "+ join(pb.command()));
            final Process p = pb.start();
            SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                            io.select();
                    }
            });
            new Redirector(p.getInputStream(), io.getOut()).start();
            new Redirector(p.getErrorStream(), io.getErr()).start();
            final ProgressHandle ph = ProgressHandleFactory.createHandle("mesh",
                            new Cancellable() {
                                    @Override
                                    public boolean cancel() {
                                            p.destroy();
                                            return true;
                                    }
                            });
            ph.start();
            try {
                    p.waitFor();
                    if(p.exitValue() != 0)
                            throw new IOException("The process returned "+p.exitValue());
            } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
            } finally {
                    ph.finish();
            }
    }

    public void performAction() 
    {
        List<String> args = getArguments();
        if (args != null) 
        {
            try
            {
                File pyFile = InstalledFileLocator.getDefault().locate(
                                "amibe-python/mesh.py",  "org.jcae.netbeans", false);
                InputOutput io = IOProvider.getDefault().getIO("mesh", true);
                runInOtherVM(args, pyFile, io);
            }
            catch(IOException ex)
            {
                    Exceptions.printStackTrace(ex);
            }
        }
    }
    
    public void generateSTL()
    {
        String fPath = fo.getAbsolutePath().substring(0,fo.getAbsolutePath().lastIndexOf("."));
        File f = new File(fPath);
        String stlPath = fo.getAbsolutePath().substring(0,fo.getAbsolutePath().lastIndexOf("."))+".stl";
        new MeshExporter.STL(f.getAbsolutePath()).write(stlPath);
        
        // Put the name of the solid in the generated STL file
        //File stlFile = new File( fo.getAbsolutePath().lastIndexOf(".")+".stl");
        
//        try 
//        {
//            RandomAccessFile raf = new RandomAccessFile(fo.getAbsolutePath().lastIndexOf(".")+".stl", "rw");
//            raf.seek(0);
//            String fLine = raf.readLine().trim();
//            if(fLine.startsWith("solid"))
//            {
//                
//            }
//        } 
//        catch (FileNotFoundException ex) 
//        {
//            Exceptions.printStackTrace(ex);
//        } catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
//        }
        
    }

    protected String getClassPath()
    {
            return InstalledFileLocator.getDefault().locate(
                    "modules/ext/amibe.jar", "org.jcae.netbeans", false).getPath();
    }
	
    private void runInOtherVM(List<String> args, File pyFile, InputOutput io)
            throws IOException
    {
            ProcessBuilder pb = new ProcessBuilder();
            String ext = Utilities.isWindows() ? ".bat" : "";
            File f = InstalledFileLocator.getDefault().locate(
                            "modules/jython/bin/jython" + ext, "org.jcae.netbeans.mesh", false);
            pb.command().add(f.getPath());
            for (String s:OptionNode.getJVMOptions()) {
                    if (s.startsWith("-") && !s.startsWith("-D")) {
                            s = "-J" + s;
                    }
                    pb.command().add(s);
            }
            String home = System.getProperty("netbeans.user");
            File dir = new File(new File(new File(new File(home), "var"), "cache"), "jython");
            pb.command().add("-Dpython.cachedir="+dir.getPath());
            pb.environment().put("CLASSPATH", getClassPath());
            pb.command().add(pyFile.getPath());
            pb.command().addAll(args);
            pb.environment().put("JAVA_HOME", System.getProperty("java.home"));		
            runProcess(pb, io);
    }

    protected List<String> getArguments() 
    {

        String parLoc = fo.getAbsolutePath().substring(0,fo.getAbsolutePath().lastIndexOf(".")); 
        File amibeDir = new File(parLoc);
        amibeDir.mkdir();

        ArrayList<String> l = new ArrayList<String>();
        l.add(fo.getAbsolutePath());
        l.add(amibeDir.getAbsolutePath());
        l.add(Double.toString(1.0));
        l.add(Double.toString(0.0));
        return l;
    }

}
