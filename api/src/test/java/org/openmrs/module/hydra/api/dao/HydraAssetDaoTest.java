package org.openmrs.module.hydra.api.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.DuplicateIdentifierException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.api.HydraBaseTest;
import org.openmrs.module.hydra.model.workflow.HydramoduleAsset;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.workflow.HydramoduleAssetType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HydraAssetDaoTest extends HydraBaseTest {

	@Autowired
	HydraDaoImpl dao;

	@Autowired
	DbSessionFactory sessionFactory;

	HydramoduleAssetCategory apparel, woodcraft, magicGear;

	HydramoduleAssetType cloak, winterWear, emblem, broomStick, wand;

	List<HydramoduleAssetType> apparelTypes, woodcraftTypes;

	HydramoduleAsset nimbus2000, firebolt, peverellCloak, ravenclawRing, hogwartzPendant;

	@Before
	public void runBeforeEachTest() throws Exception {
		super.initTestData();
		initAssets();
	}

	private void initAssets() throws ParseException {
		apparel = new HydramoduleAssetCategory();
		apparel.setAssetCategoryId(1);
		apparel.setName("Apparel");
		apparel.setDescription("");
		apparel.setRetired(Boolean.FALSE);
		apparel.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120001");

		woodcraft = new HydramoduleAssetCategory();
		woodcraft.setAssetCategoryId(2);
		woodcraft.setName("Woodcraft");
		woodcraft.setDescription("Wood items like wand and broom stick");
		woodcraft.setRetired(Boolean.TRUE);
		woodcraft.setDateRetired(dateFormatter.parse("2020-06-02 00:00:00"));
		woodcraft.setRetireReason("Created a more general category instead");
		woodcraft.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120002");

		magicGear = new HydramoduleAssetCategory();
		magicGear.setAssetCategoryId(3);
		magicGear.setName("Magic Gear");
		magicGear.setDescription("All magic tools and apparatus");
		magicGear.setRetired(Boolean.FALSE);
		magicGear.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120003");

		cloak = new HydramoduleAssetType();
		cloak.setAssetTypeId(1);
		cloak.setAssetCategory(apparel);
		cloak.setName("Cloak");
		cloak.setRetired(Boolean.FALSE);
		cloak.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120004");

		winterWear = new HydramoduleAssetType();
		winterWear.setAssetTypeId(2);
		winterWear.setAssetCategory(apparel);
		winterWear.setName("Winter wear");
		winterWear.setRetired(Boolean.FALSE);
		winterWear.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120005");

		emblem = new HydramoduleAssetType();
		emblem.setAssetTypeId(3);
		emblem.setAssetCategory(apparel);
		emblem.setName("Emblem");
		emblem.setRetired(Boolean.FALSE);
		emblem.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120006");

		broomStick = new HydramoduleAssetType();
		broomStick.setAssetTypeId(4);
		broomStick.setAssetCategory(magicGear);
		broomStick.setName("Broom Stick");
		broomStick.setRetired(Boolean.FALSE);
		broomStick.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120007");

		wand = new HydramoduleAssetType();
		wand.setAssetTypeId(5);
		wand.setAssetCategory(magicGear);
		wand.setName("Wand");
		wand.setRetired(Boolean.FALSE);
		wand.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120008");

		apparelTypes = Arrays.asList(cloak, winterWear, emblem);
		woodcraftTypes = Arrays.asList(broomStick, wand);

		nimbus2000 = new HydramoduleAsset();
		nimbus2000.setAssetId(1);
		nimbus2000.setAssetType(broomStick);
		nimbus2000.setName("Nimbus 2000");
		nimbus2000.setDescription("Harry's old broom stick");
		nimbus2000.setReferenceId("NIMBUS2000");
		nimbus2000.setCapitalValue("900");
		nimbus2000.setFixedAsset(Boolean.TRUE);
		nimbus2000.setRetired(Boolean.FALSE);
		nimbus2000.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120009");

		firebolt = new HydramoduleAsset();
		firebolt.setAssetId(2);
		firebolt.setAssetType(broomStick);
		firebolt.setName("Fire bolt");
		firebolt.setDescription("Harry's goto broom stick");
		firebolt.setReferenceId("FIREBOLT");
		firebolt.setCapitalValue("1300");
		firebolt.setFixedAsset(Boolean.TRUE);
		firebolt.setRetired(Boolean.FALSE);
		firebolt.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120010");

		peverellCloak = new HydramoduleAsset();
		peverellCloak.setAssetId(3);
		peverellCloak.setAssetType(cloak);
		peverellCloak.setName("Peverell Cloak");
		peverellCloak.setDescription("Harry's goto broom stick");
		peverellCloak.setReferenceId("PEVERELLCLOAK");
		peverellCloak.setCapitalValue("800000");
		peverellCloak.setFixedAsset(Boolean.TRUE);
		peverellCloak.setRetired(Boolean.FALSE);
		peverellCloak.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120011");

		ravenclawRing = new HydramoduleAsset();
		ravenclawRing.setAssetId(4);
		ravenclawRing.setAssetType(emblem);
		ravenclawRing.setName("Ravenclaw Ring");
		ravenclawRing.setDescription("Silver ring of house Ravenclaw of Hogwatz school");
		ravenclawRing.setReferenceId("RAVENRING");
		ravenclawRing.setCapitalValue("15");
		ravenclawRing.setFixedAsset(Boolean.TRUE);
		ravenclawRing.setRetired(Boolean.FALSE);
		ravenclawRing.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120012");

		hogwartzPendant = new HydramoduleAsset();
		hogwartzPendant.setAssetId(5);
		hogwartzPendant.setAssetType(emblem);
		hogwartzPendant.setName("Hogwarts Pendant");
		hogwartzPendant.setDescription("Hogwatz school crest pendant");
		hogwartzPendant.setReferenceId("HOGWARTZPENDANT");
		hogwartzPendant.setCapitalValue("25");
		hogwartzPendant.setFixedAsset(Boolean.TRUE);
		hogwartzPendant.setRetired(Boolean.FALSE);
		hogwartzPendant.setUuid("aaaaaaaa-bbbb-cccc-dddd-202006120013");
	}

	/* Asset Categories */
	@Test
	public void shouldGetHydramoduleActiveAssetCategories() {
		Context.clearSession();
		List<HydramoduleAssetCategory> active = Arrays.asList(apparel, magicGear);
		List<HydramoduleAssetCategory> list = dao.getAllAssetCategories(false);
		assertTrue(list.size() == active.size());
		assertThat(list, Matchers.not(Matchers.hasItem(woodcraft)));
	}

	@Test
	public void shouldGetHydramoduleAssetCategories() {
		Context.clearSession();
		List<HydramoduleAssetCategory> active = Arrays.asList(apparel, magicGear, woodcraft);
		List<HydramoduleAssetCategory> list = dao.getAllAssetCategories(true);
		assertTrue(list.size() == active.size());
	}

	@Test
	public void shouldGetHydramoduleAssetCategoryById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetCategoryByName() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetCategoryByUuid() {
		Context.clearSession();
		assertEquals(apparel, dao.getAssetCategory("aaaaaaaa-bbbb-cccc-dddd-202006120001"));
	}

	@Test
	public void shouldSaveHydramoduleAssetCategory() {
		HydramoduleAssetCategory assetCategory = new HydramoduleAssetCategory();
		assetCategory.setName("Test Asset Category");
		assetCategory.setDescription(assetCategory.getName());
		assetCategory.setUuid(UUID.randomUUID().toString());
		assetCategory = dao.saveAssetCategory(assetCategory);
		assertThat(assetCategory, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	/**
	 * Should delete the object along with all dependent objects
	 */
	@Test
	public void shouldPurgeHydramoduleAssetCategoryCompletely() {
		fail("Not yet implemented");
	}

	/**
	 * Should first set the value of dependent objects to UNKNOWN Category and then delete
	 */
	@Test
	public void shouldPurgeHydramoduleAssetCategorySafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireHydramoduleAssetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireHydramoduleAssetCategory() {
		fail("Not yet implemented");
	}

	/* Asset Types */
	@Test
	public void shouldGetHydramoduleActiveAssetTypes() {
		Context.clearSession();
		List<HydramoduleAssetType> active = Arrays.asList(cloak, winterWear, emblem, broomStick, wand);
		List<HydramoduleAssetType> list = dao.getAllAssetTypes(false);
		assertTrue(list.size() == active.size());
	}

	@Test
	public void shouldGetHydramoduleAssetTypes() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetTypeById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetTypeByName() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetTypeByUuid() {
		Context.clearSession();
		assertEquals(broomStick, dao.getAssetCategory("aaaaaaaa-bbbb-cccc-dddd-202006120007"));
	}

	@Test
	public void shouldGetHydramoduleAssetTypesByCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleAssetType() {
		HydramoduleAssetType assetType = new HydramoduleAssetType();
		assetType.setName("Test Asset Type");
		assetType.setDescription(assetType.getName());
		assetType.setUuid(UUID.randomUUID().toString());
		assetType = dao.saveAssetType(assetType);
		assertThat(assetType, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	@Test
	public void shouldPurgeHydramoduleAssetTypeCompletely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeHydramoduleAssetTypeSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireHydramoduleAssetType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireHydramoduleAssetType() {
		fail("Not yet implemented");
	}

	/* Assets */
	@Test
	public void shouldNOTGetAllHydramoduleAssets() {
		Context.clearSession();
		List<HydramoduleAssetType> active = Arrays.asList(cloak, winterWear, emblem, broomStick, wand);
		List<HydramoduleAssetType> list = dao.getAllAssetTypes(false);
		assertTrue(list.size() == active.size());
	}

	@Test
	public void shouldGetHydramoduleAssetById() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetsByName() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetByUuid() {
		Context.clearSession();
		assertEquals(nimbus2000, dao.getAssetCategory("aaaaaaaa-bbbb-cccc-dddd-202006120009"));
	}

	@Test
	public void shouldGetHydramoduleAssetsByReferenceId() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldGetHydramoduleAssetsByType() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldSaveHydramoduleAsset() {
		HydramoduleAsset asset = new HydramoduleAsset();
		asset.setAssetType(wand);
		asset.setName("Test Asset Type");
		asset.setDescription(asset.getName());
		asset.setReferenceId("TESTASSET");
		asset.setCapitalValue("1000");
		asset.setUuid(UUID.randomUUID().toString());
		asset.setFixedAsset(Boolean.TRUE);
		asset.setRetired(Boolean.FALSE);
		asset.setUuid(UUID.randomUUID().toString());
		asset = dao.saveAsset(asset);
		assertThat(asset, Matchers.hasProperty("dateCreated", Matchers.notNullValue()));
	}

	@Test(expected = DuplicateIdentifierException.class)
	public void shouldNOTSaveHydramoduleAssetWithDuplicateReferenceIdForSameAssetType() {
		HydramoduleAsset asset = new HydramoduleAsset();
		asset.setAssetType(wand);
		asset.setName("Test Asset Type");
		asset.setDescription(asset.getName());
		asset.setReferenceId("TESTASSET");
		asset.setCapitalValue("1000");
		asset.setUuid(UUID.randomUUID().toString());
		asset.setFixedAsset(Boolean.TRUE);
		asset.setRetired(Boolean.FALSE);
		asset.setUuid(UUID.randomUUID().toString());
		dao.saveAsset(asset);

		HydramoduleAsset asset2 = new HydramoduleAsset();
		asset2.setAssetType(wand);
		asset2.setName("Test Asset Type 2");
		asset2.setDescription(asset2.getName());
		asset2.setReferenceId("TESTASSET");
		asset2.setCapitalValue("2000");
		asset2.setUuid(UUID.randomUUID().toString());
		asset2.setFixedAsset(Boolean.TRUE);
		asset2.setRetired(Boolean.FALSE);
		asset2.setUuid(UUID.randomUUID().toString());
		dao.saveAsset(asset2);
	}

	@Test(expected = DAOException.class)
	public void shouldNOTSaveHydramoduleAssetForRetiredAssetType() {
		HydramoduleAsset asset = new HydramoduleAsset();
		asset.setAssetType(wand);
		asset.setName("Test Asset Type");
		asset.setDescription(asset.getName());
		asset.setReferenceId("TESTASSET");
		asset.setCapitalValue("1000");
		asset.setUuid(UUID.randomUUID().toString());
		asset.setFixedAsset(Boolean.TRUE);
		asset.setRetired(Boolean.FALSE);
		asset.setUuid(UUID.randomUUID().toString());
		dao.saveAsset(asset);
	}

	@Test
	public void shouldPurgeHydramoduleAssetCompletely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldPurgeHydramoduleAssetSafely() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldRetireHydramoduleAsset() {
		fail("Not yet implemented");
	}

	@Test
	public void shouldUnretireHydramoduleAsset() {
		fail("Not yet implemented");
	}
}
