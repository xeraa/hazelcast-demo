package at.ac.tuwien.ec.hazelcast_demo.test;


import at.ac.tuwien.ec.hazelcast_demo.model.User;
import at.ac.tuwien.ec.hazelcast_demo.service.Service;
import at.ac.tuwien.ec.hazelcast_demo.service.UserService;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceUnitTest {

	private static HazelcastInstance hazelcastInstance1 = null;
	private static HazelcastInstance hazelcastInstance2 = null;
	private static HazelcastInstance hazelcastInstance3 = null;
	private static Service<User> userService = null;

	@BeforeClass
	public static void setUpForAllTests() throws Exception {
		// Create 3 Hazelcast instances for these tests
		hazelcastInstance1 = Hazelcast.newHazelcastInstance();
		hazelcastInstance2 = Hazelcast.newHazelcastInstance();
		hazelcastInstance3 = Hazelcast.newHazelcastInstance();

		userService = UserService.getInstance();
	}

	@After
	public void clearData(){
		userService.removeAll();
	}

	@Test
	public void userServiceAddGetTest(){
		User user = new User("Philipp", "Krenn", "spam@test.com");
		assertNull(userService.get(user));
		userService.add(user);
		assertNotNull(userService.get(user));
	}

	@Test
	public void userServiceGetByMailTest(){
		User user = new User("Philipp", "Krenn", "spam@test.com");
		assertNull(userService.get(user));
		userService.add(user);
		assertNotNull(userService.get("spam@test.com"));
	}

	@Test
	public void userServiceGetAllTest(){
		assertEquals(0, userService.getAll().size());
		User user = new User("Philipp", "Krenn", "spam@test.com");
		userService.add(user);
		assertNotNull(userService.getAll());
		assertEquals(1, userService.getAll().size());
	}

	@Test
	public void userServiceRemoveTest(){
		User user = new User("Philipp", "Krenn", "spam@test.com");
		assertNull(userService.get(user));
		userService.add(user);
		assertNotNull(userService.get(user));
		userService.remove(user);
		assertNull(userService.get(user));
		assertEquals(0, userService.getAll().size());
	}

	@Test
	public void userServiceRemoveByMailTest(){
		User user = new User("Philipp", "Krenn", "spam@test.com");
		assertNull(userService.get(user));
		userService.add(user);
		assertNotNull(userService.get(user));
		userService.remove("spam@test.com");
		assertNull(userService.get(user));
		assertEquals(0, userService.getAll().size());
	}

	@Test
	public void userServiceRemoveAllTest(){
		User user = new User("Philipp", "Krenn", "spam@test.com");
		assertNull(userService.get(user));
		userService.add(user);
		assertNotNull(userService.get(user));
		userService.removeAll();
		assertNull(userService.get(user));
		assertEquals(0, userService.getAll().size());
	}

	@AfterClass
	public static void destroyAfterAllTests() throws Exception{
		Hazelcast.shutdownAll();
	}
}