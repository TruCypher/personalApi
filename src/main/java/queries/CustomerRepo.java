package queries;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import entity.Customer;


public interface CustomerRepo extends MongoRepository<Customer, String> {
	
	public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    
}