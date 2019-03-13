package com.example.mehmood.splitbill.utils.Utilities;

import com.example.mehmood.splitbill.data.Contact;

import java.util.ArrayList;

public class Utility {
    public static final String eventId = "eventId";

    public static final String defaultCurrency ="INR";

    public static final String errorNameValidation ="Field Can't be empty";

    public static final String errorNameValidation2="Maximum 20 characters Allowed";

    public static final String errorDescValidation="Maximum 50 characters Allowed";

    public static String getNameList(ArrayList<Contact> contacts)
    {
      StringBuilder sb=new StringBuilder();
      for (int i=0;i<contacts.size();i++)
      {
          sb.append(contacts.get(i).getName());
          sb.append((i<(contacts.size()-1))?",":"");
      }
       return sb.toString();
    }
}
