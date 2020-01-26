/*package ris.projekat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import model.Serviceuser;
import ris.projekat.repository.ServiceUserRepository;

@Service
public class UserDetailProvider implements UserDetailsService {
	@Autowired
	ServiceUserRepository serviceUserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Serviceuser user = serviceUserRepo.findByUsername(username);
		UserDetails userDetails = new CustomUserDetail(user);
		return userDetails;
	}

}*/
