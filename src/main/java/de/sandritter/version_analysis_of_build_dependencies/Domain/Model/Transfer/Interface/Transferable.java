package de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface;

import java.util.List;
import java.util.Map;

/**
 * Transferable.java
 * interface defining a transfer object
 *
 * @author Michael Sandritter
 */
public interface Transferable {
	
	/**
	 * get list of generic objects selected by class type
	 * @param cls
	 * @return List<?>
	 */
	public List<?> getList(Class<?> cls);
	
	/**
	 * sets a list of object in a transfer object
	 * @param cls
	 * @param lst
	 */
	public void setList(Class<?> cls, List<?> lst);
	
	/**
	 * returns an object selected by class type
	 * @param cls
	 * @return {@link Object}
	 */
	public Object getObject(Class<?> cls);
	
	/**
	 * sets an an object in an transfer object
	 * @param o
	 */
	public void setObject(Class<?> cls, Object o);
	
	/** 
	 * @param cls
	 * @param map
	 */
	public void setMap(Class<?> cls, Map<?,?> map);
	
	/**
	 * 
	 * @param cls
	 * @return Map<?,?>
	 */
	public Map<?,?> getMap(Class<?> cls);
}
