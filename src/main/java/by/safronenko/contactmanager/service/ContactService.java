package by.safronenko.contactmanager.service;

import by.safronenko.contactmanager.model.Contact;

import java.util.List;

public interface ContactService {

    Contact findById(Integer id);

    Contact findByFirstname(String firstname);

    void saveContact(Contact contact);

    void  updateContact(Contact contact);

    void deleteContactById(Integer id);

    void deleteAllContacts();

    List<Contact> findAllContacts();

    boolean isContactExist(Contact contact);
}
