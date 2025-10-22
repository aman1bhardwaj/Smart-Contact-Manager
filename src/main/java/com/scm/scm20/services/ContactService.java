package com.scm.scm20.services;

import java.util.List;

import com.scm.scm20.entities.Contact;

public interface ContactService{

    public List<Contact> getAllContacts();

    public List<Contact> getContactsByUserId(String userId);

    public Contact saveContact(Contact contact);

    public void deleteContact(String id);

    public void updateContact(Contact contact);

    public List<Contact> getFavouriteContactsByUserId(String userId);

    public Contact getContactById(String id);

    public List<Contact> searchContacts(String name ,String email , String phoneNumber);

}
