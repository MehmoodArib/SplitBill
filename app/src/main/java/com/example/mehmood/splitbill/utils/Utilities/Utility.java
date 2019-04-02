package com.example.mehmood.splitbill.utils.Utilities;

import com.example.mehmood.splitbill.data.Contact;

import java.util.HashSet;

public class Utility {
    public static final String eventId = "eventId";
    public static final String expense ="expense";
    public static final String errorNumberValidation = "Wrong Participant Number";
    public static final String defaultCurrency ="INR";

    public static final String errorNameValidation ="Field Can't be empty";

    public static final String errorNameValidation2="Maximum 20 characters Allowed";

    public static final String errorDescValidation="Maximum 50 characters Allowed";

    public static String getNameList(HashSet<Contact> contacts)
    {
      StringBuilder sb=new StringBuilder();
        int i = 0;
        for (Contact contact : contacts) {
            sb.append(contact.getName());
            sb.append(i < contacts.size() - 1 ? "," : "");
            i++;

        }
       return sb.toString();
    }
}
