package com.osc.lenus.LenusBackend.payload.responses;

import com.osc.lenus.LenusBackend.model.local.Contact;
import com.osc.lenus.LenusBackend.payload.utilities.ContactDot;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ContactGraph {
    List<ContactDot> contactDots;
    List<Contact> contactLines;
}
