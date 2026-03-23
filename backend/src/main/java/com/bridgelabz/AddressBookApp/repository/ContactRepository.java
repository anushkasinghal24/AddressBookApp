package com.bridgelabz.AddressBookApp.repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bridgelabz.AddressBookApp.model.Contact;

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