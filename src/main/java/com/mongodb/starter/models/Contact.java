package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Contact {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String contactName;
	private String contactNumber;
	private ObjectId QRTAreaID;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public ObjectId getQRTAreaID() {
		return QRTAreaID;
	}

	public void setQRTAreaID(ObjectId qRTAreaID) {
		QRTAreaID = qRTAreaID;
	}

	@Override
	public String toString() {
		return "Contact{id=" + id + ", contactName=" + contactName + ", contactNumber=" + contactNumber + ", QRTAreaID="
				+ QRTAreaID + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(QRTAreaID, contactName, contactNumber, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return Objects.equals(QRTAreaID, other.QRTAreaID) && Objects.equals(contactName, other.contactName)
				&& Objects.equals(contactNumber, other.contactNumber) && Objects.equals(id, other.id);
	}

}
