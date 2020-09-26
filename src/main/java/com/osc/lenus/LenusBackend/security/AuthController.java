package com.osc.lenus.LenusBackend.security;

import com.osc.lenus.LenusBackend.model.central.Client;
import com.osc.lenus.LenusBackend.model.local.HotelResponsible;
import com.osc.lenus.LenusBackend.model.central.SuperUser;
import com.osc.lenus.LenusBackend.payload.utilities.ClientCard;
import com.osc.lenus.LenusBackend.repositories.central.ClientRepository;
import com.osc.lenus.LenusBackend.repositories.local.HotelResponsibleRepository;
import com.osc.lenus.LenusBackend.repositories.central.SuperUsersRepository;
import com.osc.lenus.LenusBackend.security.models.ERole;
import com.osc.lenus.LenusBackend.security.models.Role;
import com.osc.lenus.LenusBackend.security.models.User;
import com.osc.lenus.LenusBackend.security.payloads.requests.LoginRequest;
import com.osc.lenus.LenusBackend.security.payloads.requests.SignupRequest;
import com.osc.lenus.LenusBackend.security.payloads.responses.AdminJwtResponse;
import com.osc.lenus.LenusBackend.security.payloads.responses.ClientJwtResponse;
import com.osc.lenus.LenusBackend.security.payloads.responses.HotelRespJwtResponse;
import com.osc.lenus.LenusBackend.security.payloads.responses.MessageResponse;
import com.osc.lenus.LenusBackend.repositories.central.RoleRepository;
import com.osc.lenus.LenusBackend.repositories.central.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HotelResponsibleRepository hotelResponsibleRepository;

    @Autowired
    private SuperUsersRepository superUsersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;



    // Client Entry points

    @PostMapping("client/login")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Client client = this.clientRepository.findByEmail(loginRequest.getEmail());

        return ResponseEntity.ok(new ClientJwtResponse(jwt,
                client,
                roles));
    }


    @PostMapping("client/signup")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignupRequest signUpRequest) {
        if (clientRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (clientRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Client client = new Client(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Add role of the client to user
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        // Save access instance and client profile
        user.setRoles(roles);
        userRepository.save(user);
        clientRepository.save(client);

        return this.authenticateClient(new LoginRequest(signUpRequest.getEmail(),signUpRequest.getPassword()));

    }

    // hotelResponsible Entry point

    @PostMapping("hotel/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        HotelResponsible hotelResponsible = this.hotelResponsibleRepository.findByEmail(userDetails.getUsername());


        return ResponseEntity.ok(new HotelRespJwtResponse(jwt,
                hotelResponsible.getId(),
                hotelResponsible.getDepartmentId(),
                userDetails.getUsername(),
                hotelResponsible.getHotelName(),
                hotelResponsible.getFirstName() + " "+ hotelResponsible.getLastName(),
                hotelResponsible.getTitle(),
                hotelResponsible.getImgUrl()));
    }


    // Admin entry point

    @PostMapping("admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AdminJwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

}
