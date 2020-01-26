package ris.projekat.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Bid;
import model.Category;
import model.Message;
import model.Rating;
import model.Serviceuser;
import ris.projekat.repository.BidRepository;
import ris.projekat.repository.CategoryRepository;
import ris.projekat.repository.MessageRepository;
import ris.projekat.repository.RatingRepository;
import ris.projekat.repository.ServiceUserRepository;

@Controller
@RequestMapping(value = "serviceUserController")
public class ServiceUserController {
	@Autowired
	ServiceUserRepository serviceUserRepo;
	@Autowired
	MessageRepository messageRepo;
	@Autowired
	RatingRepository ratingRepo;
	@Autowired
	BidRepository bidRepo;
	@Autowired
	CategoryRepository categoryRepo;
	
	@RequestMapping(value = "loginUser", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Serviceuser loggedInUser = serviceUserRepo.findByUsernameAndPassword(username, password);
		if(loggedInUser != null) {
			System.out.println(loggedInUser.getUsername() + " - " + loggedInUser.getPassword());
			request.getSession().setAttribute("loggedInUser", loggedInUser);
			return "pages/home";
		}
		else {
			System.out.println("ERROR");
			return "pages/login";
		}
		
	}
	
	@RequestMapping(value = "logoutUser", method = RequestMethod.GET)
	public String logoutUser(HttpServletRequest request) {
		request.getSession().removeAttribute("loggedInUser");
		return "pages/home";
	}
	
	@RequestMapping(value = "registerUser", method = RequestMethod.POST)
	public String registerUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		
		Serviceuser serviceUser = new Serviceuser();
		serviceUser.setUsername(username);
		serviceUser.setPassword(password);
		serviceUser.setEmail(email);
		serviceUser.setFirstName(firstName);
		serviceUser.setLastName(lastName);
		serviceUser.setPhoneNumber(phoneNumber);
		serviceUser.setCountry(country);
		serviceUser.setCity(city);
		serviceUser.setRole("USER");
		
		request.getSession().setAttribute("loggedInUser", serviceUser);
		
		Serviceuser savedUser = serviceUserRepo.save(serviceUser);
		System.out.println(savedUser);
		
		return "pages/home";
	}
	
	@RequestMapping(value = "showUserDetails", method = RequestMethod.GET)
	public String showUserDetails(HttpServletRequest request) {
		int idUser = Integer.parseInt(request.getParameter("idUser"));
		Serviceuser user = serviceUserRepo.findById(idUser).get();
		Serviceuser loggedInUser = (Serviceuser) request.getSession().getAttribute("loggedInUser");
		
		// NOTIFY LOGGED IN USER IF HE HAS TO RATE SOME OTHER USER
		int sent = 0;
		int received = 0;
		int numberToRate = 0;
		if(loggedInUser != null) {
			List<Rating> allRatings = ratingRepo.findAll();
			for (Rating rating : allRatings) {
				if(rating.getServiceuser1().getIdServiceUser() == loggedInUser.getIdServiceUser()) {
					sent++;
				}
				if(rating.getServiceuser2().getIdServiceUser() == loggedInUser.getIdServiceUser()) {
					received++;
				}
			}
			System.out.println(loggedInUser.getUsername() + " SENT = " + sent);
			System.out.println(loggedInUser.getUsername() + " RECEIVED = " + received);
			
			List<Serviceuser> usersToRate = new ArrayList<Serviceuser>();
			if(received > sent) {
				numberToRate = received - sent;
				for(int i = allRatings.size() - 1 ; i >= 0 ; i--) { 
					if(allRatings.get(i).getServiceuser2().getIdServiceUser() == loggedInUser.getIdServiceUser()) {
						usersToRate.add(allRatings.get(i).getServiceuser1());
						numberToRate--;
					
					}
					if(numberToRate == 0) {
						break;
					}
				}
			}
			
			request.getSession().setAttribute("usersToRate", usersToRate);
			
		}
		////////////////////////////////////////////////////////////////////////////////////
		
		List<Rating> positiveRatings = ratingRepo.findByServiceuser2EqualsAndRatingEquals(user, 1);
		List<Rating> neutralRatings = ratingRepo.findByServiceuser2EqualsAndRatingEquals(user, 0);
		List<Rating> negativeRatings = ratingRepo.findByServiceuser2EqualsAndRatingEquals(user, -1);
		
		request.getSession().setAttribute("positiveRatings", positiveRatings);
		request.getSession().setAttribute("neutralRatings", neutralRatings);
		request.getSession().setAttribute("negativeRatings", negativeRatings);
		
		request.getSession().setAttribute("positiveRatingsNumber", positiveRatings.size());
		request.getSession().setAttribute("neutralRatingsNumber", neutralRatings.size());
		request.getSession().setAttribute("negativeRatingsNumber", negativeRatings.size());
		
		request.getSession().setAttribute("user", user);
		
		return "pages/user";
	}
	
	@RequestMapping(value = "viewMessages", method = RequestMethod.GET)
	public String viewMessages(HttpServletRequest request) {
		int idSender = Integer.parseInt(request.getParameter("idSender"));
		int idReceiver = Integer.parseInt(request.getParameter("idReceiver"));
		
		request.getSession().setAttribute("idSender", idSender);
		request.getSession().setAttribute("idReceiver", idReceiver);
		
		Serviceuser sender = serviceUserRepo.findById(idSender).get();
		Serviceuser receiver = serviceUserRepo.findById(idReceiver).get();
		
		request.getSession().setAttribute("sender", sender);
		request.getSession().setAttribute("receiver", receiver);
		
		List<Message> messagesSent = messageRepo.findByServiceuser1AndServiceuser2OrderByDateAsc(sender, receiver);
		List<Message> messagesReceived = messageRepo.findByServiceuser1AndServiceuser2OrderByDateAsc(receiver, sender);
		
		messagesSent.addAll(messagesReceived);
		Collections.sort(messagesSent);
	
		request.getSession().setAttribute("messages", messagesSent);
		
		return "pages/messages";
	}
	
	@RequestMapping(value = "sendMessage", method = RequestMethod.POST)
	public String sendMessage(HttpServletRequest request) {
		int idReceiver = Integer.parseInt(request.getParameter("idReceiver"));
		String content = request.getParameter("newMessage");
		
		Serviceuser sender = (Serviceuser) request.getSession().getAttribute("loggedInUser");
		Serviceuser receiver = serviceUserRepo.findById(idReceiver).get();
		
		Message newMessage = new Message();
		newMessage.setContent(content);
		newMessage.setDate(new Date());
		newMessage.setServiceuser1(sender);
		newMessage.setServiceuser2(receiver);
		
		messageRepo.save(newMessage);

		return "pages/messages";
		
	}
	
	@RequestMapping(value = "rate", method = RequestMethod.POST)
	public String rate(HttpServletRequest request) {
		int idSender = Integer.parseInt(request.getParameter("idSender"));
		int idReceiver = Integer.parseInt(request.getParameter("idReceiver"));
		
		Serviceuser sender = serviceUserRepo.findById(idSender).get();
		Serviceuser receiver = serviceUserRepo.findById(idReceiver).get();
		
		int rating = Integer.parseInt(request.getParameter("rating"));
		
		Rating ratingObj = new Rating();
		ratingObj.setRating(rating);
		ratingObj.setServiceuser1(sender);
		ratingObj.setServiceuser2(receiver);
		
		ratingRepo.save(ratingObj);
		
		return "pages/user";
		
	}
	
	@RequestMapping(value = "statistics", method = RequestMethod.GET)
	public String statistics(HttpServletRequest request) throws Exception {
		Serviceuser loggedInUser = (Serviceuser) request.getSession().getAttribute("loggedInUser");
		
		String startingDateString;
		if(!request.getParameter("startingDate").equals("")) {
			startingDateString = request.getParameter("startingDate");
		}
		else {
			startingDateString = "2000-01-01";
		}
		
		String endDateString;
		if(!request.getParameter("endDate").equals("")) {
			endDateString = request.getParameter("endDate");
		}
		else {
			endDateString = "2050-01-01";
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startingDate = format.parse(startingDateString);
		Date endDate = format.parse(endDateString);
		
		List<Bid> allBids = bidRepo.searchWithOnlyDatesAndUser(startingDate, endDate, loggedInUser.getIdServiceUser());
		request.getSession().setAttribute("allBids", allBids);
		request.getSession().setAttribute("startingDate", startingDate);
		request.getSession().setAttribute("endDate", endDate);
		
		return "pages/statistics";
	}
	
	@RequestMapping(value = "welcome", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request) {
		List<Category> categories = categoryRepo.findAll();
		List<Bid> bids = bidRepo.searchAllNotFinished();
		
		request.getSession().setAttribute("categories", categories);
		request.getSession().setAttribute("bids", bids);
		
		return "pages/home";
	}
}
