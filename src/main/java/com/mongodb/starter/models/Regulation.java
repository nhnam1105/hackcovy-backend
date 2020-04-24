package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Regulation {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String content;
	private String category;
	private int sortingOrder;
	private ObjectId QRTAreaID;

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

	public ObjectId getQRTAreaID() {
		return QRTAreaID;
	}

	public void setQRTAreaID(ObjectId qRTAreaID) {
		QRTAreaID = qRTAreaID;
	}

	@Override
	public String toString() {
		return "Regulation{id=" + id + ", content=" + content + ", category=" + category + ", sortingOrder="
				+ sortingOrder + ", QRTAreaID=" + QRTAreaID + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(QRTAreaID, category, content, id, sortingOrder);
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
		return Objects.equals(QRTAreaID, other.QRTAreaID) && Objects.equals(category, other.category)
				&& Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& sortingOrder == other.sortingOrder;
	}

}
