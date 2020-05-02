package farm.nz.type;

public enum FarmType {
	FLAT(5, 1, 1, 5, "Flat land"), HILL(10, 1, 1, 8, "Hill country"), RIVER(15, 1, 1, 6, "River"),
	FOREST(20, 1, 1, 10, "Forest");

	private final int eventChance;
	private final int animalBonus;
	private final double cropGrowthRate;
	/**
	 * The maximum number of paddocks allowed for this type of farm
	 */
	private final int maxPaddocks;
	/**
	 * Display name of this farm type
	 */
	private final String display;

	FarmType(int eventChance, int animalBonus, double cropGrowthRate, int maxPaddocks, String display) {
		this.eventChance = eventChance;
		this.animalBonus = animalBonus;
		this.cropGrowthRate = cropGrowthRate;
		this.maxPaddocks = maxPaddocks;
		this.display = display;

	}

	public int getEventChance() {
		return eventChance;
	}

	public int getAnimalBonus() {
		return animalBonus;
	}

	public double getCropGrowthRate() {
		return cropGrowthRate;
	}

	public int getMaxPaddocks() {
		return maxPaddocks;
	}

	public String getDisplay() {
		return display;
	}

}
