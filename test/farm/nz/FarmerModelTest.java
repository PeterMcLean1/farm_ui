package farm.nz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import farm.nz.model.Farmer;

public class FarmerModelTest {
	
	@Test
	void farmerConstructorTest() {

		Farmer farmer = new Farmer("Joe Bloggs", 42);
		
		assertTrue(farmer.getName() == "Joe Bloggs");
		assertTrue(farmer.getAge() == 42);

	}
	
	@Test
	void farmerSetGetTest() {

		Farmer farmer = new Farmer();
		
		farmer.setName("Bob Purtie");
		farmer.setAge(56);

		assertTrue(farmer.getName() == "Bob Purtie");
		assertTrue(farmer.getAge() == 56);

	}

}
