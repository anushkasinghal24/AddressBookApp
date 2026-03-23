package com.bridgelabz.AddressBookApp.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.AddressBookApp.dto.ContactDTO;
import com.bridgelabz.AddressBookApp.model.Contact;
import com.bridgelabz.AddressBookApp.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public Contact createContact(@RequestBody ContactDTO dto){
        return contactService.addContact(dto);
    }

    @GetMapping
    public List<Contact> getContacts(){
        return contactService.getAllContacts();
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable int id,
                                 @RequestBody ContactDTO dto){
        return contactService.updateContact(id,dto);
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id){
        contactService.deleteContact(id);
        return "Contact Deleted Successfully";
    }
}