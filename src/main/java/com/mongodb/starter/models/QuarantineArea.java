package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class QuarantineArea extends ObjWithID {

	@JsonSerialize(using = ToStringSerializer.class)
	private String name;
	private String password;
	private String address;
	private Region region;
	private List<Contact> contactList;
	private List<Guest> guestList;
	private List<Post> postList;
	private List<Regulation> regulationList;
	private List<Ticket> ticketList;

	private enum Region {
		MIEN_BAC, MIEN_TRUNG, MIEN_NAM
	};

	public ObjectId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public List<Guest> getGuestList() {
		return guestList;
	}

	public void setGuestList(List<Guest> guestList) {
		this.guestList = guestList;
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public List<Regulation> getRegulationList() {
		return regulationList;
	}

	public void setRegulationList(List<Regulation> regulationList) {
		this.regulationList = regulationList;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "QuarantineArea{id=" + id + ", name=" + name + ", password=" + password + ", address=" + address
				+ ", region=" + region + ", contactList=" + contactList + ", guestList=" + guestList + ", postList="
				+ postList + ", regulationList=" + regulationList + ", ticketList=" + ticketList + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuarantineArea other = (QuarantineArea) obj;
		return Objects.equals(address, other.address) && Objects.equals(contactList, other.contactList)
				&& Objects.equals(guestList, other.guestList) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(postList, other.postList) && region == other.region
				&& Objects.equals(regulationList, other.regulationList) && Objects.equals(ticketList, other.ticketList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, contactList, guestList, id, name, password, postList, region, regulationList,
				ticketList);
	}
}