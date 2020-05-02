package farm.nz.type;

public enum AnimalType {
	CHICKEN("Chicken"), SHEEP("Sheep"), COW("Cow");

	private final String display;

	AnimalType(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

}
