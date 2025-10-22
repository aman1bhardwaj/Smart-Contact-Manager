package com.scm.scm20.services.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.helper.exceptions.ResourceNotFoundException;
import com.scm.scm20.repositories.ContactRepo;
import com.scm.scm20.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    private Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Override
    public List<Contact> getAllContacts() {

        logger.info("Fetching all the contacts from DB");
        return contactRepo.findAll();
    }

    @Override
    public List<Contact> getContactsByUserId(String userId) {

        logger.info("Getting all the contact details of the userId {}",userId);

        return contactRepo.findContactByUserId(userId);
    }

    @Override
    public Contact saveContact(Contact contact) {

        String contactId = UUID.randomUUID().toString();

        logger.info("Saving contact with Id {}", contactId);
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public void deleteContact(String id) {

        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact Not Found with id " + id));
        logger.info("Contact with id {} deleted successfully", id);
        contactRepo.delete(contact);
        ;
    }

    @Override
    public void updateContact(Contact contact) {

        Contact existingContact = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact is not present"));
        existingContact.setId(contact.getId());
        existingContact.setName(contact.getName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhoneNumber(contact.getPhoneNumber());
        existingContact.setAddress(contact.getAddress());
        existingContact.setPicture(contact.getPicture());
        existingContact.setDescription(contact.getDescription());
        existingContact.setFavourite(contact.isFavourite());
        existingContact.setSocialLinks(contact.getSocialLinks());

        contactRepo.save(existingContact);

        logger.info("Contact with id {} succesfully updated", existingContact.getId());
    }

    @Override
    public List<Contact> getFavouriteContactsByUserId(String userId) {
        
        logger.info("Fetching details of favourite contacts of the user with id {}",userId);
        return contactRepo.findByUser_UserIdAndIsFavouriteTrue(userId);
    }

    @Override
    public Contact getContactById(String id) {

        logger.info("Getting contact details having id {}" , id);

        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact Not present"));
    }

    @Override
    public List<Contact> searchContacts(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchContacts'");
    }

}
