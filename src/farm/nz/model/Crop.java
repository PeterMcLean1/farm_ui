package farm.nz.model;

import farm.nz.type.CropType;

public class Crop extends StoreItem implements Cloneable {
	private CropType type;
	private int salePrice;
	private int maturity; // days from planting to harvest
	private int dayPlanted; // day planted
	private Paddock paddock;

	/**
	 * 
	 * @param type
	 * @param price
	 * @param salePrice
	 * @param maturity
	 * @param residual
	 */
	public Crop(CropType type, int price, int salePrice, int maturity, int residual) {
		super(price, residual);
		this.type = type;
		this.salePrice = salePrice;
		this.maturity = maturity;
	}

	public boolean isMature(Game game) {
		return ((dayPlanted + maturity) <= game.getCurrentDay());

	}

	public CropType getType() {
		return type;
	}

	public void setType(CropType type) {
		this.type = type;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getMaturity() {
		return maturity;
	}

	public void setMaturity(int maturity) {
		this.maturity = maturity;
	}

	public Paddock getPaddock() {
		return paddock;
	}

	public int getDayPlanted() {
		return dayPlanted;
	}

	public void setDayPlanted(int dayPlanted) {
		this.dayPlanted = dayPlanted;
	}

	public void setPaddock(Paddock paddock) {
		this.paddock = paddock;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
