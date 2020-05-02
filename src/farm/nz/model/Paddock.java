package farm.nz.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Paddock {
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
		this.crop = crop;
	}

	public boolean hasCrop() {
		return (null != crop);
	}

	public int getPaddockID() {
		return paddockID;
	}

}
