package by.safronenko.contactmanager.service;

import by.safronenko.contactmanager.model.Contact;
import by.safronenko.contactmanager.reposiroty.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ContactService")
@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact findById(Integer id) {
        return contactRepository.findOne(id);
    }

    public Contact findByFirstname(String firstname) {
        return contactRepository.findByFirstname(firstname);
    }

    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void updateContact(Contact contact) {
        saveContact(contact);
    }

    public void deleteContactById(Integer id) {
        contactRepository.delete(id);
    }

    public void deleteAllContacts() {
        contactRepository.deleteAll();
    }

    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    public boolean isContactExist(Contact contact) {
        return findByFirstname(contact.getFirstname()) != null;
    }
}
