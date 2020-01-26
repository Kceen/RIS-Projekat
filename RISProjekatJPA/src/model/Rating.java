package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rating database table.
 * 
 */
@Entity
@NamedQuery(name="Rating.findAll", query="SELECT r FROM Rating r")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRating;

	private int rating;

	//bi-directional many-to-one association to Serviceuser
	@ManyToOne
	@JoinColumn(name="idUserSender")
	private Serviceuser serviceuser1;

	//bi-directional many-to-one association to Serviceuser
	@ManyToOne
	@JoinColumn(name="idUserReceiver")
	private Serviceuser serviceuser2;

	public Rating() {
	}

	public int getIdRating() {
		return this.idRating;
	}

	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Serviceuser getServiceuser1() {
		return this.serviceuser1;
	}

	public void setServiceuser1(Serviceuser serviceuser1) {
		this.serviceuser1 = serviceuser1;
	}

	public Serviceuser getServiceuser2() {
		return this.serviceuser2;
	}

	public void setServiceuser2(Serviceuser serviceuser2) {
		this.serviceuser2 = serviceuser2;
	}

}