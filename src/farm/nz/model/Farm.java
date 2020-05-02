package farm.nz.model;

import java.util.ArrayList;
import java.util.List;

import farm.nz.type.FarmType;

public class Farm {
	private String name;
	private int account;
	private int maintenance;
	private FarmType type;
	private Farmer farmer;
	private List<Paddock> paddocks = new ArrayList<Paddock>();
	private List<Animal> animals = new ArrayList<Animal>();
	private List<Item> items = new ArrayList<Item>();

	public List<Paddock> getPaddocks() {
		return paddocks;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	public void addAnimal(Animal animal) {
		this.animals.add(animal);
	}

	public List<Item> getItems() {
		return items;
	}

	public void removeItem(Item item) {
		if (items.contains(item)) {
			items.remove(item);
		}
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void removeAnimal(Animal animal) {
		if (animals.contains(animal)) {
			animals.remove(animal);
		}
	}

	public void setPaddocks(List<Paddock> paddocks) {
		this.paddocks = paddocks;
	}

	public void addPaddock(Paddock paddock) {
		this.paddocks.add(paddock);
	}

	public void removePaddock(Paddock paddock) {
		if (paddocks.contains(paddock)) {
			paddocks.remove(paddock);
		}
	}

	public Farm() {

	}

	public Farm(Farmer farmer) {
		this.farmer = farmer;

	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(int maintenance) {
		this.maintenance = maintenance;
	}

	public FarmType getType() {
		return type;
	}

	public void setType(FarmType type) {
		this.type = type;
	}

}
