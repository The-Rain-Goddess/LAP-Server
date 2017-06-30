/**
 * 
 */
package com.rain.app.server.redux;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Ryan May
 * @param <V>
 * @param <K>
 *
 */
public class MapListener<K, V> extends TreeMap<K, V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8860274434253225593L;
	private Map<K, V> map;
	private Listener listener;

	/**
	 * 
	 */
	public MapListener() {
		map = Collections.synchronizedMap(new TreeMap<K, V>());
	}
	
	public void addListener(Listener listener){
		this.listener = listener;
	}
	
	@Override
    public V put(K key, V val) {
        V ret = map.put(key, val);
        if(listener!=null)
        	listener.invoke(key);
        return ret;
    }

    @Override
    public V remove(Object key) {
        V ret = map.remove(key);
        if(listener!=null)
        	listener.invoke(key);
        return ret;
    }
    
    @Override
    public V get(Object key){
    	if(listener!=null)
        	listener.invoke(key);
    	return map.get(key);
    }
    
    @Override
    public void clear(){
    	map.clear();
    }
    
    @Override
    public boolean containsKey(Object key){
    	return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object val){
    	return map.containsValue(val);
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet(){
    	return map.entrySet();
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> map){
    	this.map.putAll(map);
    }
    
    @Override
    public int size(){
    	return map.size();
    }
    
    @Override
    public String toString(){
    	return map.toString();
    }
}
