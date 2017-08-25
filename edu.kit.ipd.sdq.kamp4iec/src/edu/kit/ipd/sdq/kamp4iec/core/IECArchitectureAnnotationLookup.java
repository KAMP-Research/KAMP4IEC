package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.ipd.sdq.kamp.workplan.Activity;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECMetadataFile;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECMetadataFileAggregation;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECSourceFile;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECSourceFileAggregation;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECUnitTestCase;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECUnitTestCaseAggregation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;

public class IECArchitectureAnnotationLookup {

	public static Map<IECComponent, Configuration> lookUpToChangeSoftware(IECArchitectureVersion version,
			Activity activity) {
		Map<IECComponent, Configuration> softwareChangeAffectedParts = new HashMap<IECComponent, Configuration>();
		// TODO
		return softwareChangeAffectedParts;
	}
	
	public static List<IECSourceFile> lookUpIECSourceFilesForComponent(IECArchitectureVersion version, 
			IECComponent component) {
		List<IECSourceFile> sourceFiles = new ArrayList<IECSourceFile>();
		
		if (version.getFieldOfActivityRepository().getDevelopmentArtefactSpecification() != null) {
			for (IECSourceFile sourceFile : version.getFieldOfActivityRepository().
					getDevelopmentArtefactSpecification().getSourceFiles()) {
				if (sourceFile.getComponent()==component) {
					sourceFiles.add(sourceFile);
				}
			}
		}
		
		return sourceFiles;
	}

	public static IECSourceFileAggregation lookUpSourceFileAggregationForComponent(IECArchitectureVersion version, 
			IECComponent component) {
		if (version.getFieldOfActivityRepository().getDevelopmentArtefactSpecification() != null) {
			for (IECSourceFileAggregation sourceFileAggregation : version.getFieldOfActivityRepository().
					getDevelopmentArtefactSpecification().getSourceFileAggregations()) {
				if (sourceFileAggregation.getComponent()==component) {
					return sourceFileAggregation;
				}
			}
		}
		
		return null;
	}
	
	public static List<IECMetadataFile> lookUpMetadataFilesForComponent(IECArchitectureVersion version, 
			IECComponent component) {
		List<IECMetadataFile> metadataFiles = new ArrayList<IECMetadataFile>();
		
		if (version.getFieldOfActivityRepository().getDevelopmentArtefactSpecification() != null) {
			for (IECMetadataFile metadataFile : version.getFieldOfActivityRepository().
					getDevelopmentArtefactSpecification().getMetadataFiles()) {
				if (metadataFile.getComponent()==component) {
					metadataFiles.add(metadataFile);
				}
			}
		}
		
		return metadataFiles;
	}

	public static IECMetadataFileAggregation lookUpMetadataFileAggregationForComponent(IECArchitectureVersion version, 
			IECComponent component) {
		if (version.getFieldOfActivityRepository().getDevelopmentArtefactSpecification() != null) {
			for (IECMetadataFileAggregation metadataFileAggregation : version.getFieldOfActivityRepository().
					getDevelopmentArtefactSpecification().getMetadataFileAggregations()) {
				if (metadataFileAggregation.getComponent()==component) {
					return metadataFileAggregation;
				}
			}
		}
		
		return null;
	}

	public static IECUnitTestCaseAggregation lookUpUnitTestAggregationForIECComponent(
			IECArchitectureVersion version, IECComponent component) {
		if (version.getFieldOfActivityRepository().getTestSpecification() != null) {
			for (IECUnitTestCaseAggregation testAggregation : version.getFieldOfActivityRepository().
					getTestSpecification().getUnitTestCaseAggregations()) {
				if (testAggregation.getProvidedrole()==component) {
					return testAggregation;
				}
			}
		}
		
		return null;
	}

	public static List<IECUnitTestCase> lookUpUnitTestCasesForIECComponent(
			IECArchitectureVersion version,IECComponent component) {
		List<IECUnitTestCase> testCases = new ArrayList<IECUnitTestCase>();
		
		if (version.getFieldOfActivityRepository().getTestSpecification() != null) {
			for (IECUnitTestCase testcase : version.getFieldOfActivityRepository().
					getTestSpecification().getUnitTestCases()) {
				if (testcase.getProvidedrole()==component) {
					testCases.add(testcase);
				}
			}
		}
		
		return testCases;
	}

}
