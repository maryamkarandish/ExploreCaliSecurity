package com.example.ec.security;

import com.example.ec.domain.Role;
import com.example.ec.domain.User;
import com.example.ec.repo.RoleRepository;
import com.example.ec.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Created by m.karandish on 5/29/2019.
 */
@Service
public class ExploreCaliUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
//        User user = userRepository.findByUsername(s).orElseThrow(() ->
//        new UsernameNotFoundException(String.format("user with name %s does not exist",s)));

        User user = userRepository.findByUsername(s);

        // this creates a user detail object
        // populates it with username, Password and roles
        //org.springframework.security.core.userdetails.user.withUsername() builder
        return withUsername(user.getUsername())
        .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }


    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


//    public void saveUser(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        Optional<Role> userRole = roleRepository.findByRoleName("ADMIN");
//        user.setRoles(new HashSet<>((Collection<? extends Role>) Arrays.asList(userRole)));
//        userRepository.save(user);
//    }



//    public UserDetails loadUserByJwtToken(String s) throws UsernameNotFoundException{
//        User user = userRepository.findByUsername(s).orElseThrow(() ->
//                new UsernameNotFoundException(String.format("user with name %s does not exist",s)));
//
//        // this creates a user detail object
//        // populates it with username, Password and roles
//        //org.springframework.security.core.userdetails.user.withUsername() builder
//        return withUsername(user.getUsername())
//                .password(user.getPassword())
//                .authorities(user.getRoles())
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .disabled(false)
//                .build();
//    }


}
