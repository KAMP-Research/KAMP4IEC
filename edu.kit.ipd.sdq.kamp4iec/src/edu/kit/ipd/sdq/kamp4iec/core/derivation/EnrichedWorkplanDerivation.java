package edu.kit.ipd.sdq.kamp4iec.core.derivation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;

import edu.kit.ipd.sdq.kamp.workplan.AbstractEnrichedWorkplanDerivation;
import edu.kit.ipd.sdq.kamp.workplan.Activity;
import edu.kit.ipd.sdq.kamp.workplan.BasicActivity;
import edu.kit.ipd.sdq.kamp4iec.core.ArchitectureAnnotationLookup;
import edu.kit.ipd.sdq.kamp4iec.core.ArchitectureVersion;
import edu.kit.ipd.sdq.kamp4iec.core.IECActivityElementType;
import edu.kit.ipd.sdq.kamp4iec.core.IECActivityType;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.FieldOfActivityAnnotationsRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Konfiguration;

public class EnrichedWorkplanDerivation implements AbstractEnrichedWorkplanDerivation<ArchitectureVersion> {
	@Override
	public List<Activity> deriveEnrichedWorkplan(ArchitectureVersion baseVersion, ArchitectureVersion targetVersion,
			List<Activity> baseActivityList) {
		List<Activity> result = new ArrayList<Activity>();
		deriveSoftwareChangeActivities(baseVersion, targetVersion, result);
		return result;
	}

	private void deriveSoftwareChangeActivities(ArchitectureVersion baseVersion, ArchitectureVersion targetVersion, 
			List<Activity> baseActivityList) {
		Map<IECComponent, Konfiguration> softwareSeedChanges = new HashMap<IECComponent, Konfiguration>();
		for(Activity activity : baseActivityList){
			ArchitectureVersion version = determineRelevantArchitectureVersion(activity, baseVersion, targetVersion);
			softwareSeedChanges.putAll(ArchitectureAnnotationLookup.lookUpToChangeSoftware(
					version, activity));
		}
	}

	private static LinkedList<String> createCausingElementList(Activity activity) {
		LinkedList<String> newList = new LinkedList<String>();
		newList.addFirst(activity.getElementType().getName() + " \"" + activity.getElementName() + "\"");
		return newList;
	}

	private static <T extends ArchitectureVersion> T determineRelevantArchitectureVersion(Activity activity,
			T baseVersion, T targetVersion) {
		if (activity.getBasicActivity() == BasicActivity.REMOVE) {
			return baseVersion;
		} else {
			return targetVersion;
		}
	}
}