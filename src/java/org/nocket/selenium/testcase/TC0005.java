package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0005 - Checkbox */
public class TC0005 extends SeleniumTestCase {

	private String checkboxID = BootstrapPlainTestData.CKB_ID;
	private String checkboxErrorID = BootstrapPlainTestData.CKB_ERROR_ID;
	private String submitXpath = BootstrapPlainTestData.BUTTON_SUBMIT_XPATH;

	@BeforeClass
	public static void setUpClass() {
		driver = getFirefoxWebDriverInstance();
	}

	@Before
	public void setUp() throws Exception {
		getFirefoxWindow(BootstrapPlainTestData.SITE_URL);
	}

	@Test
	public void testUnchecked() {
		deselectCheckbox(checkboxID);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(checkboxErrorID, "The check box must be checked");
	}

	@Test
	public void testChecked() {
		selectCheckbox(checkboxID);
		clickButtonByXpath(submitXpath);
		assertNoError(checkboxErrorID);
	}
}
