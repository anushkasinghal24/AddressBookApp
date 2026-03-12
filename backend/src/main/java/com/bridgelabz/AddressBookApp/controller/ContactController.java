package com.bridgelabz.AddressBookApp.controller;

import com.bridgelabz.AddressBookApp.dto.ContactDTO;
import com.bridgelabz.AddressBookApp.model.Contact;
import com.bridgelabz.AddressBookApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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