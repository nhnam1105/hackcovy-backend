package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class QuarantineArea {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String name;
	private String password;
	private String address;
	private Region region;
	private List<ObjectId> guestList;
	private List<ObjectId> ticketList;
	private List<ObjectId> postList;
	private List<ObjectId> regulationList;

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

	public List<ObjectId> getGuestList() {
		return guestList;
	}

	public void setGuestList(List<ObjectId> guestList) {
		this.guestList = guestList;
	}

	public List<ObjectId> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<ObjectId> ticketList) {
		this.ticketList = ticketList;
	}

	public List<ObjectId> getPostList() {
		return postList;
	}

	public void setPostList(List<ObjectId> postList) {
		this.postList = postList;
	}

	public List<ObjectId> getRegulationList() {
		return regulationList;
	}

	public void setRegulationList(List<ObjectId> regulationList) {
		this.regulationList = regulationList;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "QuarantineArea{id=" + id + ", name=" + name + ", password=" + password + ", address=" + address
				+ ", region=" + region + ", guestList=" + guestList + ", ticketList=" + ticketList + ", postList="
				+ postList + ", regulationList=" + regulationList + "}";
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
		return Objects.equals(address, other.address) && Objects.equals(guestList, other.guestList)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(postList, other.postList)
				&& region == other.region && Objects.equals(regulationList, other.regulationList)
				&& Objects.equals(ticketList, other.ticketList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, guestList, id, name, password, postList, region, regulationList, ticketList);
	}

}