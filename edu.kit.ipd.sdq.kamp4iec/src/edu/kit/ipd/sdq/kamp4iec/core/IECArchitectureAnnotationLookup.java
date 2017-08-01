package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.HashMap;
import java.util.Map;

import edu.kit.ipd.sdq.kamp.workplan.Activity;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;

public class IECArchitectureAnnotationLookup {

	public static Map<IECComponent, Configuration> lookUpToChangeSoftware(IECArchitectureVersion version,
			Activity activity) {
		Map<IECComponent, Configuration> softwareChangeAffectedParts = new HashMap<IECComponent, Configuration>();
		// TODO
		return softwareChangeAffectedParts;
	}
}
