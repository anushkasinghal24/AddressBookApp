package com.bridgelabz.AddressBookApp.model;
import java.util.*;
@Data
public class AddressBook {
    private String name;
    private List<Contact> contacts = new ArrayList<>();
}