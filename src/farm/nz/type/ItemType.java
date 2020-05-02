package farm.nz.type;

public enum ItemType {
	FERTILIZER("Fertiliser"), SPRAY("Weed Spray"), TRAINING("Skill Training"), FOOD("Animal Food"),
	VITAMINS("Animal Vitamins"), WORMER("Animal Wormer");

	private final String display;

	ItemType(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

}
