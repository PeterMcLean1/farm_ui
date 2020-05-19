package farm.nz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import farm.nz.model.Crop;
import farm.nz.type.CropType;

public class CropModelTest {

	@Test
	void cropContructorTest() {

		Crop crop = new Crop(CropType.CORN, 5, 20, 4, 6);
		assertTrue(crop.getType() == CropType.CORN);
		assertTrue(crop.getPurchasePrice() == 5);
		assertTrue(crop.getSalePrice() == 20);
		assertTrue(crop.getMaturity() == 4);
		assertTrue(crop.getResidualValue() == 6);

	}

	@Test
	void cropSetGetTest() {

		Crop crop = new Crop(CropType.CORN, 5, 20, 4, 6);

		crop.setType(CropType.BARLEY);
		crop.setPurchasePrice(4);
		crop.setDayPlanted(3);
		crop.setResidualValue(2);
		crop.setSalePrice(10);
		crop.setMaturity(6);

		assertTrue(crop.getType() == CropType.BARLEY);
		assertTrue(crop.getPurchasePrice() == 4);
		assertTrue(crop.getDayPlanted() == 3);
		assertTrue(crop.getResidualValue() == 2);
		assertTrue(crop.getSalePrice() == 10);
		assertTrue(crop.getMaturity() == 6);

	}

}
