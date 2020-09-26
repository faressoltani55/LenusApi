package com.osc.lenus.LenusBackend.controllers;

import com.osc.lenus.LenusBackend.config.others.EmailService;
import com.osc.lenus.LenusBackend.model.central.*;
import com.osc.lenus.LenusBackend.model.local.*;
import com.osc.lenus.LenusBackend.services.HotelResponsibleServices;
import com.osc.lenus.LenusBackend.services.SuperUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
// @PreAuthorize("hasRole('ADMIN')")
public class SuperUserController {
    @Autowired
    private SuperUserServices superUserServices;

    // SuperUsers routes :
    @GetMapping("/su/superusers")
    public List<SuperUser> getSuperUsers() {
        return this.superUserServices.getSuperUsers();
    }

    @PostMapping("/su/superusers")
    public void addSuperUser(@RequestBody SuperUser superUser) {
        this.superUserServices.addSuperUser(superUser);
    }

    @PatchMapping("/su/superusers")
    public void updateSuperUser(@RequestBody SuperUser superUser) {
        this.superUserServices.updateSuperUser(superUser);
    }

    @DeleteMapping("/su/superusers/{email}")
    public void deleteSuperUser(@PathVariable String email) {
        this.superUserServices.deleteSuperUser(email);
    }


    // HotelChains routes :

    @GetMapping("/su/hotelchains")
    public List<HotelsChain> getHotelChain() {
        return this.superUserServices.getHotelChains();
    }

    @GetMapping("/su/hotelchains/names")
    public List<String> getHotelChainNames() {
        return this.superUserServices.getHotelChainsNames();
    }

    @PostMapping("/su/hotelchains")
    public void addHotelChain(@RequestBody HotelsChain hotelsChain) throws Exception {
        this.superUserServices.addHotelChain(hotelsChain);
    }

    @PatchMapping("/su/hotelchains")
    public void updateHotelChain(@RequestBody HotelsChain hotelsChain) throws Exception {
        this.superUserServices.updateHotelChain(hotelsChain);
    }

    @DeleteMapping("/su/hotelchains/{name}")
    public void deleteHotelChain(@PathVariable String name) throws Exception {
        this.superUserServices.deleteHotelChain(name);
    }

    @PostMapping("/su/departments")
    public void addDepartment(@RequestBody Departement departement) {
        this.superUserServices.addDepartment(departement);
    }

    // Hotels routes :

    @GetMapping("/su/hotels")
    public List<Hotel> getHotels() {
        return this.superUserServices.getHotels();
    }

    @GetMapping("/su/hotels/{chainName}")
    public List<Hotel> getHotelsForChain(@PathVariable String chainName) {
        return this.superUserServices.getHotelsForChain(chainName);
    }

    @PostMapping("/su/hotels/{chainName}")
    public void addHotel(@RequestBody Hotel hotel, @PathVariable String chainName) throws Exception {
        HotelsChain hotelsChain = this.superUserServices.getHotelChainsByName(chainName);
        List<Hotel> hotels = hotelsChain.getHotels();
        hotels.add(hotel);
        hotelsChain.setHotels(hotels);
        this.superUserServices.addHotel(hotel);
        this.superUserServices.updateHotelChain(hotelsChain);
    }

    @GetMapping("/su/hotels/{chainName}/names")
    public List<String> getHotelsNamesForChains(@PathVariable String chainName) {
        return this.superUserServices.getHotelNames(chainName);
    }

    @PatchMapping("/su/hotels")
    public void updateHotel(@RequestBody Hotel hotel) throws Exception {
        this.superUserServices.updateHotel(hotel);
    }

    @DeleteMapping("/su/hotels/{chainName}/{hotelName}")
    public void deleteHotel(@PathVariable String chainName, @PathVariable String hotelName)  {
        this.superUserServices.deleteHotel(chainName,hotelName);
    }

    // Staff Routes :

    @GetMapping("su/responsibles")
    public List<HotelResponsible> getAllResponsibles() {
        return this.superUserServices.getAllResponsibles();
    }

    @GetMapping("su/responsibles/{chainName}/{hotelName}")
    public List<HotelResponsible> getResponsiblesForChainAndHotel(@PathVariable String chainName, @PathVariable String hotelName) {
        return this.superUserServices.getResponsiblesForChainAndHotel(chainName,hotelName);
    }

    @PostMapping("su/responsibles")
    public void addResponsible(@RequestBody HotelResponsible hotelResponsible) {
        this.superUserServices.addResponsible(hotelResponsible);
    }

    @PatchMapping("su/responsibles")
    public void updateResponsible(@RequestBody HotelResponsible hotelResponsible) {
        this.superUserServices.updateResponsible(hotelResponsible);
    }

    @DeleteMapping("su/responsibles/{username}")
    public void deleteResponsible(@PathVariable String username) {
        this.superUserServices.deleteResponsible(username);
    }


    @PostMapping("su/bar")
    public void addBar(@RequestBody Bar bar) {
        this.superUserServices.addBar(bar);
    }

    @PostMapping("su/gym")
    public void addGym(@RequestBody Gym gym) {
        this.superUserServices.addGym(gym);
    }

    @PostMapping("su/pool")
    public void addPool(@RequestBody Pool pool) {
        this.superUserServices.addPool(pool);
    }

    @PostMapping("su/reception")
    public void addReception(@RequestBody Reception reception) {
        this.superUserServices.addReception(reception);
    }

    @PostMapping("su/restaurant")
    public void addRestaurant(@RequestBody Restaurant restaurant) {
        this.superUserServices.addRestaurant(restaurant);
    }

    @PostMapping("su/spa")
    public void addSpa(@RequestBody Spa spa) {
        this.superUserServices.addSpa(spa);
    }

    @PostMapping("su/room/{hotelName}")
    public void addRoom(@PathVariable String hotelName, @RequestBody Room room) {
        this.superUserServices.addRoom(hotelName,room);
    }

    @PostMapping("su/bracelet")
    public void addBracelet(@RequestBody Bracelet bracelet) {
        this.superUserServices.addBracelet(bracelet);
    }

    @PostMapping("su/food/{hoteName}")
    public void addFood(@RequestBody Food food, @PathVariable String hotelName) {
        this.superUserServices.addFood(food,hotelName);
    }

    @PostMapping("su/beacon")
    public void addBeacon(@RequestBody Beacon beacon) {
        this.superUserServices.addBeacon(beacon);
    }

}
