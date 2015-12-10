



import java.util.ArrayList;
import java.util.List;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

/**
 * @author Ahmad Esmaeili 
 *
 * Created on Dec 9, 2015
 */


public class VectorViewExtension extends DefaultClassManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.nlogo.api.DefaultClassManager#load(org.nlogo.api.PrimitiveManager)
	 */
	@Override
	public void load(PrimitiveManager arg0) throws ExtensionException {
		// TODO Auto-generated method stub
		arg0.addPrimitive("export-view-vector", new ExportVector());
	}
	
	public List<String> additionalJars(){
		List<String> l = new ArrayList<String>();
		l.add("VectorGraphics2D-0.9.3.jar");
		return l;
		
	}
	 
}
