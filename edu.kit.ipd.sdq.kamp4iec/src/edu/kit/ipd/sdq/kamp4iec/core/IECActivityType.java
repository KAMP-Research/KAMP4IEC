package edu.kit.ipd.sdq.kamp4iec.core;

import edu.kit.ipd.sdq.kamp.workplan.AbstractActivityType;

public enum IECActivityType implements AbstractActivityType {
	ARCHITECTUREMODELDIFF("Architecture-related activity"),
	INTERNALMODIFICATIONMARK("Modification mark"),
	IMPLEMENTATION_SOURCECODE("Source code implementation"),
	IMPLEMENTATION_METADATA("Meta data implementation"),
	BUILDCONFIGURATION("Build configuration"),
	BUILDEXECUTION("Build execution"),
	TESTDEVELOPMENT("Test development"),
	TESTUPDATE("Test update"),
	TESTEXECUTION("Test execution"), 
	RELEASECONFIGURATION("Release configuration"),
	RELEASEEXECUTION("Release execution"),
	DEPLOYMENTCONFIGURATION("Deployment configuration"),
	DEPLOYMENTEXECUTION("Deployment execution"),
	STAFFRESPONSIBILITY("Responsible staff member"),
	HMIMODIFICATIONMARK("Modify user interaction");
	private final String name;
	
	private IECActivityType(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

}
