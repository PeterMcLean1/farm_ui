package farm.nz.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.SwingPropertyChangeSupport;

import farm.nz.type.FarmType;

public class Farm {
	public static final String CROP = "crop";
	public static final String PADDOCK = "paddock";
	public static final String ANIMAL = "animal";
	public static final String ITEM = "item";

	private String name;
	private FarmType type;
	private Farmer farmer;
	private List<Paddock> paddocks = new ArrayList<Paddock>();
	private List<Animal> animals = new ArrayList<Animal>();
	private List<Item> items = new ArrayList<Item>();
	private SwingPropertyChangeSupport support = new SwingPropertyChangeSupport(this);

	public List<Paddock> getPaddocks() {
		return paddocks;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
		support.firePropertyChange(ANIMAL, 1, 2);
	}

	public void addAnimal(Animal animal) {

		this.animals.add(animal);
		support.firePropertyChange(ANIMAL, 1, 2);
	}

	public List<Item> getItems() {
		return items;
	}

	public void removeItem(Item item) {

		int oldValue = this.items.size();
		if (items.contains(item)) {
			items.remove(item);
		}
		int newValue = this.items.size();
		support.firePropertyChange(ITEM, oldValue, newValue);
	}

	public void addItem(Item item) {

		this.items.add(item);

		support.firePropertyChange(ITEM, 1, 2);
	}

	public void setItems(List<Item> items) {

		this.items = items;
		support.firePropertyChange(ITEM, 1, 2);
	}

	public void removeAnimal(Animal animal) {

		int oldValue = this.animals.size();
		if (animals.contains(animal)) {
			animals.remove(animal);
		}
		int newValue = this.animals.size();
		support.firePropertyChange(ANIMAL, oldValue, newValue);
	}

	public void setPaddocks(List<Paddock> paddocks) {

		this.paddocks = paddocks;
		support.firePropertyChange(PADDOCK, 1, 2);
	}

	public void addPaddock(Paddock paddock) {

		this.paddocks.add(paddock);
		support.firePropertyChange(PADDOCK, 1, 2);
	}

	public void removePaddock(Paddock paddock) {

		int oldValue = this.paddocks.size();
		if (paddocks.contains(paddock)) {
			paddocks.remove(paddock);
		}
		int newValue = this.paddocks.size();
		support.firePropertyChange(PADDOCK, oldValue, newValue);
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

	public FarmType getType() {
		return type;
	}

	public void setType(FarmType type) {
		this.type = type;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

}
