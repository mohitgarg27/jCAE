/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.util.Collection;
import org.jcae.netbeans.cad.NbShape;
import org.openide.filesystems.FileObject;

/**
 *
 * @author mita
 */
public class ProjectGeomUtils 
{
    public static double[] getBoundingBox(String sName, String rName, FileObject projectDirectory) 
    {
        boolean firstFlag = true;
        double[] toRet = new double[6];
        Collection<String> patches = ProjectUtils.getPatches(sName, rName, projectDirectory);
        
        for(String patch: patches)
        {
            double[] patchBB = new double[6];
            String loc = projectDirectory.getPath()+"/"+rName+"/"+sName+"/"+patch+".brep";
            NbShape n =  new NbShape(loc);
            patchBB = n.getBounds();
            if(firstFlag)
            {
                for(int i=0;i<6;i++)
                    toRet[i] = patchBB[i];
                firstFlag = false;
            }
            else
            {
                if(toRet[0]>patchBB[0])
                    toRet[0] = patchBB[0];
                if(toRet[1]>patchBB[1])
                    toRet[1] = patchBB[1];
                if(toRet[2]>patchBB[2])
                    toRet[2] = patchBB[2];
                
                if(toRet[3]<patchBB[3])
                    toRet[3] = patchBB[3];
                if(toRet[4]<patchBB[4])
                    toRet[4] = patchBB[4];
                if(toRet[5]<patchBB[5])
                    toRet[5] = patchBB[5];                
            }
        }

        // Buffer it
        int bufferRatio = 2;
        double[] toRetBuff = new double[6];
        
        toRetBuff[0] = toRet[0] - Math.abs((toRet[3]-toRet[0])/bufferRatio);
        toRetBuff[1] = toRet[1] - Math.abs((toRet[4]-toRet[1])/bufferRatio);
        toRetBuff[2] = toRet[2] - Math.abs((toRet[5]-toRet[2])/bufferRatio);

        toRetBuff[3] = toRet[3] + Math.abs((toRet[3]-toRet[0])/bufferRatio);
        toRetBuff[4] = toRet[4] + Math.abs((toRet[4]-toRet[1])/bufferRatio);
        toRetBuff[5] = toRet[5] + Math.abs((toRet[5]-toRet[2])/bufferRatio);
        return toRetBuff;
    }    
}
