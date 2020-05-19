package farm.nz.model;

import java.util.ArrayList;
import java.util.List;

import farm.nz.type.AnimalType;
import farm.nz.type.CropType;
import farm.nz.type.ItemType;

public class Store {
	private List<Crop> cropList = new ArrayList<Crop>();
	private List<Item> itemList = new ArrayList<Item>();
	private List<Animal> animalList = new ArrayList<Animal>();

	/**
	 * 
	 */
	public Store() {
		// (CropType type, int purchasePrice, int salePrice, int maturity, int residual)
		cropList.add(new Crop(CropType.CORN, 5, 20, 4, 6));
		cropList.add(new Crop(CropType.TOMATO, 4, 13, 5, 5));
		cropList.add(new Crop(CropType.BARLEY, 6, 12, 3, 7));
		cropList.add(new Crop(CropType.PUMPKIN, 3, 30, 6, 4));
		cropList.add(new Crop(CropType.WHEAT, 1, 4, 3, 2));
		cropList.add(new Crop(CropType.PEA, 2, 6, 3, 3));
		cropList.add(new Crop(CropType.PINE, 2, 12, 4, 6));
		// (ItemType type, boolean crop, boolean animal, int price, int bonus, int
		// residual)
		itemList.add(new Item(ItemType.FERTILIZER, true, false, false, 5, 3, 5));
		itemList.add(new Item(ItemType.WEED_SPRAY, true, false, false, 3, 2, 3));
		itemList.add(new Item(ItemType.BUG_SPRAY, true, false, false, 2, 1, 2));
		// itemList.add(new Item(ItemType.TRAINING, false, false, true, 10, 5, 0));
		itemList.add(new Item(ItemType.FOOD, false, true, false, 1, 1, 1));
		itemList.add(new Item(ItemType.VITAMINS, false, true, false, 2, 3, 1));
		itemList.add(new Item(ItemType.WORMER, false, true, false, 3, 5, 2));
		// (AnimalType type, int happy, int health, int purchasePrice, int dailyIncome,
		// int residual)
		animalList.add(new Animal(AnimalType.CHICKEN, 1, 5, 10, 2, 10));
		animalList.add(new Animal(AnimalType.SHEEP, 1, 5, 20, 5, 20));
		animalList.add(new Animal(AnimalType.COW, 1, 5, 30, 8, 30));

	}

	public List<Crop> getCropList() {
		return cropList;
	}

	public void setCropList(List<Crop> cropList) {
		this.cropList = cropList;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public List<Animal> getAnimalList() {
		return animalList;
	}

	public void setAnimalList(List<Animal> animalList) {
		this.animalList = animalList;
	}

}
