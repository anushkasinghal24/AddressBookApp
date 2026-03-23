package com.bridgelabz.AddressBookApp.service;
import java.util.*;
import com.bridgelabz.AddressBookApp.model.AddressBook;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {

    private Map<String, AddressBook> addressBooks = new HashMap<>();

    public void addAddressBook(String name) {
        addressBooks.put(name, new AddressBook());
    }
}
