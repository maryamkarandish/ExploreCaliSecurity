package com.example.ec.web;

import com.example.ec.domain.User;
import com.example.ec.repo.UserRepository;
import com.example.ec.security.ExploreCaliUserDetailsService;
import com.example.ec.security.JwtProvider;
import com.example.ec.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private ExploreCaliUserDetailsService myUserService;

//    @PostMapping("/signin")
//    public Authentication login(@RequestBody @Valid LoginDto loginDto) {
//       return userService.signin(loginDto.getUsername(), loginDto.getPassword()) ;
//    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody @Valid LoginDto loginDto) {
        String username = loginDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));
        String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).getRoles());
        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User signup(@RequestBody @Valid LoginDto loginDto){
        return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),
                loginDto.getLastName()).orElseThrow(() -> new RuntimeException("User already exists"));
    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String return400(RuntimeException ex) {
        LOGGER.error("Unable to complete transaction", ex);
        return ex.getMessage();
    }
}