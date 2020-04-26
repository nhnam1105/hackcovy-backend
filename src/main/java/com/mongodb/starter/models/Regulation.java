package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Regulation extends ObjWithID {

	@JsonSerialize(using = ToStringSerializer.class)
	private String content;
	private String category;
	private int sortingOrder;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSortingOrder() {
		return sortingOrder;
	}

	public void setSortingOrder(int sortingOrder) {
		this.sortingOrder = sortingOrder;
	}

	@Override
	public String toString() {
		return "Regulation{id=" + id + ", content=" + content + ", category=" + category + ", sortingOrder="
				+ sortingOrder + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, content, id, sortingOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Regulation other = (Regulation) obj;
		return Objects.equals(category, other.category) && Objects.equals(content, other.content)
				&& Objects.equals(id, other.id) && sortingOrder == other.sortingOrder;
	}

}
