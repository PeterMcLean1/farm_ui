package farm.nz.model;

import farm.nz.type.ItemType;

/**
 * 
 * @author peter.mclean
 *
 */
public class Item extends StoreItem {
	/**
	 * Represents the type of this item.
	 */
	private ItemType type;
	/**
	 * Indicates whether the item can be applied to a crop.
	 */
	private boolean crop;
	/**
	 * Indicates whether the item can be applied to an animal.
	 */
	private boolean animal;
	private boolean skill;

	/**
	 * The bonus to add when this item is used on crop or animal.
	 */
	private int bonus;

	public Item(ItemType type, boolean crop, boolean animal, boolean skill, int price, int bonus, int residual) {
		super(price, residual);
		this.type = type;
		this.crop = crop;
		this.animal = animal;
		this.skill = skill;
		this.bonus = bonus;
	}

	public boolean isSkill() {
		return skill;
	}

	public void setSkill(boolean skill) {
		this.skill = skill;
	}

	/**
	 * Gets the item type.
	 * 
	 * @return An ItemType representing the type of this item.
	 */
	public ItemType getType() {
		return type;
	}

	/**
	 * Sets the item type.
	 * 
	 * @param type An ItemType representing the type of this item.
	 */
	public void setType(ItemType type) {
		this.type = type;
	}

	/**
	 * Indicates whether this item can be used on a crop.
	 * 
	 * @return A boolean indicating if this item can be used on a crop.
	 */
	public boolean isCrop() {
		return crop;
	}

	/**
	 * Sets whether this item can be used on a crop.
	 * 
	 * @param boolean Indicates if this item can be used on a crop.
	 */
	public void setCrop(boolean crop) {
		this.crop = crop;
	}

	/**
	 * Indicates whether this item can be used on an animal.
	 * 
	 * @return A boolean indicating if this item can be used on an animal.
	 */
	public boolean isAnimal() {
		return animal;
	}

	/**
	 * Sets whether this item can be used on an animal.
	 * 
	 * @param boolean Indicates if this item can be used on an animal.
	 */
	public void setAnimal(boolean animal) {
		this.animal = animal;
	}

	/**
	 * Gets the bonus to apply to crop or animal when used.
	 * 
	 * @return An int representing the bonus to apply to crop or animal when used.
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * Sets the bonus to apply to crop or animal when used.
	 * 
	 * @param int The bonus to apply to crop or animal when used.
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

}
