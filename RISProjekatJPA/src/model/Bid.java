package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the bid database table.
 * 
 */
@Entity
@NamedQuery(name="Bid.findAll", query="SELECT b FROM Bid b")
public class Bid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBid;

	private int currentPrice;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startingDate;

	private int startingPrice;

	//bi-directional many-to-one association to Item
	@ManyToOne
	private Item item;

	//bi-directional many-to-one association to Serviceuser
	@ManyToOne
	@JoinColumn(name="idUserMAXBidder")
	private Serviceuser serviceuser;

	//bi-directional many-to-one association to Serviceuser
	@OneToMany(mappedBy="bid")
	private List<Serviceuser> serviceusers;

	public Bid() {
	}

	public int getIdBid() {
		return this.idBid;
	}

	public void setIdBid(int idBid) {
		this.idBid = idBid;
	}

	public int getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartingDate() {
		return this.startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public int getStartingPrice() {
		return this.startingPrice;
	}

	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Serviceuser getServiceuser() {
		return this.serviceuser;
	}

	public void setServiceuser(Serviceuser serviceuser) {
		this.serviceuser = serviceuser;
	}

	public List<Serviceuser> getServiceusers() {
		return this.serviceusers;
	}

	public void setServiceusers(List<Serviceuser> serviceusers) {
		this.serviceusers = serviceusers;
	}

	public Serviceuser addServiceuser(Serviceuser serviceuser) {
		getServiceusers().add(serviceuser);
		serviceuser.setBid(this);

		return serviceuser;
	}

	public Serviceuser removeServiceuser(Serviceuser serviceuser) {
		getServiceusers().remove(serviceuser);
		serviceuser.setBid(null);

		return serviceuser;
	}

}