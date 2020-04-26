package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Guest extends ObjWithID {

	@JsonSerialize(using = ToStringSerializer.class)
	private String firstName;
	private String lastName;
	private Date DOB;
	private Status status;
	private Date dateOfAdmission;
	private Gender gender;
	private String roomNumber;
	private List<Ticket> ticketList;
	private String note;

	private enum Status {
		HEALTHY, SHOWING_SYMPTOMS, INFECTED
	};

	private enum Gender {
		MALE, FEMALE, UNDEFINED
	};

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public Date getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(Date dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Guest{id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", DOB=" + DOB + ", status="
				+ status + ", dateOfAdmission=" + dateOfAdmission + ", gender=" + gender + ", roomNumber=" + roomNumber
				+ ", ticketList=" + ticketList + ", note=" + note + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(DOB, dateOfAdmission, firstName, gender, id, lastName, note, roomNumber, status,
				ticketList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guest other = (Guest) obj;
		return Objects.equals(DOB, other.DOB) && Objects.equals(dateOfAdmission, other.dateOfAdmission)
				&& Objects.equals(firstName, other.firstName) && gender == other.gender && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(note, other.note)
				&& Objects.equals(roomNumber, other.roomNumber) && status == other.status
				&& Objects.equals(ticketList, other.ticketList);
	}

}
