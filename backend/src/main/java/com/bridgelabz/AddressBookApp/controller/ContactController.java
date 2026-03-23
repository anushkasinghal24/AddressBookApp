package com.bridgelabz.AddressBookApp.controller;
import java.util.List;
import java.util.Map;

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



    @GetMapping
    public List<Contact> getContacts(){
        return contactService.getAllContacts();
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable int id,
                                 @RequestBody ContactDTO dto){
        return contactService.updateContact(id,dto);
    }
    @GetMapping("/sort/city/{city}")
    public List<Contact> sortByCityFiltered(@PathVariable String city){
        return contactService.sortByCity(city);
    }
    @GetMapping("/search/city/{city}")
    public List<Contact> searchByCity(@PathVariable String city){
        return contactService.searchByCity(city);
    }
    @GetMapping("/group/city")
    public Map<String, List<Contact>> groupByCity(){
        return contactService.groupByCity();
    }
    @GetMapping("/count/city")
    public Map<String, Long> countByCity(){
        return contactService.countByCity();
    }
    @GetMapping("/sort/name")
    public List<Contact> sortByName(){
        return contactService.sortByName();
    }
    @GetMapping("/sort/city")
    public List<Contact> sortByCity(){
        return contactService.sortByCity();
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id){
        contactService.deleteContact(id);
        return "Contact Deleted Successfully";
    }

    @PostMapping("/book/{name}")
    public String createBook(@PathVariable String name){
        return contactService.createAddressBook(name);
    }

    @PostMapping("/{bookName}")
    public Contact addContact(@PathVariable String bookName,
                              @RequestBody ContactDTO dto){
        return contactService.addContact(bookName, dto);
    }
    @GetMapping("/sort/name/{bookName}")
    public List<Contact> sortByName(@PathVariable String bookName){
        return contactService.sortByName(bookName);
    }
    @GetMapping("/count/state")
    public Map<String, Long> countByState(){
        return contactService.countByState();
    }
    @PutMapping("/update/{firstName}/{lastName}")
    public Contact updateByName(@PathVariable String firstName,
                                @PathVariable String lastName,
                                @RequestBody ContactDTO dto){

        return contactService.updateByName(firstName, lastName, dto);
    }



}