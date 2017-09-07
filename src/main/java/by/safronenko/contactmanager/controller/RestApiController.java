package by.safronenko.contactmanager.controller;

import by.safronenko.contactmanager.model.Contact;
import by.safronenko.contactmanager.service.ContactService;
import by.safronenko.contactmanager.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contact/", method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> listAllContacts() {
        List<Contact> contacts = contactService.findAllContacts();
        if (contacts.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getContact(@PathVariable("id") Integer id) {
        Contact contact = contactService.findById(id);
        if (contact == null) {
            return new ResponseEntity(new CustomErrorType("Contact with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact/", method = RequestMethod.POST)
    public ResponseEntity<?> createContact(@RequestBody Contact contact, UriComponentsBuilder ucBuilder) {

        if (contactService.isContactExist(contact)) {
            return new ResponseEntity(new CustomErrorType("Unable to create. Contact with name " +
                    contact.getFirstname() + " already exist."),HttpStatus.CONFLICT);
        }
        contactService.saveContact(contact);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/contact/{id}").buildAndExpand(contact.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContact(@PathVariable("id") Integer id, @RequestBody Contact contact) {

        Contact currentContact = contactService.findById(id);

        if (currentContact == null) {
            return new ResponseEntity(new CustomErrorType("Unable to upate. Contact with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentContact.setFirstname(contact.getFirstname());
        currentContact.setLastname(contact.getLastname());
        currentContact.setTelephone(contact.getTelephone());
        contactService.updateContact(currentContact);
        return new ResponseEntity<>(currentContact, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteContact(@PathVariable("id") Integer id) {

        Contact contact = contactService.findById(id);
        if (contact == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Contact with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        contactService.deleteContactById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/contact/", method = RequestMethod.DELETE)
    public ResponseEntity<Contact> deleteAllUsers() {
        contactService.deleteAllContacts();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
