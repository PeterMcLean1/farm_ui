package farm.nz.model;

public abstract class StoreItem {

	private int purchasePrice;
	private int residualValue;

	public StoreItem() {

	}

	/**
	 * 
	 * @param purchasePrice
	 * @param residualValue
	 */
	public StoreItem(int purchasePrice, int residualValue) {
		this.purchasePrice = purchasePrice;
		this.residualValue = residualValue;

	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getResidualValue() {
		return residualValue;
	}

	public void setResidualValue(int residual) {
		this.residualValue = residual;
	}

}
