package farm.nz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import farm.nz.model.Animal;
import farm.nz.type.AnimalType;

public class AnimalModelTest {

	@Test
	void animalConstructorTest() {

		Animal animal = new Animal(AnimalType.CHICKEN, 5, 6, 10, 3, 9);
		assertTrue(animal.getType() == AnimalType.CHICKEN);
		assertTrue(animal.getBaseIncome() == 3);
		assertTrue(animal.getHappy() == 5);
		assertTrue(animal.getHealth() == 6);
		assertTrue(animal.getResidualValue() == 9);
		assertTrue(animal.getPurchasePrice() == 10);

	}

	@Test
	void animalSetGetTest() {

		Animal animal = new Animal(AnimalType.CHICKEN, 5, 6, 10, 3, 9);

		animal.setType(AnimalType.COW);
		animal.setBaseIncome(4);
		animal.setHappy(7);
		animal.setHealth(2);
		animal.setResidualValue(18);
		animal.setPurchasePrice(1);

		assertTrue(animal.getType() == AnimalType.COW);
		assertTrue(animal.getBaseIncome() == 4);
		assertTrue(animal.getHappy() == 7);
		assertTrue(animal.getHealth() == 2);
		assertTrue(animal.getResidualValue() == 18);
		assertTrue(animal.getPurchasePrice() == 1);
	}

}
