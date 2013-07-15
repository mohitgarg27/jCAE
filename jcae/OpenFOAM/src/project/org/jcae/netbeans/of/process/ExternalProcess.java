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

package project.org.jcae.netbeans.of.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.SwingUtilities;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.windows.InputOutput;

/**
 * @author mohit
 */
public abstract class ExternalProcess 
{
    List<String> args;
    String name;
    String path;
    private InputOutput io;
                
    public ExternalProcess(List<String> arguments, String name, String runPath)
    {        
        args = arguments;
        this.name = name;
        this.path = runPath;
    }

    /**
     * @return the io
     */
    public InputOutput getIo() {
        return io;
    }

    /**
     * @param io the io to set
     */
    public void setIo(InputOutput io) {
        this.io = io;
    }
    
    protected static class Redirector extends Thread 
    {
            private final BufferedReader in;
            private final PrintWriter out;

            public Redirector(InputStream in, PrintWriter out) 
            {
                    this.in = new BufferedReader(new InputStreamReader(in));
                    this.out = out;
            }

            @Override
            public void run() 
            {
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
            final ProgressHandle ph = ProgressHandleFactory.createHandle(name,
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

    public abstract void performAction() ;
    
    
    protected void run(List<String> args, InputOutput io) throws IOException
    {
            ProcessBuilder pb = new ProcessBuilder();            
            pb.command().addAll(args);
            File pwd = new File(path);
            pb = pb.directory(pwd);
            runProcess(pb, io);
    }
}
