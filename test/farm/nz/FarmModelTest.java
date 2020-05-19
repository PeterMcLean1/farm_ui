package farm.nz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import farm.nz.model.Animal;
import farm.nz.model.Crop;
import farm.nz.model.Farm;
import farm.nz.model.Farmer;
import farm.nz.model.Item;
import farm.nz.model.Paddock;
import farm.nz.type.AnimalType;
import farm.nz.type.CropType;
import farm.nz.type.FarmType;
import farm.nz.type.ItemType;

public class FarmModelTest {

	private Farmer farmer;
	private List<Animal> animals;
	private List<Paddock> paddocks;
	private List<Item> items;
	private Item item1;
	private Paddock paddock1;
	private Animal animal1;
	private Farm farm;

	@BeforeEach
	void setup() {
		farmer = new Farmer("Joe Bloggs", 42);
		animals = new ArrayList<Animal>();
		paddocks = new ArrayList<Paddock>();
		items = new ArrayList<Item>();

		paddock1 = new Paddock();
		paddock1.setCrop(new Crop(CropType.PUMPKIN, 3, 30, 6, 4));
		paddocks.add(paddock1);

		Paddock p2 = new Paddock();
		p2.setCrop(new Crop(CropType.TOMATO, 4, 13, 5, 5));
		paddocks.add(p2);

		item1 = new Item(ItemType.BUG_SPRAY, true, false, false, 3, 2, 3);
		items.add(item1);
		items.add(new Item(ItemType.WORMER, false, false, true, 10, 5, 0));
		items.add(new Item(ItemType.FOOD, false, true, false, 1, 1, 1));
		items.add(new Item(ItemType.VITAMINS, false, true, false, 2, 3, 1));

		animal1 = new Animal(AnimalType.CHICKEN, 1, 5, 10, 2, 10);
		animals.add(animal1);
		animals.add(new Animal(AnimalType.SHEEP, 1, 5, 20, 5, 20));
		animals.add(new Animal(AnimalType.COW, 1, 5, 30, 8, 30));

		farm = new Farm();
		farm.setAnimals(animals);
		farm.setItems(items);
		farm.setPaddocks(paddocks);
	}

	// no args constructor test
	@Test
	void farmSetGetTest() {

		Farm farm = new Farm();
		farm.setAnimals(animals);
		farm.setFarmer(farmer);
		farm.setItems(items);
		farm.setName("Farm1");
		farm.setPaddocks(paddocks);
		farm.setType(FarmType.FLAT);

		assertTrue(farm.getAnimals().size() == 3);
		assertTrue(farm.getFarmer().getName() == "Joe Bloggs");
		assertTrue(farm.getItems().size() == 4);
		assertTrue(farm.getName() == "Farm1");
		assertTrue(farm.getPaddocks().size() == 2);
		assertTrue(farm.getType() == FarmType.FLAT);

	}

	// constructor test
	@Test
	void farmConstructorTest() {

		Farm farm = new Farm(farmer);
		assertTrue(farm.getFarmer().getName() == "Joe Bloggs");
		assertTrue(farm.getFarmer().getAge() == 42);

	}

	@Test
	void addAnimalTest() {

		assertTrue(farm.getAnimals().size() == 3);
		farm.addAnimal(new Animal(AnimalType.SHEEP, 1, 5, 30, 8, 30));
		assertTrue(farm.getAnimals().size() == 4);

	}

	@Test
	void addItemTest() {

		assertTrue(farm.getItems().size() == 4);
		farm.addItem(new Item(ItemType.WORMER, false, false, true, 10, 5, 0));
		assertTrue(farm.getItems().size() == 5);

	}

	@Test
	void addPaddockTest() {

		assertTrue(farm.getPaddocks().size() == 2);
		farm.addPaddock(new Paddock());
		assertTrue(farm.getPaddocks().size() == 3);

	}

	@Test
	void removeAnimalTest() {

		assertTrue(farm.getAnimals().size() == 3);
		farm.removeAnimal(animal1);
		assertTrue(farm.getAnimals().size() == 2);

	}

	@Test
	void removeItemTest() {

		assertTrue(farm.getItems().size() == 4);
		farm.removeItem(item1);
		assertTrue(farm.getItems().size() == 3);

	}

	@Test
	void removePaddockTest() {

		assertTrue(farm.getPaddocks().size() == 2);
		farm.removePaddock(paddock1);
		assertTrue(farm.getPaddocks().size() == 1);

	}

}
