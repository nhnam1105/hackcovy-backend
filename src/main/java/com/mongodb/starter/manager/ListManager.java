package com.mongodb.starter.manager;

import com.mongodb.starter.models.ObjWithID;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.ArrayList;

public abstract class ListManager<T extends ObjWithID> {

	private List<T> managedList;

	public List<T> getManagedList() {
		return managedList;
	}

	public void setManagedList(List<T> managedList) {
		this.managedList = managedList;
	}

	public ListManager(List<T> managedList) {
		setManagedList(managedList);
	}

	public T save(T t) {
		t.setId(new ObjectId());
		managedList.add(t);
		return t;
	}

	public List<T> saveAll(List<T> ts) {
		ts.forEach(t -> t.setId(new ObjectId()));
		managedList.addAll(ts);
		return ts;
	}

	public List<T> findAll() {
		List<T> result = new ArrayList<T>();
		result.addAll(managedList);
		return result;
	}

	public List<T> findAll(List<String> ids) {
		List<T> results = new ArrayList<T>();
		for (int i = 0; i < ids.size(); i++) {
			T result = findOne(ids.get(i));
			if (result != null)
				results.add(result);
		}
		if (results.isEmpty())
			return null;
		return results;
	}

	public T findOne(String id) {
		for (int i = 0; i < managedList.size(); i++)
			if (id == managedList.get(i).getId().toHexString())
				return managedList.get(i);
		return null;
	}

	public long count() {
		return managedList.size();
	}

	public long delete(String id) {
		long deletedCount = 0;
		while (count() > 0) {
			T result = findOne(id);
			if (result == null)
				break;
			else {
				managedList.remove(result);
				deletedCount++;
			}
		}
		return deletedCount;
	}

	public long delete(List<String> ids) {
		long deletedCount = 0;
		for (int i = 0; i < ids.size(); i++)
			deletedCount += delete(ids.get(i));
		return deletedCount;
	}

	public long deleteAll() {
		long deletedCount = managedList.size();
		managedList.clear();
		return deletedCount;
	}

	/**
	 * Return the element as it is AFTER the update
	 * 
	 * @param t
	 * @return
	 */
	public T update(T t) {
		String id = t.getId().toHexString();
		T old = findOne(id);
		if (old == null)
			return null;
		else {
			int index = managedList.indexOf(old);
			managedList.remove(index);
			managedList.add(index, t);
			return t;
		}
	}

	public long update(List<T> ts) {
		long updatedCount = 0;
		for (int i = 0; i < ts.size(); i++) {
			T updateResult = update(ts.get(i));
			if (updateResult != null)
				updatedCount++;
		}
		return updatedCount;
	}

}
