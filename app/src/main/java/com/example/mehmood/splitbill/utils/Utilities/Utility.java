package com.example.mehmood.splitbill.utils.Utilities;

import com.example.mehmood.splitbill.data.Contact;

import java.util.ArrayList;

public class Utility {
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
