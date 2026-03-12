package com.bridgelabz.AddressBookApp.repository;


import com.bridgelabz.AddressBookApp.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ContactRepository {

    private Map<Integer, Contact> contactMap = new HashMap<>();
    private int idCounter = 1;

    public Contact save(Contact contact){
        contact.setId(idCounter++);
        contactMap.put(contact.getId(), contact);
        return contact;
    }

    public List<Contact> findAll(){
        return new ArrayList<>(contactMap.values());
    }

    public Contact findById(int id){
        return contactMap.get(id);
    }

    public Contact update(int id, Contact contact){
        contact.setId(id);
        contactMap.put(id, contact);
        return contact;
    }

    public void delete(int id){
        contactMap.remove(id);
    }
}