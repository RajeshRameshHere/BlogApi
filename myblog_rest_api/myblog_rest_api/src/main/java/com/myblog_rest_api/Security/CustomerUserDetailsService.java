package com.myblog_rest_api.Security;

import com.myblog_rest_api.Entity.Role;
import com.myblog_rest_api.Entity.User;
import com.myblog_rest_api.Repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;
public CustomerUserDetailsService(UserRepository userRepo){
    this.userRepo=userRepo;
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmailOrUsername(username, username).orElseThrow(
                () -> new UsernameNotFoundException("User with user_name or email not found:" + username)
        );

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority>  mapRolesToAuthorities(Set<Role> roles) {

        List<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return authorities;
    }
}
