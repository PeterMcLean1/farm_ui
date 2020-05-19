package farm.nz;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import farm.nz.model.Crop;
import farm.nz.model.Paddock;
import farm.nz.type.CropType;

public class PaddockModelTest {
	
	private Crop crop = new Crop(CropType.PINE, 2, 12, 4, 6);
	
	@Test
	void paddockSetGetTest() {

		Paddock paddock1 = new Paddock();
		Paddock paddock2 = new Paddock();
		
		assertNotEquals(paddock1.getPaddockID(), paddock2.getPaddockID());
		
		assertTrue(paddock1.hasCrop() == false);
		assertTrue(paddock2.hasCrop() == false);
		
		paddock1.setCrop(crop);

		assertTrue(paddock1.getCrop().getType() == CropType.PINE);
		assertTrue(paddock1.hasCrop() == true);
		assertTrue(paddock2.hasCrop() == false);

	}
	

}
