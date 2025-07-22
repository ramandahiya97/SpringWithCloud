package com.rest.webservices.inventory_management_system.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.rest.webservices.inventory_management_system.model.User;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
	private static int count = 0;
	private static List<User> users = new ArrayList<>();
	static {
		users.add(new User(++count,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++count,"Eve",LocalDate.now().minusYears(25)));
		users.add(new User(++count,"Jim",LocalDate.now().minusYears(20)));
	}
	public List<User> findAll(){
		return users;
	}
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	public User create(User user) {
		user.setId(++count);
		users.add(user);
		return user;
	}
}
