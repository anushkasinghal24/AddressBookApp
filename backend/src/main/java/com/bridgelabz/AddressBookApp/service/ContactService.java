package com.bridgelabz.AddressBookApp.service;

import com.bridgelabz.AddressBookApp.dto.ContactDTO;
import com.bridgelabz.AddressBookApp.model.Contact;
import com.bridgelabz.AddressBookApp.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    public Contact addContact(ContactDTO dto){

        Contact contact = new Contact(
                0,
                dto.firstName,
                dto.lastName,
                dto.address,
                dto.city,
                dto.state,
                dto.zip,
                dto.phoneNumber,
                dto.email
        );

        return repository.save(contact);
    }

    public List<Contact> getAllContacts(){
        return repository.findAll();
    }

    public Contact updateContact(int id, ContactDTO dto){

        Contact contact = new Contact(
                id,
                dto.firstName,
                dto.lastName,
                dto.address,
                dto.city,
                dto.state,
                dto.zip,
                dto.phoneNumber,
                dto.email
        );

        return repository.update(id,contact);
    }

    public void deleteContact(int id){
        repository.delete(id);
    }
}