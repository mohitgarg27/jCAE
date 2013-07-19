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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 * @author mohit
 */
public class SHMZoneProcess extends ExternalProcess
{
    String zoneType; // Should use enum
    String zoneName; // Should use enum
    String method;
    String params;
            
    String sName;
    String rName;
    FileObject project;
    
    public SHMZoneProcess(List<String> arguments, String zName, String zType, String method, String params, String sName,  String rName, FileObject project)
    {        
        super(arguments, sName, project.getPath()+"/"+rName+"/"+sName);
        this.zoneName = zName;
        this.zoneType = zType;
        this.method = method;
        this.params = params;
        this.sName = sName;
        this.rName = rName;
        this.project = project;
    }

    public void performAction() 
    {
        if (args == null) 
        {
            /*
             * Steps:
             * 1. Setset
             * 2. Run execution - cellSet/faceSet in the inner cmd prompt
             * 3. setsToZones
             */
            try
            {
                // 2 - Create a batch file for inner command prompt
                File tmpFile = new File("tmpFile");
                tmpFile.createNewFile();
                PrintWriter pw = new PrintWriter(tmpFile);
                
                String command1 = "";                
                if(zoneType.equalsIgnoreCase("face"))
                    command1 = command1+ " " + "faceSet";
                else if(zoneType.equalsIgnoreCase("cell"))
                    command1 = command1+ " " + "cellSet";                
                command1 = command1 + " " +zoneName + " new";
                command1 = command1 +" " + method;
                command1 = command1 + " " + params;
                command1 = command1 + ";";
                pw.println(command1);
                
                String command2 = "quit;";
                pw.println(command2);
                pw.close();
                
                // 1 - Run setSet command with -latestTime & -batch flags
                args = new ArrayList<String>();
                args.add("setSet");
                args.add("-latestTime");
                args.add("-batch");
                args.add(tmpFile.getAbsolutePath());
                InputOutput io = IOProvider.getDefault().getIO(name, true);
                super.run(args, io);
                tmpFile.delete();
                
                // 3 
                args = new ArrayList<String>();
                args.add("setsToZones");
                args.add("-latestTime");
                args.add("-noFlipMap");
                super.run(args, io);
                
            }
            catch(IOException ex)
            {
                    Exceptions.printStackTrace(ex);
            }
        }
    }    
}
