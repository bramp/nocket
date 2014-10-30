package org.nocket.selenium.testcase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nocket.selenium.BootstrapPlainTestData;
import org.nocket.selenium.SeleniumTestCase;

/** TestCase TC0002 - Numberfield */
public class TC0002 extends SeleniumTestCase {

	private String numberfieldID = BootstrapPlainTestData.NF_ID;
	private String numberfieldErrorID = BootstrapPlainTestData.NF_ERROR_ID;
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
	public void testNotNullError() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NULL);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(numberfieldErrorID, "Bitte tragen Sie einen Wert im Feld 'Numberfield' ein.");
	}

	@Test
	public void testSizeMinError() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_VALUE_MIN_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(numberfieldErrorID, "Der Wert " + BootstrapPlainTestData.NF_VALUE_MIN_FALSE
				+ " im Feld 'Numberfield' muss gr��er sein als '18'.");
	}

	@Test
	public void testSizeMin() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_VALUE_MIN_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(numberfieldErrorID);
	}

	@Test
	public void testSizeMaxError() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_VALUE_MAX_FALSE);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(numberfieldErrorID, "Der Wert " + BootstrapPlainTestData.NF_VALUE_MAX_FALSE
				+ " im Feld 'Numberfield' muss kleiner sein als '75'.");
	}

	@Test
	public void testSizeMax() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_VALUE_MAX_TRUE);
		clickButtonByXpath(submitXpath);
		assertNoError(numberfieldErrorID);
	}

	@Test
	public void testNegatives() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_PATTERN_NEGATIVE);
		clickButtonByXpath(submitXpath);
		assertErrorMessage(numberfieldErrorID, "Der Wert " + BootstrapPlainTestData.NF_PATTERN_NEGATIVE
				+ " im Feld 'Numberfield' muss gr��er sein als '18'.");
	}

	@Test
	public void testPlus() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_PATTERN_PLUS);
		clickButtonByXpath(submitXpath);
		// <p class="error"> visible, but no error message implemented.
		assertErrorMessage(numberfieldErrorID, BootstrapPlainTestData.NULL);
	}

	@Test
	public void testAlphanumeric() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_PATTERN_ALPHANUMERIC);
		clickButtonByXpath(submitXpath);
		// <p class="error"> visible, but no error message implemented.
		assertErrorMessage(numberfieldErrorID, BootstrapPlainTestData.NULL);
	}

	@Test
	public void testDecimalDot() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_PATTERN_DOT);
		clickButtonByXpath(submitXpath);
		// <p class="error"> visible, but no error message implemented.
		assertErrorMessage(numberfieldErrorID, BootstrapPlainTestData.NULL);
	}

	@Test
	public void testDecimalComma() {
		setFieldValueByID(numberfieldID, BootstrapPlainTestData.NF_PATTERN_COMMA);
		clickButtonByXpath(submitXpath);
		// <p class="error"> visible, but no error message implemented.
		assertErrorMessage(numberfieldErrorID, BootstrapPlainTestData.NULL);
	}
}
