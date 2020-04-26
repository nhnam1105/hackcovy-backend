package com.mongodb.starter.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Post extends ObjWithID {

	@JsonSerialize(using = ToStringSerializer.class)
	private String content;
	private String title;
	private String mediaURL;
	private Date createdDate;
	private String creatorName;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
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

	@Override
	public String toString() {
		return "Post{id=" + id + ", content=" + content + ", title=" + title + ", mediaURL=" + mediaURL
				+ ", createdDate=" + createdDate + ", creatorName=" + creatorName + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createdDate, creatorName, id, mediaURL, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(creatorName, other.creatorName) && Objects.equals(id, other.id)
				&& Objects.equals(mediaURL, other.mediaURL) && Objects.equals(title, other.title);
	}

}
