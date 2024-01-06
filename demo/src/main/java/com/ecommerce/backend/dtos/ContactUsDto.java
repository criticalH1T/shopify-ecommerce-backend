package com.ecommerce.backend.dtos;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.ecommerce.backend.entities.ContactUs}
 */
@Value
public class ContactUsDto implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String comment;
}
