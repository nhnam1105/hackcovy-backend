package com.mongodb.starter.manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mongodb.starter.models.ObjWithID;
import com.mongodb.starter.models.QuarantineArea;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.ArrayList;

@JsonInclude(Include.NON_NULL)
public abstract class ListManager<T extends ObjWithID> {

	@JsonSerialize(using = ToStringSerializer.class)
	protected QuarantineArea boss;

	public QuarantineArea getBoss() {
		return boss;
	}

	public void setBoss(QuarantineArea boss) {
		this.boss = boss;
	}

	public ListManager() {

	}

	public ListManager(QuarantineArea boss) {
		setBoss(boss);
	}

	protected abstract List<T> getList();

	public T save(T t) {
		t.setId(new ObjectId());
		getList();
		
		return t;
	}

	public List<T> saveAll(List<T> ts) {
		ts.forEach(t -> t.setId(new ObjectId()));
		getList().addAll(ts);
		return ts;
	}

	public List<T> findAll() {
		List<T> result = new ArrayList<T>();
		result.addAll(getList());
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
		for (int i = 0; i < getList().size(); i++)
			if (id == getList().get(i).getId().toHexString())
				return getList().get(i);
		return null;
	}

	public long count() {
		return getList().size();
	}

	public long delete(String id) {
		long deletedCount = 0;
		while (count() > 0) {
			T result = findOne(id);
			if (result == null)
				break;
			else {
				getList().remove(result);
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
		long deletedCount = getList().size();
		getList().clear();
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
			int index = getList().indexOf(old);
			getList().remove(index);
			getList().add(index, t);
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
