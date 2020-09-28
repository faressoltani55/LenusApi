package com.osc.lenus.LenusBackend.services;

import com.osc.lenus.LenusBackend.config.others.EmailService;
import com.osc.lenus.LenusBackend.model.central.*;
import com.osc.lenus.LenusBackend.model.local.*;
import com.osc.lenus.LenusBackend.payload.requests.Group;
import com.osc.lenus.LenusBackend.payload.responses.Contacted;
import com.osc.lenus.LenusBackend.payload.utilities.ClientCard;
import com.osc.lenus.LenusBackend.payload.utilities.ContactDot;
import com.osc.lenus.LenusBackend.payload.utilities.Occupation;
import com.osc.lenus.LenusBackend.payload.utilities.TemperatureCard;
import com.osc.lenus.LenusBackend.repositories.central.*;
import com.osc.lenus.LenusBackend.repositories.local.*;
import com.osc.lenus.LenusBackend.security.models.ERole;
import com.osc.lenus.LenusBackend.security.models.Role;
import com.osc.lenus.LenusBackend.security.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HotelResponsibleServices {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelResponsibleRepository hotelResponsibleRepository;

    @Autowired
    private HotelsRepository hotelsRepository;

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ContactRepository contactRepository;

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


    // Home page :

    public List<Occupation> getOccupation(String hotelName) {
        List<Zone> hotelZones = new ArrayList<>();
        hotelZones.addAll(this.receptionRepository.findAll());
        hotelZones.addAll(this.barRepository.findAll());
        hotelZones.addAll(this.gymRepository.findAll());
        hotelZones.addAll(this.restaurantRepository.findAll());
        hotelZones.addAll(this.poolRepository.findAll());
        hotelZones.addAll(this.spaRepository.findAll());
        List<Occupation> occupations = new ArrayList<>();
        Date oneMinBack = new Date(System.currentTimeMillis() - 10 * 1000);
        for(int i=0; i<hotelZones.size(); i++) {
            Zone zone = hotelZones.get(i);
            zone.setOccupation(0);
            List<Beacon> beacons = zone.getBeacons();
            List<Access> accesses = new ArrayList<>();
            for(Beacon beacon : beacons) {
                accesses.addAll(this.accessRepository.findByBeaconId(beacon.getId(),oneMinBack));
            }
            for(Access access: accesses)
                System.out.println(access.getDate());
            zone.setOccupation(accesses.size());
            occupations.add(new Occupation(zone.getName(),(double) zone.getOccupation() *100 / zone.getSize()));
        }
        return occupations;
    }

    public List<Request> getRequests(String deptId) {
        String hotelId = this.hotelsRepository.findByName(this.hotelResponsibleRepository.findByDepartmentId(deptId).getHotelName()).getId();
        return this.requestsRepository.findByHotelId(hotelId);
    }



    public List<ContactDot> getDots(String hotelName) {
        String hotelId = this.hotelsRepository.findByName(hotelName).getId();
        List<Reservation> reservations = this.reservationRepository.findByHotelId(hotelId);
        List<ContactDot> contactDots = new ArrayList<>();
        for(Reservation reservation : reservations)
            contactDots.add(new ContactDot(reservation.getFirstname() + ' '+ reservation.getLastname(), reservation.getReservationCode()));
        return contactDots;
    }

    public List<Contact> getContactLines(String hotelName) {
        return this.contactRepository.findAll();
    }


    // Clients Page :

    public List<ClientCard> getClients(String hotelName) {
        String hotelId = this.hotelsRepository.findByName(hotelName).getId();
        List<Reservation> reservations = this.reservationRepository.findByHotelId(hotelId);
        List<Client> clients = new ArrayList<>();
        for(Reservation reservation : reservations)
            clients.add(this.clientRepository.findById(reservation.getClientId()).get());
        List<ClientCard> clientCards = new ArrayList<>();
        for (int i=0; i<reservations.size(); i++) {
            clientCards.add(new ClientCard(reservations.get(i), clients.get(i)));
        }
        return clientCards;
    }

            // reservation code for prototype is only for 1 hotel, and for the dates format in the front
    public void createReservation(String hotelName,ClientCard clientCard) {
        if(!this.clientRepository.existsByEmail(clientCard.getEmail()) && clientCard.isAdult()) {
            String hotelId = this.hotelsRepository.findByName(hotelName).getId();
            Client client = new Client(clientCard);
            client.setPassword(generateRandomPassword());
            Address address = new Address();
            address.setCountry(clientCard.getCountry());
            client.setAddress(address);
            this.addressRepository.save(address);
            this.clientRepository.save(client);
            String resCode = generateReservationCode(clientCard,hotelName);
            client = this.clientRepository.findByEmail(client.getEmail());
            Reservation reservation = new Reservation();
            reservation.setHotelId(hotelId);
            reservation.setClientId(client.getId());
            reservation.setFirstname(client.getFirstName());
            reservation.setLastname(client.getLastName());
            reservation.setArrivalDate(clientCard.getArrivalDate());
            reservation.setExitDate(clientCard.getExitDate());
            reservation.setReservationCode(resCode);
            reservation.setAdult(clientCard.isAdult());
            this.reservationRepository.save(reservation);
            User user = new User("",client.getEmail(),encoder.encode(client.getPassword()));
            Set<Role> roles = new HashSet<>();
            Role userRole = this.roleRepository.findByName(ERole.ROLE_USER).get();
            roles.add(userRole);
            user.setRoles(roles);
            this.userRepository.save(user);
        }
        else {
            Client client = new Client();
            Reservation reservation = new Reservation();
            if (this.clientRepository.existsByEmail(clientCard.getEmail())) {
                client = this.clientRepository.findByEmail(clientCard.getEmail());
                reservation.setClientId(client.getId());
            }
            String hotelId = this.hotelsRepository.findByName(hotelName).getId();
            reservation.setHotelId(hotelId);
            String resCode = generateReservationCode(clientCard,hotelName);
            reservation.setFirstname(clientCard.getFirstName());
            reservation.setLastname(clientCard.getLastName());
            reservation.setArrivalDate(clientCard.getArrivalDate());
            reservation.setExitDate(clientCard.getExitDate());
            reservation.setReservationCode(resCode);
            reservation.setAdult(clientCard.isAdult());
            this.reservationRepository.save(reservation);
        }
    }

    public void updateReservation(ClientCard clientCard) {
        Client client = this.clientRepository.findByEmail(clientCard.getEmail());
        Reservation reservation = this.reservationRepository.findByClientIdAndArrivalDateAndExitDate(client.getId(),clientCard.getArrivalDate(),clientCard.getExitDate());
        client.setFirstName(clientCard.getFirstName());
        client.setLastName(clientCard.getLastName());
        client.setIdCard(clientCard.getCin());
        client.setPassport(clientCard.getPassport());
        client.setBirthDate(clientCard.getBirthDate());
        reservation.setFirstname(client.getFirstName());
        reservation.setLastname(client.getLastName());
        reservation.setArrivalDate(clientCard.getArrivalDate());
        reservation.setExitDate(clientCard.getExitDate());
        reservation.setAdult(clientCard.isAdult());
        this.clientRepository.save(client);
        this.reservationRepository.save(reservation);
    }


    // Reservation Page : (Prototype : 1 res to 1 client)

    public List<ContactDot> getPeople() {
        List<Reservation> reservations = this.reservationRepository.findByGroupCode("");
        List<ContactDot> contactDots = new ArrayList<>();
        for(Reservation reservation : reservations)
            contactDots.add(new ContactDot(reservation.getFirstname() + " "+ reservation.getLastname(), reservation.getReservationCode()));
        return contactDots;
    }

    //success !
    public void setGroup(Group group) throws IOException, MessagingException {
        List<Reservation> reservations = new ArrayList<>();
        for(String code : group.getReservationCodes())
            reservations.add(this.reservationRepository.findByReservationCode(code));
        for(Reservation r: reservations) {
            r.setGroupCode(generateGroupCode(group));
            this.reservationRepository.save(r);
            this.emailService.sendEmail(r.getReservationCode(), this.hotelsRepository.findById(r.getHotelId()).get().getName());
        }
    }

    public List<ContactDot> getRoommates() {
        List<Reservation> reservations = this.reservationRepository.findByRoomNumber(0);
        List<ContactDot> contactDots = new ArrayList<>();
        for(Reservation reservation : reservations)
            contactDots.add(new ContactDot(reservation.getFirstname() + " "+ reservation.getLastname(), reservation.getReservationCode()));
        return contactDots;
    }

    public void setRoom(Group group, int roomNumber) {
        List<Reservation> reservations = new ArrayList<>();
        // Add clients to room :
        for(String resCode : group.getReservationCodes())
            reservations.add(this.reservationRepository.findByReservationCode(resCode));
        Room room = this.roomRepository.findByHotelIdAndNumber(this.reservationRepository.findByReservationCode(group.getReservationCodes().get(0)).getHotelId(),roomNumber);
        List<String> guests = new ArrayList<>();
        for(Reservation reservation : reservations)
            guests.add(reservation.getFirstname() + " " + reservation.getLastname());
        room.setClientNames(guests);
        room.setOccupied(true);
        this.roomRepository.save(room);

        // Add room to clients :
        for(String code : group.getReservationCodes())
            reservations.add(this.reservationRepository.findByReservationCode(code));
        for(Reservation r: reservations) {
            r.setRoomNumber(roomNumber);
            this.reservationRepository.save(r);
        }
    }


    public List<Room> getRooms(String hotelName) {
        return this.roomRepository.findByHotelId(this.hotelsRepository.findByName(hotelName).getId());
    }


    // Requests Page :

    public void changeRequestStatus(Request request) {
        this.requestsRepository.save(request);
    }


    // Feedbacks page :

    public List<Feedback> getFeedbacks(String hotelName) {
        String hotelId = this.hotelsRepository.findByName(hotelName).getId();
        return this.feedbackRepository.findByHotelId(hotelId);
    }

    // Health page :

    public List<Contacted> getContactCircle(String reservationCode) {
        List<Contact> contacts1 = this.contactRepository.findByIdPerson1(reservationCode);
        List<Contact> contacts2 = this.contactRepository.findByIdPerson2(reservationCode);
        List<Contacted> clients = new ArrayList<>();
        Reservation reservation;
        for(Contact contact : contacts1) {
            reservation = this.reservationRepository.findByReservationCode(contact.getIdPerson2());
            clients.add(new Contacted(reservation.getFirstname() + reservation.getLastname(),reservation.getRoomNumber(),reservation.getMobile()));
        }
        for(Contact contact : contacts2){
            reservation = this.reservationRepository.findByReservationCode(contact.getIdPerson1());
            clients.add(new Contacted(reservation.getFirstname() + reservation.getLastname(),reservation.getRoomNumber(),reservation.getMobile()));
        }
        return clients;
    }

    public List<TemperatureCard> getTemperatureAlerts() {
        // Extract current hotel reservations
        List<Reservation> reservations = this.reservationRepository.findReservationsByDate(
                (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).format(new Date())
        );
        // Extract measurements
        List<TemperatureCard> measurements = new ArrayList<>();
        for(Reservation reservation: reservations)
            for(Temperature temperature : reservation.getTemperatureMeasurements())
                measurements.add(new TemperatureCard(temperature.getId(),temperature.getTemperature(),reservation.getReservationCode(),temperature.getDate(),reservation.getRoomNumber(),reservation.getFirstname() + " " + reservation.getLastname()));
        // Sort by added date and return
        Collections.sort(measurements, Collections.reverseOrder());
        return measurements;
    }




    // Utilities :
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;

    private String generateReservationCode(ClientCard client, String hotelName) {
        return String.valueOf(client.getFirstName().charAt(0)) + String.valueOf(client.getLastName().charAt(0)) + this.reservationRepository.count();
    }

    private String generateGroupCode(Group group) {
        String grpCode = "";
        for (String resCode : group.getReservationCodes())
            grpCode.concat(resCode.substring(0,2));
        return grpCode + this.reservationRepository.count();
    }

    private String generateRandomPassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }


}
