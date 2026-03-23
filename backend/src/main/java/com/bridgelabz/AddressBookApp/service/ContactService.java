package com.bridgelabz.AddressBookApp.service;

import java.util.*;
import java.util.stream.Collectors;

import com.bridgelabz.AddressBookApp.model.AddressBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.AddressBookApp.dto.ContactDTO;
import com.bridgelabz.AddressBookApp.model.Contact;
import com.bridgelabz.AddressBookApp.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    // UC5: store address book names
    private Set<String> addressBooks = new HashSet<>();
    private Map<String, AddressBook> bookMap = new HashMap<>();
    public String createAddressBook(String name){
        AddressBook book = new AddressBook();
        book.setName(name);

        bookMap.put(name, book);
        addressBooks.add(name);

        return "Address Book Created";
    }

    //  UC6: Add Contact with Duplicate Check
    public Contact addContact(String bookName, ContactDTO dto){


        if(!addressBooks.contains(bookName)){
            throw new RuntimeException("Address Book not found!");
        }

        List<Contact> existingContacts = repository.findAll();

        boolean exists = existingContacts.stream()
                .anyMatch(c ->
                        c.getFirstName().equalsIgnoreCase(dto.firstName)
                                && c.getLastName().equalsIgnoreCase(dto.lastName)
                                && c.getBookName() != null
                                && c.getBookName().equalsIgnoreCase(bookName)
                );

        if (exists) {
            throw new RuntimeException("Duplicate Contact!");
        }

        // Safe object creation (NO constructor issues)
        Contact contact = new Contact();

        contact.setFirstName(dto.firstName);
        contact.setLastName(dto.lastName);
        contact.setAddress(dto.address);
        contact.setCity(dto.city);
        contact.setState(dto.state);
        contact.setZip(dto.zip);
        contact.setPhoneNumber(dto.phoneNumber);
        contact.setEmail(dto.email);
        contact.setBookName(bookName);

        //SAVE in repository
        Contact savedContact = repository.save(contact);

        //  ADD THIS PART HERE (VERY IMPORTANT)
        AddressBook book = bookMap.get(bookName);
        book.getContacts().add(savedContact);

        //return repository.save(contact);
        return savedContact;
    }

    // UC1–UC4: Get All Contacts
    public List<Contact> getAllContacts(){
        return repository.findAll();
    }


    public Contact updateContact(int id, ContactDTO dto){

        Contact existing = repository.findById(id);

        if(existing == null){
            throw new RuntimeException("Contact not found!");
        }

        existing.setFirstName(dto.firstName);
        existing.setLastName(dto.lastName);
        existing.setAddress(dto.address);
        existing.setCity(dto.city);
        existing.setState(dto.state);
        existing.setZip(dto.zip);
        existing.setPhoneNumber(dto.phoneNumber);
        existing.setEmail(dto.email);

        return repository.update(id, existing);
    }

    public void deleteContact(int id){

        if(repository.findById(id) == null){
            throw new RuntimeException("Contact not found!");
        }

        repository.delete(id);
    }

    //  UC7: Search by City
    public List<Contact> searchByCity(String city){
        return repository.findAll().stream()
                .filter(c -> c.getCity() != null &&
                        c.getCity().equalsIgnoreCase(city))
                .toList();
    }

    // UC8: Group by City
    public Map<String, List<Contact>> groupByCity(){
        return repository.findAll().stream()
                .filter(c -> c.getCity() != null)
                .collect(Collectors.groupingBy(Contact::getCity));
    }

    // UC9: Count by City
    public Map<String, Long> countByCity(){
        return repository.findAll().stream()
                .filter(c -> c.getCity() != null)
                .collect(Collectors.groupingBy(
                        Contact::getCity,
                        Collectors.counting()
                ));
    }

    // UC10: Count by State
    public Map<String, Long> countByState(){
        return repository.findAll().stream()
                .filter(c -> c.getState() != null)
                .collect(Collectors.groupingBy(
                        Contact::getState,
                        Collectors.counting()
                ));
    }


    public List<Contact> sortByName(String bookName){
        return repository.findAll().stream()
                .filter(c -> c.getBookName() != null &&
                        c.getBookName().equalsIgnoreCase(bookName))
                .sorted(Comparator
                        .comparing(Contact::getFirstName)
                        .thenComparing(Contact::getLastName))
                .toList();
    }

    // UC11: Sort by City
    public List<Contact> sortByCity(){
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Contact::getCity))
                .toList();
    }
    public List<Contact> sortByCity(String city){
        return repository.findAll().stream()
                .filter(c -> c.getCity() != null &&
                        c.getCity().equalsIgnoreCase(city))
                .sorted(Comparator.comparing(Contact::getFirstName))
                .toList();
    }

    public Contact updateByName(String firstName, String lastName, ContactDTO dto){

        List<Contact> contacts = repository.findAll();

        for(Contact c : contacts){
            if(c.getFirstName().equalsIgnoreCase(firstName)
                    && c.getLastName().equalsIgnoreCase(lastName)){

                // update fields
                c.setAddress(dto.address);
                c.setCity(dto.city);
                c.setState(dto.state);
                c.setZip(dto.zip);
                c.setPhoneNumber(dto.phoneNumber);
                c.setEmail(dto.email);

                repository.update(c.getId(), c);
                return c;
            }
        }

        throw new RuntimeException("Contact not found!");
    }
    public List<Contact> sortByName(){
        return repository.findAll().stream()
                .sorted(Comparator
                        .comparing(Contact::getFirstName)
                        .thenComparing(Contact::getLastName))
                .toList();
    }

}