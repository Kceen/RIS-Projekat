package ris.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Rating;
import model.Serviceuser;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
	public List<Rating> findByServiceuser2EqualsAndRatingEquals(Serviceuser receiver, int rating);
}
