package com.canterosolutions.restapiexample.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {
	
	private static List<User> users = new ArrayList<>();
	private static int usersCount = 3;
	
	static {
		users.add(new User(1, "Goku", LocalDate.now().minusYears(40)));
		users.add(new User(2, "Vegeta", LocalDate.now().minusYears(42)));
		users.add(new User(3, "Gohan", LocalDate.now().minusYears(20)));
		
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User findUser(int user_id) {
		// This try is to check if it is not null
		try {
			return users.stream().filter(user -> user.getId().equals(user_id)).findFirst().get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User findUser(String user_id) {
		User user = null;
		Integer id;
		try {
			id = Integer.valueOf(user_id);
			for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
				user = iterator.next();
				if(user.getId() == id)
					return user;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			
		} 
	
		return user;
		
	}

	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setId(++usersCount);
		users.add(user);
		
		return user;
	}

	public void deleteUser(Integer id) {
		User findUser = this.findUser(id);
		if(findUser != null)
			users.remove(findUser);
	}
}
