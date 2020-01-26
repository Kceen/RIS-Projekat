package ris.projekat.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Bid;
import model.Category;
import model.Image;
import model.Item;
import model.Serviceuser;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ris.projekat.repository.BidRepository;
import ris.projekat.repository.CategoryRepository;
import ris.projekat.repository.ImageRepository;
import ris.projekat.repository.ItemRepository;

@Controller
@RequestMapping(value = "auctionController")
public class AuctionController {
	@Autowired
	CategoryRepository categoryRepo;
	@Autowired
	ItemRepository itemRepo;
	@Autowired
	BidRepository bidRepo;
	@Autowired
	ImageRepository imageRepo;

	@RequestMapping(value = "addNewAuction", method = RequestMethod.POST)
	public String addNewAuction(HttpServletRequest request) throws Exception {
		Serviceuser loggedInUser = (Serviceuser) request.getSession().getAttribute("loggedInUser");

		String itemName = request.getParameter("itemName");
		String itemDescription = request.getParameter("itemDescription");
		int itemCategoryId = Integer.parseInt(request.getParameter("itemCategory"));
		Category itemCategory = categoryRepo.findById(itemCategoryId).get();
		String itemImage1 = request.getParameter("itemImage1");
		String itemImage2 = request.getParameter("itemImage2");
		String itemImage3 = request.getParameter("itemImage3");
		String itemImage4 = request.getParameter("itemImage4");

		List<String> itemImagesLinks = new ArrayList<String>();
		itemImagesLinks.add(itemImage1);
		itemImagesLinks.add(itemImage2);
		itemImagesLinks.add(itemImage3);
		itemImagesLinks.add(itemImage4);

		Item item = new Item();
		item.setName(itemName);
		item.setDescription(itemDescription);
		item.setServiceuser(loggedInUser);
		item.setCategory(itemCategory);

		itemRepo.save(item);

		List<Image> images = new ArrayList<Image>();
		for (String imageLink : itemImagesLinks) {
			if (!imageLink.equals("")) {
				Image image = new Image();
				image.setImageLink(imageLink);
				image.setItem(item);
				imageRepo.save(image);
				images.add(image);
			}
		}

		item.setImages(images);

		int bidStartingPrice = Integer.parseInt(request.getParameter("bidStartingPrice"));
		// String bidStartingDateString = request.getParameter("bidStartingDate");
		// DateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:MM");
		// Date bidStartingDate = format.parse(bidStartingDateString);

		Bid bid = new Bid();
		bid.setStartingPrice(bidStartingPrice);
		bid.setCurrentPrice(bidStartingPrice);
		bid.setStartingDate(new Date());
		bid.setItem(item);

		long numberOfBids = bidRepo.count();
		request.getSession().setAttribute("numberOfBids", numberOfBids);

		bidRepo.save(bid);

		return "pages/home";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(HttpServletRequest request) throws Exception {
		boolean categoryAll = false;
		
		int idCategory = 0;
		if(request.getParameter("category").equals("all")) {
			categoryAll = true;
		}
		else {
			 idCategory = Integer.parseInt(request.getParameter("category"));
		}
		
		int startingPriceMoreThan;
		if(!request.getParameter("startingPriceMoreThan").equals("")) {
			startingPriceMoreThan = Integer.parseInt(request.getParameter("startingPriceMoreThan"));
		}
		else {
			startingPriceMoreThan = 0;
		}
		
		int startingPriceLessThan;
		if(!request.getParameter("startingPriceLessThan").equals("")) {
			startingPriceLessThan = Integer.parseInt(request.getParameter("startingPriceLessThan"));
		}
		else {
			startingPriceLessThan = 1000000000;
		}
		
		int currentPriceMoreThan;
		if(!request.getParameter("currentPriceMoreThan").equals("")) {
			currentPriceMoreThan = Integer.parseInt(request.getParameter("currentPriceMoreThan"));
		}
		else {
			currentPriceMoreThan = 0;
		}
		
		int currentPriceLessThan;
		if(!request.getParameter("currentPriceLessThan").equals("")) {
			currentPriceLessThan = Integer.parseInt(request.getParameter("currentPriceLessThan"));
		}
		else {
			currentPriceLessThan = 1000000000;
		}
		
		String startingDateBeforeString;
		if(!request.getParameter("startingDateBefore").equals("")) {
			startingDateBeforeString = request.getParameter("startingDateBefore");
		}
		else {
			startingDateBeforeString = "2050-01-01";
		}
		
		String startingDateAfterString;
		if(!request.getParameter("startingDateAfter").equals("")) {
			startingDateAfterString = request.getParameter("startingDateAfter");
		}
		else {
			startingDateAfterString = "2000-01-01";
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startingDateBefore = format.parse(startingDateBeforeString);
		Date startingDateAfter = format.parse(startingDateAfterString);
		
		List<Bid> bids = new ArrayList<Bid>();
		if(categoryAll) {
			bids = bidRepo.search(startingPriceMoreThan, startingPriceLessThan, currentPriceMoreThan, currentPriceLessThan, startingDateAfter, startingDateBefore);
		}
		else {
			bids = bidRepo.searchWithCategory(idCategory, startingPriceMoreThan, startingPriceLessThan, currentPriceMoreThan, currentPriceLessThan, startingDateAfter, startingDateBefore);
		}
		request.getSession().setAttribute("bids", bids);

		long numberOfBids = bids.size();
		request.getSession().setAttribute("numberOfBids", numberOfBids);

		return "pages/home";
	}

	@RequestMapping(value = "findBid", method = RequestMethod.GET)
	public String findBid(HttpServletRequest request) {
		//Serviceuser loggedInUser = (Serviceuser) request.getSession().getAttribute("loggedInUser");
		int idBid = Integer.parseInt(request.getParameter("idBid"));
		Bid bid = bidRepo.findById(idBid).get();

		Item item = bid.getItem();

		List<Image> itemImages = imageRepo.findByItem_idItem(item.getIdItem());
		request.getSession().setAttribute("itemImages", itemImages);
		request.getSession().setAttribute("bid", bid);
		
		//if(loggedInUser != null && loggedInUser.getIdServiceUser() == bid.getItem().getServiceuser().getIdServiceUser()) {
		//	request.getSession().setAttribute("loggedInUsersBid", 1);
		//}
		
		return "pages/bid";

	}

	@RequestMapping(value = "raiseTheBid", method = RequestMethod.POST)
	public String raiseTheBid(HttpServletRequest request) {
		Serviceuser loggedInUser = (Serviceuser) request.getSession().getAttribute("loggedInUser");
		
		int idBid = Integer.parseInt(request.getParameter("idBid"));
		Bid bid = bidRepo.findById(idBid).get();
		
		int newCurrentPrice = Integer.parseInt(request.getParameter("newCurrentPrice"));
		bid.setCurrentPrice(newCurrentPrice);
		bid.setServiceuser(loggedInUser);
		
		/*boolean alreadyBid = false;
		if(bid.getServiceusers().isEmpty()) {
			bid.getServiceusers().add(loggedInUser);
			System.out.println("U IFU SAAAAAAAAAAAAAAAAAAAAAAMMMM");
		}
		else {
			for (Serviceuser user : bid.getServiceusers()) {
				if(user.getIdServiceUser() == loggedInUser.getIdServiceUser()) {
					alreadyBid = true;
					break;
				}
			}
			if(!alreadyBid) {
				bid.getServiceusers().add(loggedInUser);
			}
		}

		System.out.println("EEEEEEEEEEEEEEEEEEE" + bid.getServiceusers());*/
		
		bidRepo.save(bid);
		
		return "pages/bid";

	}

	@RequestMapping(value = "endBid", method = RequestMethod.GET)
	public String endBid(HttpServletRequest request) {
		int idBid = Integer.parseInt(request.getParameter("idBid"));
		Bid bidToEnd = bidRepo.findById(idBid).get();
		
		bidToEnd.setEndDate(new Date());
		request.getSession().setAttribute("endedBid", bidToEnd);
		bidRepo.save(bidToEnd);
		
		return "pages/endedBid";
	}
	
	@RequestMapping(value = "generateReport", method = RequestMethod.GET)
	public void generateReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Bid> bids = (List<Bid>) request.getSession().getAttribute("bids"); 
		
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bids);
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/Aukcinjo.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), dataSource);
		inputStream.close();

		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=AukcinjoReport.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	}
}
