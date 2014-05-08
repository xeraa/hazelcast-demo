package at.ac.tuwien.ec.hazelcast_demo.service;

import at.ac.tuwien.ec.hazelcast_demo.model.User;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import java.util.Collection;
import java.util.Map;

public class UserService implements Service<User> {

	private static UserService instance = null;
	private HazelcastInstance hazelcastClient;

	private UserService(){
		ClientConfig clientConfig = new ClientConfig();
		hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
	}

	public static UserService getInstance(){
		if(instance == null){
			synchronized(UserService.class){
				if(instance == null){
					instance = new UserService();
				}
			}
		}

		return instance;
	}

	@Override
	public void add(User user){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		usersMap.put(user.getEmail(), user);
	}

	@Override
	public User get(User user){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		return usersMap.get(user.getEmail());
	}

	@Override
	public User get(String email){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		return usersMap.get(email);
	}

	@Override
	public Collection<User> getAll(){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		return usersMap.values();
	}

	@Override
	public void remove(User user){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		usersMap.remove(user.getEmail());
	}

	@Override
	public void remove(String email){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		usersMap.remove(email);
	}

	@Override
	public void removeAll(){
		Map<String, User> usersMap = hazelcastClient.getMap("users");
		for(Map.Entry<String, User> userMap : usersMap.entrySet()){
			usersMap.remove(userMap.getValue().getEmail());
		}
	}

}