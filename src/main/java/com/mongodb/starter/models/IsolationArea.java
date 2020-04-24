package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class IsolationArea {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String name;
	private String password;
	private String address;
	private Region region;
	private List<Guest> guestList;
	private List<Ticket> ticketList;
	private List<Post> postList;
	private List<Regulation> regulationList;

	private enum Region {
		MienBac, MienTrung, MienNam
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
		return "IsolationArea{id=" + id + ", name=" + name + ", password=" + password + ", address=" + address
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
		IsolationArea other = (IsolationArea) obj;
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