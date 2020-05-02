package farm.nz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import farm.nz.model.Animal;
import farm.nz.model.Crop;
import farm.nz.model.Farm;
import farm.nz.model.Game;
import farm.nz.model.Item;
import farm.nz.model.Paddock;

/**
 * Contains methods to print general game play options to console
 * 
 * @author peter.mclean
 *
 */
public class GameUtil {

	static Scanner keyboard = new Scanner(System.in);

	/**
	 * Pushes the existing text output up the screen so that it does not distract
	 * from the current user actions
	 */
	public static void clearScreen() {
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}

	}

	/**
	 * End of day logic
	 * 
	 * @param game Used to track game instance progress
	 */
	private static void dayEnd(Game game) {
		Farm farm = game.getFarm();
		int bonus = 0;

		List<Animal> animals = farm.getAnimals();
		for (Animal animal : animals) {
			bonus = bonus + animal.getDailyIncome();

		}

		farm.setAccount(farm.getAccount() + bonus);

		if (game.getCurrentDay() == game.getDaysToPlay()) {
			endGame(game);
		} else {
			dayStart(game);
		}

	}

	/**
	 * Start of day logic
	 * 
	 * @param game Used to track game instance progress
	 */
	private static void dayStart(Game game) {
		game.incrementCurrentDay();
		game.setActionCount(0);
		Farm farm = game.getFarm();
		boolean skill = false;
		for (Item item : farm.getItems()) {
			if (item.isSkill()) {
				skill = true;

			}
		}

		// reduce animal health and happy by 1
		List<Animal> animals = farm.getAnimals();
		for (Animal a : animals) {
			if (!skill && a.getHappy() > 0) {
				a.setHappy(a.getHappy() - 1);
			}
			if (!skill && a.getHealth() > 0) {
				a.setHealth(a.getHealth() - 1);
			}
		}

		int chance = farm.getType().getEventChance();

		Random rnd = new Random();
		int i = rnd.nextInt(100);

		if (i < chance) {
			int eventType = -1;
			eventType = rnd.nextInt(3);
			StringBuffer sb = new StringBuffer();

			switch (eventType) {
			case 0:
				// drought
				// get farm crops and delete half (rounded down)
				List<Paddock> paddocks = farm.getPaddocks();
				List<Paddock> cropPaddock = new ArrayList<Paddock>();
				int cropsLost = 0;
				for (Paddock p : paddocks) {
					if (p.hasCrop()) {
						cropPaddock.add(p);
					}
				}
				int cropLength = cropPaddock.size() / 2;
				while (cropLength > 0) {
					int deleteCrop = rnd.nextInt(cropPaddock.size());
					Paddock affectedPaddock = cropPaddock.remove(deleteCrop);
					affectedPaddock.setCrop(null);
					cropsLost++;
					cropLength--;

				}

				sb.append("!!! SEVERE WEATHER WARNING !!!\n\n");
				sb.append("A drought has occured!\n");
				sb.append("The wells have dried up and the crops are thirsty.\n");
				sb.append("You have lost ");
				sb.append(cropsLost);
				sb.append(" crop");
				if (cropsLost > 1) {
					sb.append("s");
				}

				eventScreen(sb, game);
				break;
			case 1:
				// fence break
				// get farm animals
				// remove one or more of all farm animals
				// remaining animals lose 'substantial' happiness

				int animalLength = animals.size();
				int animalsLost = 0;
				if (animalLength == 0) {
					// there are no animals on farm
					// do nothing
				} else if (animalLength == 1) {
					// only 1 animal on farm so remove it
					animals.remove(0);
					animalsLost++;
				} else {
					// more than 1 animal on farm so remove one
					// at random, and 20% chance of removing other
					// animals
					int deleteAnimal = rnd.nextInt(animalLength);
					animals.remove(deleteAnimal);
					List<Animal> animalList = new ArrayList<Animal>(animals);
					for (Animal a : animalList) {
						a.setHappy(a.getHappy() - 2);
						int animalChance = rnd.nextInt(100);
						if (animalChance < 20) {
							animals.remove(a);
							animalsLost++;
						}
					}
				}

				sb.append("!!! BROKEN FENCE !!!\n\n");
				sb.append("Some of your animals (");
				sb.append(animalsLost);
				sb.append(") have escaped through a broken fence and are lost forever!\n");
				sb.append("The remaining animals are not so happy.");
				eventScreen(sb, game);
				break;
			case 2:
				// win county fair
				// add 'reasonable' sum of money to farm account
				// money earned scaled number of farm crops and animals
				int winnings = 30;
				animalLength = farm.getAnimals().size();
				winnings = winnings + 5 * animalLength;

				int numberCrops = 0;
				for (Paddock p : farm.getPaddocks()) {
					if (p.hasCrop()) {
						numberCrops++;
					}
				}
				winnings = winnings + 5 * numberCrops;
				farm.setAccount(farm.getAccount() + winnings);

				sb.append("!!! COUNTY FAIR AWARDS !!!\n\n");
				sb.append("Your farm has won the top award at the annual county fair.\n");
				sb.append("The prize winnings ($");
				sb.append(winnings);
				sb.append(") have been added to your farm account.");
				eventScreen(sb, game);
				break;

			}
		}
		mainScreen(game);

	}

	public static void eventScreen(StringBuffer sb, Game game) {
		header(game);
		sb.append("\n1. Return to main menu");

		System.out.println(sb);
		GameUtil.getInputNumber(); // accept any input

		mainScreen(game);

	}

	public static void endGame(Game game) {

		Farm farm = game.getFarm();
		int score = farm.getAccount();
		for (Paddock paddock : farm.getPaddocks()) {
			if (null != paddock.getCrop()) {
				score = score + paddock.getCrop().getResidualValue();
			}
		}
		for (Animal animal : farm.getAnimals()) {
			score = score + animal.getResidualValue();
		}
		for (Item item : farm.getItems()) {
			score = score + item.getResidualValue();
		}
		header(game);
		System.out.println("Game over! Score: " + score);

	}

	private static void noActionAnimal(Game game) {
		header(game);
		System.out.println(
				"You have no actions remaining for the day.\nMove on to the next day to refresh your actions!\n 1. Return to Animal list");
		GameUtil.getInputNumber();
		viewAnimals(game);
	}

	private static void noActionMaintenance(Game game) {
		header(game);
		System.out.println(
				"You have no actions remaining for the day.\nMove on to the next day to refresh your actions!\n 1. Return to Farm maintenance");
		GameUtil.getInputNumber();
		farmMaintenance(game);
	}

	private static void noActionCrop(Game game) {
		header(game);
		System.out.println(
				"You have no actions remaining for the day.\nMove on to the next day to refresh your actions!\n 1. Return to Crop list");
		GameUtil.getInputNumber();
		viewCrops(game);
	}

	private static boolean gotActions(Game game) {
		return game.getActionCount() < game.getMaxDailyActions();

	}

	/**
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void farmMaintenance(Game game) {
		header(game);
		Farm farm = game.getFarm();
		int maxPaddocks = farm.getType().getMaxPaddocks();
		int numPaddocks = farm.getPaddocks().size();
		if (numPaddocks >= maxPaddocks) {
			System.out.println("1. You have no more land to create paddocks");
		} else {
			System.out.println("1. *Clear land and erect fence (+1 paddock to your farm)");
		}

		System.out.println("2. *Repair barn (+3 happiness to a random animal)");
		System.out.println("3. Return to main menu");
		int selection = GameUtil.getInputNumber();

		switch (selection) {
		case 1:
			if (gotActions(game)) {
				if (numPaddocks < maxPaddocks) {
					Paddock paddock = new Paddock();
					game.getFarm().addPaddock(paddock);
					game.incrementActionCount();
				}
				mainScreen(game);
			} else {
				noActionMaintenance(game);
			}

			break;
		case 2:
			if (gotActions(game)) {
				List<Animal> animals = game.getFarm().getAnimals();
				if (animals.size() > 0) {
					Random rand = new Random();
					Animal animal = animals.get(rand.nextInt(animals.size()));
					animal.setHappy(animal.getHappy() + 3);
					game.incrementActionCount();
				}
				mainScreen(game);
			} else {
				noActionMaintenance(game);
			}

			break;
		default:
			mainScreen(game);
		}

	}

	/**
	 * Handles input that needs to be safely parsed from String to Integer.
	 * 
	 * @return Returns the input parsed as an int or -1 on error.
	 */
	public static int getInputNumber() {
		if (keyboard.hasNext()) {
			try {
				return Integer.parseInt(keyboard.next());
				// parseInt will throw NumberFormatException
				// if the next token does not match the Integer
				// regular expression, or is out of range
			} catch (NumberFormatException nfe) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * Prints a header with useful status information for the current farmer
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void header(Game game) {
		clearScreen();
		Farm farm = game.getFarm();
		StringBuffer sb1 = new StringBuffer();
		sb1.append(farm.getName());
		int nameLength = sb1.length();
		int spaces = 25 - nameLength;
		for (int i = 0; i < spaces; i++) {
			sb1.append(" ");
		}
		StringBuffer sb2 = new StringBuffer();
		sb2.append("---------------------------------------------\n");
		sb2.append("| ");
		sb2.append(sb1);
		sb2.append("   Account: $");
		sb2.append(farm.getAccount());
		sb2.append(" |\n");
		sb2.append("---------------------------------------------\n");
		sb2.append("| Day: ");
		sb2.append(game.getCurrentDay());
		sb2.append(" of ");
		sb2.append(game.getDaysToPlay());
		sb2.append("         Actions used: ");
		sb2.append(game.getActionCount());
		sb2.append(" of ");
		sb2.append(game.getMaxDailyActions());
		sb2.append(" |\n");
		sb2.append("---------------------------------------------");
		System.out.println(sb2.toString());
	}

	/**
	 * Displays the main menu for the game
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void mainScreen(Game game) {
		StringBuffer sb = new StringBuffer();
		sb.append("What would you like to do next? (1-5):\n\n");
		sb.append("1. View Crops\n");
		sb.append("2. View Animals\n");
		sb.append("3. View Supplies\n");
		sb.append("4. Improve your Farm\n");
		sb.append("5. Visit the General Store\n");
		if (game.getCurrentDay() == game.getDaysToPlay()) {
			sb.append("6. End Game\n");
		} else {
			sb.append("6. Move to next day\n");
		}

		boolean looper = true;

		while (looper) {
			header(game);
			System.out.println(sb.toString());
			int selection = GameUtil.getInputNumber();

			switch (selection) {
			case 1:
				viewCrops(game);
				looper = false;
				break;
			case 2:
				viewAnimals(game);
				looper = false;
				break;
			case 3:
				viewItems(game);
				looper = false;
				break;
			case 4:
				farmMaintenance(game);
				looper = false;
				break;
			case 5:
				StoreUtil.mainStore(game);
				looper = false;
				break;
			case 6:
				dayEnd(game);
				looper = false;
				break;
			default:
				continue;
			}
		}

	}

	/**
	 * Used to set up programmed game options prior to the start of game play
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void setupEnvironment(Game game) {
		Farm farm = game.getFarm();
		farm.setAccount(50);
		// farm.setType(FarmType.FLAT);
		// farm.setName("Peter Valley Farm");
		// game.setDaysToPlay(5);
		game.setMaxDailyActions(2);
		farm.addPaddock(new Paddock());
		farm.addPaddock(new Paddock());
	}

	public static void startInfo(Game game) {
		StringBuffer sb = new StringBuffer();
		// TODO write game instructions

		sb.append("\n1. Return to main menu");

		System.out.println(sb);
		GameUtil.getInputNumber(); // accept any input
	}

	/**
	 * Displays the actions available to be applied to an animal
	 * 
	 * @param animal The animal being viewed
	 * @param game   Used to track game instance progress
	 */

	public static void viewAnimal(Animal animal, Game game) {
		header(game);
		System.out.println("You have selected a " + animal.getType().getDisplay());
		System.out.println("What action do you wish to perform?\n");
		System.out.println("1. *Play with animal (+2 happiness)");
		System.out.println("2. *Use item on animal (+item-bonus health)");
		System.out.println("3. Return to Animal list");

		int selection = GameUtil.getInputNumber();

		switch (selection) {
		case 1:
			if (gotActions(game)) {
				animal.setHappy(animal.getHappy() + 2);
				game.incrementActionCount();
				viewAnimals(game);
			} else {
				noActionAnimal(game);
			}

			break;
		case 2:
			if (gotActions(game)) {
				viewAnimalItems(animal, game);
			} else {
				noActionAnimal(game);
			}

			break;
		default:
			viewAnimals(game);
		}

	}

	/**
	 * 
	 * @param animal The animal to apply an item to
	 * @param game   Used to track game instance progress
	 */
	public static void viewAnimalItems(Animal animal, Game game) {
		header(game);
		Farm farm = game.getFarm();
		List<Item> items = farm.getItems();
		List<Item> animalItems = new ArrayList<Item>();

		for (Item item : items) {
			if (item.isAnimal()) {
				animalItems.add(item);
			}
		}
		if (animalItems.size() > 0) {
			System.out.println("Select the item you wish to use:");
		} else {
			System.out.println("You have no farm supplies able to be used on animals.");
			System.out.println("Hint: Farm supplies can be purchased from the General Store.");
		}

		int lineNumber = 1;

		for (Item item : animalItems) {
			System.out.println(lineNumber + ". " + item.getType().getDisplay());

			lineNumber++;
		}
		System.out.println(lineNumber + ". Return to animal list");
		int selection = GameUtil.getInputNumber();
		lineNumber = 1;

		for (Item item : animalItems) {

			if (selection == lineNumber) {
				animal.setHealth(animal.getHealth() + item.getBonus());
				farm.getItems().remove(item);
				game.incrementActionCount();

			}
			lineNumber++;
		}
		viewAnimals(game);

	}

	/**
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void viewAnimals(Game game) {
		Farm farm = game.getFarm();
		List<Animal> animals = farm.getAnimals();
		header(game);
		int lineNumber = 1;
		if (animals.size() == 0) {
			System.out.println("You have no farm animals. \nHint: Animals can be purchased from the General Store.");
		} else {
			System.out.println("Select a farm animal you wish to tend:");
		}

		for (Animal animal : animals) {

			System.out.println(lineNumber + ". " + animal.getType().getDisplay() + " (Happiness: " + animal.getHappy()
					+ " Health: " + animal.getHealth() + ")");
			lineNumber++;
		}
		System.out.println(lineNumber + ". Return to main menu");
		int selection = GameUtil.getInputNumber();

		lineNumber = 1;

		for (Animal animal : animals) {
			if (selection == lineNumber) {
				viewAnimal(animal, game);
			}
			lineNumber++;
		}
		mainScreen(game);

	}

	/**
	 * 
	 * @param paddock
	 * @param game    Used to track game instance progress
	 */
	public static void viewCropItems(Paddock paddock, Game game) {
		header(game);
		Farm farm = game.getFarm();
		List<Item> items = farm.getItems();
		List<Item> cropItems = new ArrayList<Item>();
		for (Item item : items) {
			if (item.isCrop()) {
				cropItems.add(item);
			}
		}
		if (cropItems.size() > 0) {
			System.out.println("Select the item you wish to use:");
		} else {
			System.out.println("You have no farm supplies able to be used on crops.");
			System.out.println("Hint: Crops can be purchased from the General Store.");
		}

		int lineNumber = 1;

		for (Item item : cropItems) {
			System.out.println(lineNumber + ". " + item.getType().getDisplay());
			lineNumber++;
		}

		System.out.println(lineNumber + ". Return to crop list");
		int selection = GameUtil.getInputNumber();

		lineNumber = 1;

		for (Item item : cropItems) {
			if (selection == lineNumber) {
				paddock.getCrop().setMaturity(paddock.getCrop().getMaturity() - item.getBonus());
				farm.getItems().remove(item);
				game.incrementActionCount();
			}
			lineNumber++;
		}

		viewCrops(game);

	}

	/**
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void viewCrops(Game game) {
		Farm farm = game.getFarm();
		List<Paddock> paddocks = farm.getPaddocks();
		header(game);
		boolean hasCrops = false;
		for (Paddock p : paddocks) {
			if (p.hasCrop()) {
				hasCrops = true;
			}
		}
		if (hasCrops) {
			System.out.println("Select the paddock/crop you wish to tend:");
		} else {
			System.out.println(
					"You have no crops in your paddocks.\nHint: Crops can be purchased from the General Store.");
		}
		int lineNumber = 1;

		for (Paddock paddock : paddocks) {
			if (paddock.hasCrop()) {
				Crop crop = paddock.getCrop();
				int timeGrown = game.getCurrentDay() - crop.getDayPlanted();
				int timeMature = crop.getDayPlanted()
						+ StoreUtil.calculateGrowth(crop.getMaturity(), farm.getType().getCropGrowthRate())
						- game.getCurrentDay();
				System.out
						.println(lineNumber + ". Paddock " + paddock.getPaddockID() + " (" + crop.getType().getDisplay()
								+ ", Days grown: " + timeGrown + ", Days to harvest: " + timeMature + ")");
			} else {
				System.out.println(lineNumber + ". Paddock " + paddock.getPaddockID() + " (no crop so cannot select!)");
				// System.out.println("NOTE: Visit the General Store to buy and plant crops");
			}
			lineNumber++;
		}
		System.out.println(lineNumber + ". Return to main menu");
		int selection = GameUtil.getInputNumber();

		lineNumber = 1;

		for (Paddock paddock : farm.getPaddocks()) {
			if (selection == lineNumber) {
				viewPaddock(paddock, game);
			}
			lineNumber++;
		}

		mainScreen(game);

	}

	/**
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void viewItems(Game game) {
		header(game);
		List<Item> items = game.getFarm().getItems();
		if (items.size() == 0) {
			System.out.println("You have no farm supplies. \nHint: Supplies can be purchased from the General Store.");
		} else {
			System.out.println("This is a list of your farm supplies:");
		}
		Farm farm = game.getFarm();
		int lineNumber = 1;

		for (Item item : items) {
			System.out.println(lineNumber + ". " + item.getType().getDisplay());
			lineNumber++;
		}

		System.out.println(lineNumber + ". Return to main menu");
		GameUtil.getInputNumber(); // accept any input
		mainScreen(game);

	}

	/**
	 * Menu to select actions on a crop
	 * 
	 * @param paddock A farm space to plant crops in
	 * @param game    Used to track game instance progress
	 */
	public static void viewPaddock(Paddock paddock, Game game) {
		header(game);

		Crop crop = paddock.getCrop();
		if (null == crop) {
			viewCrops(game);
		} else {

			System.out.println("You have selected paddock " + paddock.getPaddockID() + " containing "
					+ crop.getType().getDisplay());
			System.out.println("What action do you wish to perform?\n");
			System.out.println("1. *Water crop");
			System.out.println("2. *Use item on crop");
			if (crop.isMature(game)) {
				System.out.println("3. *Harvest crop (crop is ready to harvest)");
			} else {
				System.out.println("3. *Harvest crop (crop is NOT ready to harvest)");
			}

			System.out.println("4. Return to crop list");

			int selection = GameUtil.getInputNumber();

			switch (selection) {
			case 1:
				if (gotActions(game)) {
					crop.setMaturity(crop.getMaturity() - 1);
					game.incrementActionCount();
					viewCrops(game);
				} else {
					noActionCrop(game);
				}

				break;
			case 2:
				if (gotActions(game)) {
					viewCropItems(paddock, game);
				} else {
					noActionCrop(game);
				}
				break;
			case 3:
				if (gotActions(game)) {
					if (paddock.getCrop().isMature(game)) {
						game.getFarm().setAccount(game.getFarm().getAccount() + paddock.getCrop().getSalePrice());
						paddock.setCrop(null);
					}
					viewPaddock(paddock, game);
				} else {
					noActionCrop(game);
				}

				break;
			default:
				viewCrops(game);
			}
		}

	}

}
