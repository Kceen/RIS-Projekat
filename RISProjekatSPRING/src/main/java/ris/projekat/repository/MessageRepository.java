package ris.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Message;
import model.Serviceuser;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	public List<Message> findByServiceuser1AndServiceuser2OrderByDateAsc(Serviceuser sender, Serviceuser receiver);
}
