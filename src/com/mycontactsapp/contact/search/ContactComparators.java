package com.mycontactsapp.contact.search;

import com.mycontactsapp.contact.model.Contact;
import java.util.Comparator;

public class ContactComparators {

    // Sort by name (A-Z)
    public static final Comparator<Contact> byName = Comparator.comparing(Contact::getName);

    // Sort by date added (oldest first)
    public static final Comparator<Contact> byDateAsc = Comparator.comparing(Contact::getCreatedAt);

    // Sort by date added (newest first)
    public static final Comparator<Contact> byDateDesc = Comparator.comparing(Contact::getCreatedAt).reversed();

    // Sort by frequency (most contacted first)
    public static final Comparator<Contact> byFrequency = Comparator.comparing(Contact::getContactCount).reversed();
}