package com.ecommerce.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.backend.entities.ContactUs;
import com.ecommerce.backend.exceptions.StatusCode;
import com.ecommerce.backend.mappers.ContactUsMapper;
import com.ecommerce.backend.repositories.ContactUsRepository;
import com.ecommerce.backend.requests.ContactUsRequest;

import jakarta.persistence.EntityNotFoundException;

import com.ecommerce.backend.auth.GenericResponse;
import com.ecommerce.backend.dtos.ContactUsDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
public class ContactUsController {
    private final ContactUsRepository contactUsRepository;
    private final ContactUsMapper contactUsMapper;

    public ContactUsController(ContactUsRepository contactUsRepository, ContactUsMapper contactUsMapper) {
        this.contactUsMapper = contactUsMapper;
        this.contactUsRepository = contactUsRepository;
    }

    @GetMapping
    public List<ContactUsDto> getAllContacts() {
        List<ContactUs> contactUsList = contactUsRepository.findAll();
        return contactUsList.stream()
                .map(contactUsMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<GenericResponse> createContact(@RequestBody ContactUsRequest createdContact) {
        try {
            ContactUs newContact = ContactUs.builder()
                    .name(createdContact.getName())
                    .email(createdContact.getEmail())
                    .comment(createdContact.getComment())
                    .build();
            contactUsRepository.save(newContact);

            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Contact created succesfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error creating contact.")
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<GenericResponse> deleteContactById(@PathVariable Integer id) {
        try {
            ContactUs contact = contactUsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Contact with id " + id + " not found."));
            contactUsRepository.delete(contact);

            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Contact with id " + id + " deleted successfully.")
                    .status(StatusCode.SUCCESS)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Contact with id " + id + " not found.")
                    .status(StatusCode.NOT_FOUND)
                    .build();
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            GenericResponse response = GenericResponse.builder()
                    .responseMessage("Error deleting contact with id: " + id)
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
