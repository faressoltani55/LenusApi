package com.osc.lenus.LenusBackend.controllers;

import com.osc.lenus.LenusBackend.model.central.Feedback;
import com.osc.lenus.LenusBackend.model.local.Recommandation;
import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.services.MobileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class MobileController {
    @Autowired
    private MobileServices mobileServices;

    @GetMapping("mobile/enter/{id}/{resCode}")
    public boolean enter(@PathVariable  String id, @PathVariable String resCode) {
        return this.mobileServices.enter(resCode,id);
    }

    @GetMapping("mobile/occupation")
    public List<Occupation> getOccupation() {
        return this.mobileServices.getOccupation();
    }

    @GetMapping("mobile/recommendation/{resCode}")
    public List<Recommandation> getRecommendations(@PathVariable String resCode) {
        return this.mobileServices.getRecommendations(resCode);
    }

    @PostMapping("mobile/request/{resCode}")
    public void makeRequest(@RequestBody String text, @PathVariable String resCode) {
        this.mobileServices.makeRequest(resCode,text);
    }

    //pour les notifs
    @GetMapping("mobile/request/{resCode}")
    public String getRequestStatus(@PathVariable String resCode) {
        return this.mobileServices.getRequestStatus(resCode);
    }

    @PostMapping("mobile/feedback/{resCode}")
    public void makeFeedback(@PathVariable String resCode, @RequestBody Feedback feedback) {
        this.mobileServices.makeFeedback(resCode,feedback);
    }
}
