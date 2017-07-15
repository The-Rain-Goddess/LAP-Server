package com.rain.app.server.redux;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.rain.app.service.riot.api.RiotApi;

public class FutureTask<V> implements Callable<V> {
	RiotApi reference;
	Object[] args;
	Method futureRequest;
	
	public FutureTask(RiotApi api, Method futureRequest, Object... args) {
		this.reference = api;
		this.futureRequest = futureRequest;
		this.args = args;
	}
	
	private void suspend(int seconds){
		try{
			Thread.sleep(seconds * 1000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V call() throws IllegalAccessException, IllegalArgumentException{
		System.out.println("FutureTask: Invoked: " + futureRequest.toGenericString());
		try{
			return (V) futureRequest.invoke(reference, args);
		} catch(InvocationTargetException e){
			suspend(1);
			System.out.println("FutureTask: Service endpoint unavailable, re-requesting...");
			return call();
		} 
	}
}
