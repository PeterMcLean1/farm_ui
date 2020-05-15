package farm.nz.model;

import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.SwingPropertyChangeSupport;

public class Paddock {
	private SwingPropertyChangeSupport support = new SwingPropertyChangeSupport(this);
	private static final AtomicInteger count = new AtomicInteger(0);
	private final int paddockID;
	private Crop crop;

	public Paddock() {
		this.paddockID = count.incrementAndGet(); // give the paddock a unique incremental ID
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		Crop crop1 = this.crop;
		this.crop = crop;
		support.firePropertyChange(Farm.CROP, crop1, crop);
	}

	public boolean hasCrop() {
		return (null != crop);
	}

	public int getPaddockID() {
		return paddockID;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

}
