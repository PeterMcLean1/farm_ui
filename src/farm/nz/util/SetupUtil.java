package farm.nz.util;

import java.util.Scanner;

import farm.nz.model.Farm;
import farm.nz.model.Farmer;
import farm.nz.model.Game;
import farm.nz.type.FarmType;

/**
 * Contains methods to print setup options to console
 * 
 * @author peter.mclean
 *
 */
public class SetupUtil {
	static Scanner keyboard = new Scanner(System.in);

	/**
	 * Interface used to enter age to farmer player
	 * 
	 * @param farmer
	 */
	public static void setAge(Farmer farmer) {
		boolean looper = true;
		while (looper) {
			GameUtil.clearScreen();
			System.out.println("Please enter your age (1-100):");
			int age = GameUtil.getInputNumber();
			if (100 < age || 1 > age) {
				continue;
			}

			System.out.println("You have entered " + age + ", is this correct? (Y/N)");
			String response = keyboard.next();
			if (response.equalsIgnoreCase("y")) {
				farmer.setAge(age);
				looper = false;
			}
		}
	}

	public static void setFarmName(Farm farm) {
		boolean looper = true;
		while (looper) {
			GameUtil.clearScreen();
			System.out.println("Your farm name must be less than 25 characters");
			System.out.println("Please enter a name for your farm:");
			String name = keyboard.next();
			if (25 < name.length()) {
				continue;
			}
			System.out.println("Your farm name is " + name + ", is this correct? (Y/N)");
			String response = keyboard.next();
			if (response.equalsIgnoreCase("y")) {
				farm.setName(name);
				looper = false;
			}
		}
	}

	public static void setFarmType(Farm farm) {
		StringBuffer sb = new StringBuffer();
		sb.append("Please select your farm type (1-4):\n\n");
		sb.append("1. " + FarmType.FLAT.getDisplay() + "\n");
		sb.append("2. " + FarmType.HILL.getDisplay() + "\n");
		sb.append("3. " + FarmType.RIVER.getDisplay() + "\n");
		sb.append("4. " + FarmType.FOREST.getDisplay());
		boolean looper = true;

		while (looper) {
			GameUtil.clearScreen();
			System.out.println(sb.toString());
			int selection = GameUtil.getInputNumber();

			switch (selection) {
			case 1:
				farm.setType(FarmType.FLAT);
				break;
			case 2:
				farm.setType(FarmType.HILL);
				break;
			case 3:
				farm.setType(FarmType.RIVER);
				break;
			case 4:
				farm.setType(FarmType.FOREST);
				break;
			default:
				farm.setType(FarmType.FLAT);
			}

			System.out.println("Your farm type is " + farm.getType().getDisplay() + ", is this correct? (Y/N)");
			String response = keyboard.next();
			if (response.equalsIgnoreCase("y")) {
				looper = false;
			}
		}

	}

	/**
	 * Allows user to set the number of game days they wish to play
	 * 
	 * @param game Used to track game instance progress
	 */
	public static void setGameDays(Game game) {
		boolean looper = true;
		int days = 0;

		while (looper) {
			GameUtil.clearScreen();
			System.out.println("Please enter the number of days you want to play (5-10):");
			days = GameUtil.getInputNumber();
			if (days < 5 || days > 10) {
				continue;
			}

			System.out.println("Play for " + days + " days? (Y/N)");
			String response = keyboard.next();
			if (response.equalsIgnoreCase("y")) {
				game.setDaysToPlay(days);
				looper = false;
			}
		}

	}

	public static void setName(Farmer farmer) {
		boolean looper = true;
		System.out.println("Welcome farmer!");
		while (looper) {
			GameUtil.clearScreen();
			System.out.println("Your name must be 3-15 characters, and contain no special characters");
			System.out.println("Please enter a name for your farmer:");
			String name = keyboard.next();
			// regex validate name = no special characters 3-15 long
			if (!name.matches("[A-Za-z0-9]{3,15}")) {
				continue;
			}
			System.out.println("The name you entered is " + name + ", is this correct? (Y/N)");
			String response = keyboard.next();
			if (response.equalsIgnoreCase("y")) {
				farmer.setName(name);
				looper = false;
			}
		}
	}

}
