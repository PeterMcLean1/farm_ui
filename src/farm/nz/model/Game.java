package farm.nz.model;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

public class Game {
	public static final String ACTION = "action";
	public static final String DAY = "day";
	public static final String ACCOUNT = "account";

	private int account;
	private int daysToPlay;
	private int currentDay = 1;
	private int actionCount = 0;
	private int maxDailyActions = 2;
	private Farm farm;

	private SwingPropertyChangeSupport support = new SwingPropertyChangeSupport(this);

	public Game(Farm farm) {
		this.farm = farm;
	}

	public int getActionCount() {
		return actionCount;
	}

	public int getCurrentDay() {
		return currentDay;
	}

	public int getDaysToPlay() {
		return daysToPlay;
	}

	public Farm getFarm() {
		return farm;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		int oldValue = this.account;
		int newValue = account;
		this.account = account;
		support.firePropertyChange(ACCOUNT, oldValue, newValue);
	}

	public int getMaxDailyActions() {
		return maxDailyActions;
	}

	/**
	 * Used to determine if the player has more daily actions
	 * 
	 * @return Does this player have more daily actions today
	 */
	public boolean hasActions() {
		return actionCount < maxDailyActions;
	}

	public void incrementCurrentDay() {

		this.currentDay++;

		support.firePropertyChange(DAY, 1, 2);
	}

	public void incrementActionCount() {

		this.actionCount++;

		support.firePropertyChange(ACTION, 1, 2);
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
		support.firePropertyChange(ACTION, 1, 2);
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}

	public void setDaysToPlay(int daysToPlay) {
		this.daysToPlay = daysToPlay;

	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public void setMaxDailyActions(int maxDailyActions) {
		this.maxDailyActions = maxDailyActions;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

}
