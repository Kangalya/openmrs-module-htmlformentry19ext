/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.htmlformentry19ext;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.HtmlForm;
import org.openmrs.module.htmlformentry.HtmlFormExporter;
import org.openmrs.module.htmlformentry.TestUtil;
import org.openmrs.test.BaseModuleContextSensitiveTest;


/**
 * Test finding dependencies when doing a Metadata Sharing export of a form with this module's tags
 */
public class MetadataSharingExportTest extends BaseModuleContextSensitiveTest {

	@Before
	public void setup() throws Exception {
		executeDataSet("org/openmrs/module/htmlformentry19ext/include/IntegrationTests.xml");
		new HTMLFormEntryExtensions19Activator().started();
	}
	
	@Test
	public void testExportWithEncounterRoleAndProvider() throws Exception {
		HtmlForm form = new HtmlForm();
		form.setXmlData(new TestUtil().loadXmlFromFile("org/openmrs/module/htmlformentry19ext/include/metadataSharingExportTest.xml"));
		
		HtmlFormExporter exporter = new HtmlFormExporter(form);
		HtmlForm formClone = exporter.export(true, true, true, true);
		Collection<OpenmrsObject> dependencies = formClone.getDependencies();
				
		Assert.assertTrue(dependencies.contains(Context.getEncounterService().getEncounterRoleByUuid("e5c5cc92-5283-11e1-bb6a-d975bd577a5e")));
		Assert.assertTrue(dependencies.contains(Context.getEncounterService().getEncounterRoleByUuid("eb75d754-5283-11e1-bb6a-d975bd577a5e")));
		Assert.assertTrue(dependencies.contains(Context.getProviderService().getProviderbyUuid("d2299800-cca9-11e0-9572-0800200c9a66"))); // fix after TRUNK-3053
		Assert.assertTrue(dependencies.contains(Context.getProviderService().getProviderbyUuid("c2299800-cca9-11e0-9572-0800200c9a66"))); // fix after TRUNK-3053
	}
	
}
