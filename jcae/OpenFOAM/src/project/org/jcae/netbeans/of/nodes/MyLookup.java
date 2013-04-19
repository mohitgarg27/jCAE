/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import org.openide.util.Lookup;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author mogargaa65
 */
    
public class MyLookup extends ProxyLookup
{		
        public void setDelegates(Lookup... lookups) 
        {
                setLookups(lookups);
        }
}
