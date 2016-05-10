package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;

/**
 * 
 * TransferObject.java
 * used to transfer data between different objects and services
 *
 * @author Michael Sandritter
 */
public class Transport implements Transferable{
	
	private Map<Class<?>, List<?>> listMap;
	private Map<Class<?>, Object> objectMap;
	private Map<Class<?>, Map<?,?>> mapMap;
	
	public Transport() {
		this.listMap = new HashMap<Class<?>, List<?>>();
		this.objectMap = new HashMap<Class<?>, Object>();
		this.mapMap = new HashMap<Class<?>, Map<?,?>>();
	}

	@Override
	public List<?> getList(Class<?> cls) {
		return listMap.get(cls);
	}

	@Override
	public void setList(Class<?> cls, List<?> lst) {
		listMap.put(cls, lst);
	}

	@Override
	public Object getObject(Class<?> cls) {
		return objectMap.get(cls);
	}

	@Override
	public void setObject(Class<?> cls, Object o) {
		objectMap.put(cls, o);
	}

	@Override
	public void setMap(Class<?> cls, Map<?, ?> map) {
		this.mapMap.put(cls, map);
	}

	@Override
	public Map<?, ?> getMap(Class<?> cls) {
		return mapMap.get(cls);
	}
}
