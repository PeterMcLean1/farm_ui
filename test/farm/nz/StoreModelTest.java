package farm.nz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import farm.nz.model.Animal;
import farm.nz.model.Crop;
import farm.nz.model.Item;
import farm.nz.model.Store;
import farm.nz.type.AnimalType;
import farm.nz.type.CropType;
import farm.nz.type.ItemType;

public class StoreModelTest {
	
	@Test
	void storeSetGetTest() {

		Store store = new Store();
		
		assertTrue(store.getAnimalList().size() == 3);
		assertTrue(store.getItemList().size() == 6);
		assertTrue(store.getCropList().size() == 7);
		
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(new Animal(AnimalType.CHICKEN, 1, 5, 10, 2, 10));
		List<Crop> crops = new ArrayList<Crop>();
		crops.add(new Crop(CropType.TOMATO, 4, 13, 5, 5));
		crops.add(new Crop(CropType.PINE, 4, 13, 5, 5));
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(ItemType.FOOD, false, true, false, 1, 1, 1));
		
		store.setAnimalList(animals);
		store.setCropList(crops);
		store.setItemList(items);
		
		assertTrue(store.getAnimalList().size() == 1);
		assertTrue(store.getItemList().size() == 1);
		assertTrue(store.getCropList().size() == 2);
		

	}

}
