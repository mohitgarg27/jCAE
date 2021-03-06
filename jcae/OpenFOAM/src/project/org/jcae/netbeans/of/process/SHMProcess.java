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

import java.io.IOException;
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
public class SHMProcess extends ExternalProcess
{
    String sName;
    String rName;
    FileObject project;
    
    public SHMProcess(List<String> arguments, String sName,  String rName, FileObject project)
    {        
        super(arguments, sName, project.getPath()+"/"+rName+"/"+sName);
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
             * 1. Generate blockMesh
             * 2. Generate EdgeMesh
             * 3. Generate Castellated mesh
             * 4. CheckMesh
             * 5. Generate Snap mesh
             * 6. CheckMesh
             * 7. Generate Add-Layer mesh
             * 8. CheckMesh         
             */
            try
            {
                // 1
                args = new ArrayList<String>();
                args.add("blockMesh");
                InputOutput io = IOProvider.getDefault().getIO(name, true);                
                super.run(args, io);
                
                // 2
                args = new ArrayList<String>();
                args.add("surfaceFeatureExtract");
                args.add("-includedAngle");
                args.add("150");
                args.add("-writeObj");
                args.add("constant/triSurface/"+name+".stl");
                args.add(name);
                super.run(args, io);
                
                // 3
                args = new ArrayList<String>();
                args.add("snappyHexMesh");                
                super.run(args, io);
                
                // 4
                args = new ArrayList<String>();
                args.add("checkMesh");                
                super.run(args, io);
                
                // 5                                
                ProjectUtils.updateSHMDictWithNewStage(sName, rName, project, "snap");
                args = new ArrayList<String>();
                args.add("snappyHexMesh");
                super.run(args, io);
                
                // 6
                args = new ArrayList<String>();
                args.add("checkMesh");                
                super.run(args, io);
                
                // 7                
                ProjectUtils.updateSHMDictWithNewStage(sName, rName, project, "addLayers");
                args = new ArrayList<String>();
                args.add("snappyHexMesh");                
                super.run(args, io);
                
                // 8                
                args = new ArrayList<String>();
                args.add("checkMesh");                
                super.run(args, io);
                
            }
            catch(IOException ex)
            {
                    Exceptions.printStackTrace(ex);
            }
        }
    }    
}
