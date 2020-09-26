package com.osc.lenus.LenusBackend.services;

import com.osc.lenus.LenusBackend.model.central.*;
import com.osc.lenus.LenusBackend.model.local.*;
import com.osc.lenus.LenusBackend.repositories.central.*;
import com.osc.lenus.LenusBackend.repositories.local.*;
import com.osc.lenus.LenusBackend.security.models.ERole;
import com.osc.lenus.LenusBackend.security.models.Role;
import com.osc.lenus.LenusBackend.security.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuperUserServices {

    //Managing SuperUsers :
    @Autowired
    private SuperUsersRepository superUsersRepository;

    public List<SuperUser> getSuperUsers() {
        return this.superUsersRepository.findAll();
    }

    public void addSuperUser(SuperUser superUser) {
        this.superUsersRepository.save(superUser);
    }

    public void updateSuperUser(SuperUser superUser) {
        this.superUsersRepository.deleteByEmail(superUser.getEmail());
        this.superUsersRepository.save(superUser);
    }

    public void deleteSuperUser(String email) {
        this.superUsersRepository.deleteByEmail(email);
    }


    // Managing HotelChains Dashboard :

    @Autowired
    private HotelChainRepository hotelChainRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<HotelsChain> getHotelChains() {
        return this.hotelChainRepository.findAll();
    }

    public void addHotelChain(HotelsChain hotelsChain) throws Exception {
        if(!this.hotelChainRepository.existsByName(hotelsChain.getName())) {
            hotelsChain.setHotels(new ArrayList<>());
            this.hotelChainRepository.save(hotelsChain);
        }
        else throw new Exception("Chain Already Exists");
    }

    public void updateHotelChain(HotelsChain hotelsChain) throws Exception {
        if(this.hotelChainRepository.existsByName(hotelsChain.getName())) {
            this.hotelChainRepository.save(hotelsChain);
        }
        else throw new Exception("Chain Don't Exists");
    }

    public void deleteHotelChain(String name) throws Exception {
        if(this.hotelChainRepository.existsByName(name))
            this.hotelChainRepository.deleteByName(name);
        else throw new Exception("Chain Doesn't Exists");
    }

    public List<String> getHotelChainsNames() {
        List<HotelsChain> list = this.getHotelChains();
        ArrayList<String> names = new ArrayList<>();
        for(HotelsChain chaine : list) names.add(chaine.getName());
        return names;
    }

    public HotelsChain getHotelChainsByName(String name) {
        return this.hotelChainRepository.findByName(name);
    }

    // Managing Hotels Dashboard :

    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<Hotel> getHotels() {
        return this.hotelsRepository.findAll();
    }

    public List<Hotel> getHotelsForChain(String chainName) {
        return this.hotelChainRepository.findByName(chainName).getHotels();
    }

    public List<String> getHotelNames(String chainName) {
        HotelsChain hotelsChain = this.getHotelChainsByName(chainName);
        ArrayList<String> names = new ArrayList<>();
        for(Hotel hotel : hotelsChain.getHotels()) names.add(hotel.getName());
        return names;
    }

    public void addHotel(Hotel hotel) throws Exception {
        if(!this.hotelsRepository.existsByName(hotel.getName())) {
            hotel.setContacts(new ArrayList<>());
            hotel.setZones(new ArrayList<>());
            hotel.setDepartements(new ArrayList<>());
            hotel.setRooms(new ArrayList<>());
            this.addressRepository.save(hotel.getAddress());
            this.hotelsRepository.save(hotel);
        }
        else throw new Exception("Hotel Already Exists");
    }

    public void updateHotel(Hotel hotel) throws Exception {
        if(this.hotelsRepository.existsByName(hotel.getName())) {
            this.addressRepository.delete(hotel.getAddress());
            this.addressRepository.save(hotel.getAddress());
            this.hotelsRepository.delete(hotel);
            this.hotelsRepository.save(hotel);
        }
        else throw new Exception("Hotel Doesn't Exists");
    }

    public void deleteHotel(String chainName, String hotelName) {
            this.hotelsRepository.deleteByNameAndChainName(hotelName, chainName);
    }

    public void addDepartment(Departement departement) {
        departement.setResponsibles(new ArrayList<>());
        departement.setStaff(new ArrayList<>());
        this.departmentRepository.save(departement);
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Departement> departements = hotel.getDepartements();
        departements.add(departement);
        this.hotelsRepository.save(hotel);
    }


    // Managing Responsibles Dashboard :

    @Autowired
    private HotelResponsibleRepository hotelResponsibleRepository;

    public List<HotelResponsible> getResponsiblesForChainAndHotel(String chainName, String hotelName) {
        return this.hotelResponsibleRepository.findByChainNameAndHotelName(chainName,hotelName);
    }

    public List<HotelResponsible> getAllResponsibles() {
        return this.hotelResponsibleRepository.findAll();
    }

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void addResponsible(HotelResponsible hotelResponsible) {
        Departement departement = this.departmentRepository.findById(hotelResponsible.getDepartmentId()).get();
        List<HotelResponsible> hotelResponsibles = departement.getResponsibles();
        hotelResponsibles.add(hotelResponsible);
        this.hotelResponsibleRepository.save(hotelResponsible);
        this.departmentRepository.save(departement);
        Reception zone = this.receptionRepository.findById(hotelResponsible.getZoneId()).get();
        zone.setResponsible(hotelResponsible);
        this.receptionRepository.save(zone);
        User user = new User(hotelResponsible.getUsername(),
                hotelResponsible.getEmail(),
                encoder.encode(hotelResponsible.getPassword()));

        // Add role of the client to user
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        // Save access instance and client profile
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateResponsible(HotelResponsible hotelResponsible) {
        this.hotelResponsibleRepository.save(hotelResponsible);
    }

    public void deleteResponsible(String username) {
        this.hotelResponsibleRepository.deleteByUsername(username);
    }

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

    @Autowired
    private RoomRepository roomRepository;

    public void addRestaurant(Restaurant restaurant) {
        restaurant.setResponsible(null);
        restaurant.setBeacons(new ArrayList<>());
        restaurant.setStaff(new ArrayList<>());
        restaurant.setBreakfast(new ArrayList<>());
        restaurant.setLunch(new ArrayList<>());
        restaurant.setDinner(new ArrayList<>());
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Zone> zones = hotel.getZones();
        zones.add(restaurant);
        hotel.setZones(zones);
        this.restaurantRepository.save(restaurant);
        this.hotelsRepository.save(hotel);
    }

    public void addSpa(Spa spa) {
        spa.setResponsible(null);
        spa.setBeacons(new ArrayList<>());
        spa.setStaff(new ArrayList<>());
        spa.setEvents(new ArrayList<>());
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Zone> zones = hotel.getZones();
        zones.add(spa);
        hotel.setZones(zones);
        this.spaRepository.save(spa);
        this.hotelsRepository.save(hotel);
    }

    public void addReception(Reception reception) {
        reception.setResponsible(null);
        reception.setBeacons(new ArrayList<>());
        reception.setStaff(new ArrayList<>());
        reception.setEvents(new ArrayList<>());
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Zone> zones = hotel.getZones();
        zones.add(reception);
        hotel.setZones(zones);
        this.receptionRepository.save(reception);
        this.hotelsRepository.save(hotel);
    }

    public void addPool(Pool pool) {
        pool.setResponsible(null);
        pool.setBeacons(new ArrayList<>());
        pool.setStaff(new ArrayList<>());
        pool.setEvents(new ArrayList<>());
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Zone> zones = hotel.getZones();
        zones.add(pool);
        hotel.setZones(zones);
        this.poolRepository.save(pool);
        this.hotelsRepository.save(hotel);
    }

    public void addGym(Gym gym) {
        gym.setResponsible(null);
        gym.setBeacons(new ArrayList<>());
        gym.setStaff(new ArrayList<>());
        gym.setEvents(new ArrayList<>());
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Zone> zones = hotel.getZones();
        zones.add(gym);
        hotel.setZones(zones);
        this.hotelsRepository.findByName("Test Hotel").getZones().add(gym);
        this.gymRepository.save(gym);
        this.hotelsRepository.save(hotel);
    }

    public void addBar(Bar bar) {
        bar.setResponsible(null);
        bar.setBeacons(new ArrayList<>());
        bar.setStaff(new ArrayList<>());
        bar.setEvents(new ArrayList<>());
        bar.setDrinks(new ArrayList<>());
        Hotel hotel = hotelsRepository.findByName("Test Hotel");
        List<Zone> zones = hotel.getZones();
        zones.add(bar);
        hotel.setZones(zones);
        this.barRepository.save(bar);
        this.hotelsRepository.save(hotel);
    }

    public void addRoom(String hotelName, Room room) {
        Hotel hotel = this.hotelsRepository.findByName(hotelName);
        room.setHotelId(hotel.getId());
        this.roomRepository.save(room);
        room = this.roomRepository.findByHotelIdAndNumber(room.getHotelId(),room.getNumber());
        List<String> rooms = hotel.getRooms();
        rooms.add(room.getId());
        hotel.setRooms(rooms);
        this.hotelsRepository.save(hotel);
    }

    @Autowired
    private BraceletRepository braceletRepository;

    public void addBracelet(Bracelet bracelet) {
        Hotel hotel = this.hotelsRepository.findById(bracelet.getHotelId()).get();
        this.braceletRepository.save(bracelet);
        List<Bracelet> bracelets = this.braceletRepository.findAll();
        List<String> ids = new ArrayList<>();
        for(Bracelet bracelet1 : bracelets)
            ids.add(bracelet1.getId());
        hotel.setBracelets(ids);
        this.hotelsRepository.save(hotel);
    }

    @Autowired
    private FoodRepository foodRepository;

    public void addFood(Food food, String hotelName) {
        Restaurant restaurant = this.restaurantRepository.findByHotelId(this.hotelsRepository.findByName(hotelName).getId());
        this.foodRepository.save(food);
        List<Food> foods = restaurant.getBreakfast();
        foods.add(food);
        restaurant.setBreakfast(foods);
        this.restaurantRepository.save(restaurant);
    }

    @Autowired
    private BeaconRepository beaconRepository;

    public void addBeacon(Beacon beacon) {
        Reception reception = this.receptionRepository.findByHotelId("5f60c0d9c2a7fb6c58b5db93");
        List<Beacon> beacons = reception.getBeacons();
        this.beaconRepository.save(beacon);
        beacons.add(beacon);
        reception.setBeacons(beacons);
        this.receptionRepository.save(reception);
    }
}
