package farm.nz.type;

public enum CropType {
	CORN("Corn"), TOMATO("Tomato"), BARLEY("Barley"), PUMPKIN("Pumpkin"), WHEAT("Wheat"), PEA("Peas"),
	PINE("Pine Trees");

	private final String display;

	CropType(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}
}
