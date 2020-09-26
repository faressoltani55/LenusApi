package com.osc.lenus.LenusBackend.services;

import com.osc.lenus.LenusBackend.model.central.Hotel;
import com.osc.lenus.LenusBackend.model.central.Zone;
import com.osc.lenus.LenusBackend.model.local.Access;
import com.osc.lenus.LenusBackend.model.local.Beacon;
import com.osc.lenus.LenusBackend.model.local.Recommandation;
import com.osc.lenus.LenusBackend.payload.responses.MobileHomePageData;
import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.repositories.central.HotelsRepository;
import com.osc.lenus.LenusBackend.repositories.central.ZoneRepository;
import com.osc.lenus.LenusBackend.repositories.local.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MobileServices {
    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SpaRepository spaRepository;

    public List<Occupation> getOccupation() {
        List<Zone> hotelZones = new ArrayList<>();
        hotelZones.addAll(this.receptionRepository.findAll());
        hotelZones.addAll(this.barRepository.findAll());
        hotelZones.addAll(this.gymRepository.findAll());
        hotelZones.addAll(this.restaurantRepository.findAll());
        hotelZones.addAll(this.poolRepository.findAll());
        hotelZones.addAll(this.spaRepository.findAll());
        List<Occupation> occupations = new ArrayList<>();
        Date oneMinBack = new Date(System.currentTimeMillis() - 60 * 1000);
        for(int i=0; i<hotelZones.size(); i++) {
            Zone zone = hotelZones.get(i);
            zone.setOccupation(0);
            List<Beacon> beacons = zone.getBeacons();
            List<Access> accesses = new ArrayList<>();
            for(Beacon beacon : beacons) {
                accesses.addAll(this.accessRepository.findByBeaconId(beacon.getId(),oneMinBack));
            }
            zone.setOccupation(accesses.size());
            occupations.add(new Occupation(zone.getName(),(double) zone.getOccupation() / zone.getSize()));
        }
        return occupations;
    }


}
