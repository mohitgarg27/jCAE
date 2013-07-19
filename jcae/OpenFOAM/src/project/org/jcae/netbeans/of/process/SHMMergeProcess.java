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
public class SHMMergeProcess extends ExternalProcess
{
    // sName2 will merge INTO sName1
    
    String sName1;
    String sName2;
    String rName;
    FileObject project;
    
    public SHMMergeProcess(List<String> arguments, String sName1, String sName2,  String rName, FileObject project)
    {        
        super(arguments, sName1+"-Merge-"+sName2, project.getPath()+"/"+rName);
        this.sName1 = sName1;
        this.sName2 = sName2;
        this.rName = rName;
        this.project = project;
    }

    public void performAction() 
    {
        if (args == null) 
        {
             try
            {                
                args = new ArrayList<String>();
                args.add("mergeMeshes");
                args.add(sName1);
                
                args.add(sName2);
                InputOutput io = IOProvider.getDefault().getIO(name, true);
                super.run(args, io);
            }
            catch(IOException ex)
            {
                    Exceptions.printStackTrace(ex);
            }
        }
    }    
}
