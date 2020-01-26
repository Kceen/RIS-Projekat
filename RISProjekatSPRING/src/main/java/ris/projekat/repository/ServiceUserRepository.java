package ris.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Serviceuser;

public interface ServiceUserRepository extends JpaRepository<Serviceuser, Integer> {
	public Serviceuser findByUsernameAndPassword(String username, String password);
	public Serviceuser findByUsername(String username);
}
