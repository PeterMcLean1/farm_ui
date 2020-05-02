package farm.nz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import farm.nz.model.Animal;
import farm.nz.model.Crop;
import farm.nz.model.Farm;
import farm.nz.model.Game;
import farm.nz.model.Item;
import farm.nz.model.Paddock;
import farm.nz.model.Store;

/**
 * Contains methods to print general store options to console
 * 
 * @author peter.mclean
 *
 */
public class StoreUtil {

	static Scanner keyboard = new Scanner(System.in);

	/**
	 * Displays animals for purchase from the general store
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void buyAnimals(Game game) {
		GameUtil.header(game);
		Farm farm = game.getFarm();
		System.out.println("Please select the Animal to purchase?");
		Store store = new Store();
		List<Animal> animals = store.getAnimalList();
		int lineNumber = 1;

		for (Animal animal : animals) {
			System.out.println(lineNumber + ". " + animal.getType().getDisplay() + "(Buy: $" + animal.getPurchasePrice()
					+ ", Base income: $" + animal.getBaseIncome() + ")");
			lineNumber++;
		}

		System.out.println(lineNumber + ". General Store Menu");
		int selection = GameUtil.getInputNumber();
		lineNumber = 1;

		for (Animal animal : animals) {
			if (selection == lineNumber) {
				if (animal.getPurchasePrice() <= farm.getAccount()) {
					animal.setHappy(animal.getHappy() + farm.getType().getAnimalBonus());
					farm.setAccount(farm.getAccount() - animal.getPurchasePrice());
					farm.addAnimal(animal);

				}
			}
			lineNumber++;
		}

		mainStore(game);

	}

	public static int calculateGrowth(int maturity, double rate) {
		return (int) Math.round(maturity * rate);
	}

	/**
	 * Displays crops for purchase from the general store
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void buyCrops(Game game) {
		GameUtil.header(game);
		Farm farm = game.getFarm();
		double growthRate = farm.getType().getCropGrowthRate();
		List<Paddock> paddocks = farm.getPaddocks();
		List<Paddock> emptyPaddocks = new ArrayList<Paddock>();
		Store store = new Store();
		List<Crop> crops = store.getCropList();

		for (Paddock p : paddocks) {
			if (!p.hasCrop()) {
				emptyPaddocks.add(p);
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("You have ");
		sb.append(emptyPaddocks.size());
		sb.append(" empty Paddocks to plant Crops in\n");
		if (emptyPaddocks.size() > 0) {
			sb.append("Select the Crop you wish to plant (1-");
			sb.append(crops.size());
			sb.append(")\n");
		} else {
			sb.append("You have no space to plant Crops.\n");
		}

		int lineNumber = 1;
		for (Crop crop : crops) {
			sb.append(lineNumber);
			sb.append(". ");
			sb.append(crop.getType().getDisplay());
			sb.append(" (Buy: $");
			sb.append(crop.getPurchasePrice());
			sb.append(", Sell: $");
			sb.append(crop.getSalePrice());
			sb.append(", Days to grow: ");
			sb.append(calculateGrowth(crop.getMaturity(), growthRate));
			sb.append(")\n");
			lineNumber++;
		}
		sb.append(lineNumber);
		sb.append(". Return to the General Store menu");

		System.out.println(sb.toString());

		int selection = GameUtil.getInputNumber();

		if (emptyPaddocks.size() == 0) {
			mainStore(game);
		}
		lineNumber = 1;

		for (Crop crop : crops) {

			if (selection == lineNumber) {
				if (crop.getPurchasePrice() <= farm.getAccount()) {
					farm.setAccount(farm.getAccount() - crop.getPurchasePrice());
					Paddock p = emptyPaddocks.get(0);
					crop.setDayPlanted(game.getCurrentDay());
					p.setCrop(crop);
					emptyPaddocks.remove(p);

				}
			}
			lineNumber++;

		}
		mainStore(game);
	}

	/**
	 * Displays items/supplies for purchase from the general store
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void buySupplies(Game game) {
		GameUtil.header(game);
		Farm farm = game.getFarm();
		Store store = new Store();
		List<Item> items = store.getItemList();
		int lineNumber = 1;
		StringBuffer sb = new StringBuffer();
		sb.append("Select the item you wish to purchase (1-");
		sb.append(items.size());
		sb.append(")\n\n");

		for (Item item : items) {
			sb.append(lineNumber);
			sb.append(". ");
			sb.append(item.getType().getDisplay());
			sb.append("($");
			sb.append(item.getPurchasePrice());
			sb.append(")\n");
			lineNumber++;
		}

		sb.append(lineNumber);
		sb.append(". Return to the General Store menu");
		System.out.println(sb.toString());

		int selection = GameUtil.getInputNumber();

		lineNumber = 1;

		for (Item item : items) {
			if (selection == lineNumber) {
				if (item.getPurchasePrice() <= farm.getAccount()) {
					farm.setAccount(farm.getAccount() - item.getPurchasePrice());
					farm.addItem(item);
				}
			}
			lineNumber++;
		}

		mainStore(game);

	}

	/**
	 * Displays the main screen of the general store
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void mainStore(Game game) {
		StringBuffer sb = new StringBuffer();
		GameUtil.header(game);
		sb.append("Welcome to the General Store!\n");
		sb.append("What would you like to do next? (1-4):\n\n");
		sb.append("1. Buy and plant Crops\n");
		sb.append("2. Buy Animals\n");
		sb.append("3. Buy Supplies\n");
		sb.append("4. Exit General Store\n");

		System.out.println(sb.toString());
		int selection = GameUtil.getInputNumber();

		switch (selection) {
		case 1:
			buyCrops(game);
			break;
		case 2:
			buyAnimals(game);
			break;
		case 3:
			buySupplies(game);
			break;
		default:
			GameUtil.mainScreen(game);
		}
	}

}
