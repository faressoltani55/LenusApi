package com.osc.lenus.LenusBackend.controllers;

import com.osc.lenus.LenusBackend.model.local.Access;
import com.osc.lenus.LenusBackend.model.local.Temperature;
import com.osc.lenus.LenusBackend.payload.requests.BContact;
import com.osc.lenus.LenusBackend.services.BeaconServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
public class BeaconsController {
    @Autowired
    private BeaconServices beaconServices;

    @PostMapping("beacon/contact")
    public void saveContact(@RequestBody  BContact bContact) {
        this.beaconServices.saveContact(bContact);
    }

    @PostMapping("beacon/temperature")
    public void saveTemperature(@RequestBody  Temperature temperature) {
        this.beaconServices.saveTemperatureAlert(temperature);
    }

    @PostMapping("beacon/access")
    public void saveAccess(@RequestBody Access access) {
        this.beaconServices.saveAccess(access);
    }
}
