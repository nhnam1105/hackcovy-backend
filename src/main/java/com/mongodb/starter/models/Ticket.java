package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Ticket extends ObjWithID {

	@JsonSerialize(using = ToStringSerializer.class)
	private String subject;
	private String content;
	private Date createdDate;
	private String creatorName;
	private Date creatorDOB;
	private boolean isEmergent;
	private boolean isSolved;
	private String roomNumber;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreatorDOB() {
		return creatorDOB;
	}

	public void setCreatorDOB(Date creatorDOB) {
		this.creatorDOB = creatorDOB;
	}

	public boolean isEmergent() {
		return isEmergent;
	}

	public void setEmergent(boolean isEmergent) {
		this.isEmergent = isEmergent;
	}

	public boolean isSolved() {
		return isSolved;
	}

	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Override
	public String toString() {
		return "Ticket{id=" + id + ", subject=" + subject + ", content=" + content + ", createdDate=" + createdDate
				+ ", creatorName=" + creatorName + ", creatorDOB=" + creatorDOB + ", isEmergent=" + isEmergent
				+ ", isSolved=" + isSolved + ", roomNumber=" + roomNumber + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createdDate, creatorDOB, creatorName, id, isEmergent, isSolved, roomNumber,
				subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(content, other.content) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(creatorDOB, other.creatorDOB) && Objects.equals(creatorName, other.creatorName)
				&& Objects.equals(id, other.id) && isEmergent == other.isEmergent && isSolved == other.isSolved
				&& Objects.equals(roomNumber, other.roomNumber) && Objects.equals(subject, other.subject);
	}

}
