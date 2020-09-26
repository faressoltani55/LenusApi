package com.osc.lenus.LenusBackend.controllers;

import com.osc.lenus.LenusBackend.model.central.Client;
import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.repositories.central.AddressRepository;
import com.osc.lenus.LenusBackend.repositories.central.ClientRepository;
import com.osc.lenus.LenusBackend.repositories.central.RequestsRepository;
import com.osc.lenus.LenusBackend.services.MobileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class MobileController {
    @Autowired
    private MobileServices mobileServices;

    @GetMapping("mobile/occupation")
    public List<Occupation> getOccupation() {
        return this.mobileServices.getOccupation();
    }

}
