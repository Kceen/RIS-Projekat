package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the serviceuser database table.
 * 
 */
@Entity
@NamedQuery(name="Serviceuser.findAll", query="SELECT s FROM Serviceuser s")
public class Serviceuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idServiceUser;

	private String city;

	private String country;

	private String email;

	private String firstName;

	private String imageLink;

	private String lastName;

	private String password;

	private String phoneNumber;

	private String role;

	private String username;

	//bi-directional many-to-one association to Bid
	@OneToMany(mappedBy="serviceuser")
	private List<Bid> bids;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="serviceuser")
	private List<Item> items;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="serviceuser1")
	private List<Message> messages1;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="serviceuser2")
	private List<Message> messages2;

	//bi-directional many-to-one association to Rating
	@OneToMany(mappedBy="serviceuser1")
	private List<Rating> ratings1;

	//bi-directional many-to-one association to Rating
	@OneToMany(mappedBy="serviceuser2")
	private List<Rating> ratings2;

	//bi-directional many-to-one association to Bid
	@ManyToOne
	private Bid bid;

	public Serviceuser() {
	}

	public int getIdServiceUser() {
		return this.idServiceUser;
	}

	public void setIdServiceUser(int idServiceUser) {
		this.idServiceUser = idServiceUser;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getImageLink() {
		return this.imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Bid> getBids() {
		return this.bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public Bid addBid(Bid bid) {
		getBids().add(bid);
		bid.setServiceuser(this);

		return bid;
	}

	public Bid removeBid(Bid bid) {
		getBids().remove(bid);
		bid.setServiceuser(null);

		return bid;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setServiceuser(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setServiceuser(null);

		return item;
	}

	public List<Message> getMessages1() {
		return this.messages1;
	}

	public void setMessages1(List<Message> messages1) {
		this.messages1 = messages1;
	}

	public Message addMessages1(Message messages1) {
		getMessages1().add(messages1);
		messages1.setServiceuser1(this);

		return messages1;
	}

	public Message removeMessages1(Message messages1) {
		getMessages1().remove(messages1);
		messages1.setServiceuser1(null);

		return messages1;
	}

	public List<Message> getMessages2() {
		return this.messages2;
	}

	public void setMessages2(List<Message> messages2) {
		this.messages2 = messages2;
	}

	public Message addMessages2(Message messages2) {
		getMessages2().add(messages2);
		messages2.setServiceuser2(this);

		return messages2;
	}

	public Message removeMessages2(Message messages2) {
		getMessages2().remove(messages2);
		messages2.setServiceuser2(null);

		return messages2;
	}

	public List<Rating> getRatings1() {
		return this.ratings1;
	}

	public void setRatings1(List<Rating> ratings1) {
		this.ratings1 = ratings1;
	}

	public Rating addRatings1(Rating ratings1) {
		getRatings1().add(ratings1);
		ratings1.setServiceuser1(this);

		return ratings1;
	}

	public Rating removeRatings1(Rating ratings1) {
		getRatings1().remove(ratings1);
		ratings1.setServiceuser1(null);

		return ratings1;
	}

	public List<Rating> getRatings2() {
		return this.ratings2;
	}

	public void setRatings2(List<Rating> ratings2) {
		this.ratings2 = ratings2;
	}

	public Rating addRatings2(Rating ratings2) {
		getRatings2().add(ratings2);
		ratings2.setServiceuser2(this);

		return ratings2;
	}

	public Rating removeRatings2(Rating ratings2) {
		getRatings2().remove(ratings2);
		ratings2.setServiceuser2(null);

		return ratings2;
	}

	public Bid getBid() {
		return this.bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}
	
	@Override
	public String toString() {
		return username;
	}

}