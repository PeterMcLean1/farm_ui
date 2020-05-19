package farm.nz.type;

public enum FarmType {
	FLAT(5, 4, 10, 50, "Flat land", "Easy"), HILL(10, 3, 8, 40, "Hill country", "Medium"),
	RIVER(15, 2, 6, 30, "River", "Hard"), FOREST(20, 1, 4, 20, "Forest", "Insane");

	private final int eventChance;
	private final int animalBonus;
	private final int startMoney;
	/**
	 * The maximum number of paddocks allowed for this type of farm.
	 */
	private final int maxPaddocks;
	/**
	 * Display name of this farm type.
	 */
	private final String display;

	private final String difficulty;

	FarmType(int eventChance, int animalBonus, int maxPaddocks, int startMoney, String display,
			String difficulty) {
		this.eventChance = eventChance;
		this.animalBonus = animalBonus;
		this.maxPaddocks = maxPaddocks;
		this.display = display;
		this.startMoney = startMoney;
		this.difficulty = difficulty;

	}

	public int getEventChance() {
		return eventChance;
	}

	public int getAnimalBonus() {
		return animalBonus;
	}

	public int getStartMoney() {
		return startMoney;
	}

	public int getMaxPaddocks() {
		return maxPaddocks;
	}

	public String getDisplay() {
		return display;
	}

	public String getDifficulty() {
		return difficulty;
	}

}
