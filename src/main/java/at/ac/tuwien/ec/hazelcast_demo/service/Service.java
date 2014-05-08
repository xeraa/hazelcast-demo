package at.ac.tuwien.ec.hazelcast_demo.service;

import java.util.Collection;

public interface Service<T> {

	public void add(T t);

	public T get(T t);

	public T get(String s);

	public Collection<T> getAll();

	public void remove(T t);

	public void remove(String s);

	public void removeAll();
}