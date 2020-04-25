package com.mongodb.starter.manager;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mongodb.starter.models.Contact;
import com.mongodb.starter.models.QuarantineArea;
import com.mongodb.starter.repositories.ContactRepository;

@JsonInclude(Include.NON_NULL)
public class ContactManager extends ListManager<Contact> {

	public ContactManager() {
		super();
	}

	public ContactManager(QuarantineArea boss) {
		super(boss);
	}

	@Override
	protected List<Contact> getList() {
		return boss.getContactList();
	}

}
