package ris.projekat.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Bid;

public interface BidRepository extends JpaRepository<Bid, Integer> {
	
	@Query("SELECT b FROM Bid b WHERE b.item.category.idCategory = :idCategory AND b.startingPrice > :startingPriceMoreThan AND b.startingPrice < :startingPriceLessThan AND b.currentPrice > :currentPriceMoreThan AND b.currentPrice < :currentPriceLessThan AND b.startingDate > :startingDateAfter AND b.startingDate < :startingDateBefore AND b.endDate = null")
	public List<Bid> searchWithCategory(@Param("idCategory") int idCategory, @Param("startingPriceMoreThan") int startingPriceMoreThan, @Param("startingPriceLessThan") int startingPriceLessThan, @Param("currentPriceMoreThan") int currentPriceMoreThan, @Param("currentPriceLessThan") int currentPriceLessThan, @Param("startingDateAfter") Date startingDateAfter, @Param("startingDateBefore") Date startingDateBefore);
	
	@Query("SELECT b FROM Bid b WHERE b.startingPrice > :startingPriceMoreThan AND b.startingPrice < :startingPriceLessThan AND b.currentPrice > :currentPriceMoreThan AND b.currentPrice < :currentPriceLessThan AND b.startingDate > :startingDateAfter AND b.startingDate < :startingDateBefore AND b.endDate = null")
	public List<Bid> search(@Param("startingPriceMoreThan") int startingPriceMoreThan, @Param("startingPriceLessThan") int startingPriceLessThan, @Param("currentPriceMoreThan") int currentPriceMoreThan, @Param("currentPriceLessThan") int currentPriceLessThan, @Param("startingDateAfter") Date startingDateAfter, @Param("startingDateBefore") Date startingDateBefore);
	
	@Query("SELECT b FROM Bid b WHERE b.startingDate >= :startingDate AND b.startingDate <= :endDate AND b.item.serviceuser.idServiceUser = :idLoggedInUser")
	public List<Bid> searchWithOnlyDatesAndUser(@Param("startingDate") Date startingDate, @Param("endDate") Date endDate, @Param("idLoggedInUser") int idLoggedInUser);
	
	@Query("SELECT b FROM Bid b WHERE b.endDate = null")
	public List<Bid> searchAllNotFinished();
	
}
