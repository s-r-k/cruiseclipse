package org.cruiseclipse.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CCProxy implements java.lang.reflect.InvocationHandler {
	private CruiseControlDataSource obj;

	public static CruiseControlDataSource newInstance(CruiseControlDataSource obj) {
		return (CruiseControlDataSource) Proxy.newProxyInstance(obj.getClass()
				.getClassLoader(), obj.getClass().getInterfaces(), new CCProxy(obj));
	}

	private CCProxy(CruiseControlDataSource obj) {
		this.obj = obj;
	}

	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		Object result;
		try {
			if (!m.getName().equals("updateProjects")) {
				obj.getClass().getMethod("updateProjects").invoke(obj);
			}
			result = m.invoke(obj, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (Exception e) {
			throw new RuntimeException("unexpected invocation exception: "+ e.getMessage());
		}
		return result;
	}
}
