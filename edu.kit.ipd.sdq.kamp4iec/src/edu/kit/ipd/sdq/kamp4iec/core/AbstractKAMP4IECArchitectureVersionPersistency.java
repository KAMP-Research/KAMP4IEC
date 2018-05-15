package edu.kit.ipd.sdq.kamp4iec.core;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

//import de.uka.ipd.sdq.componentInternalDependencies.ComponentInternalDependenciesPackage;
import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersionPersistency;

public abstract class AbstractKAMP4IECArchitectureVersionPersistency<T extends IECArchitectureVersion> 
		extends AbstractArchitectureVersionPersistency<T> {
	
	public static final String FILEEXTENSION_REPOSITORY = "iecrepository";
	public static final String FILEEXTENSION_CONFIGURATION = "iecmodel";
	public static final String FILEEXTENSION_FIELDOFACTIVITYANNOTATIONS = "iecfieldofactivityannotations";
	public static final String FILEEXTENSION_COMPONENTINTERNALDEPENDENCIES = "componentinternaldependencies";
	public static final String FILEEXTENSION_MODIFICATIONMARK = "iecmodificationmarks";
	public static final String FILEEXTENSION_HMI = "kamp4hmimodel";
	public static final String FILEEXTENSION_HMI_MODIFICATIONMARKS = "hmimodificationmarks";

	@Override
	public void save(String targetDirectoryPath, String filename, T version) {
		saveKAMPModels(targetDirectoryPath, filename, version);
	}

	@Override
	public void saveModificationMarkFile(String targetDirectoryPath, String filename, T version) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		String internalModFilePath = filename + "." + FILEEXTENSION_MODIFICATIONMARK;	
		if (version.getModificationMarkRepository()!=null)
			saveEmfModelToResource(version.getModificationMarkRepository(), targetDirectoryPath, internalModFilePath, resourceSet);	
		String internalHMIModFilePath = filename + "." + FILEEXTENSION_HMI_MODIFICATIONMARKS;	
		if (version.getHMIModificationRepository()!=null)
			saveEmfModelToResource(version.getHMIModificationRepository(), targetDirectoryPath, internalHMIModFilePath, resourceSet);		
		
	}
	
	public static void saveKAMPModels(String targetDirectoryPath, String filename, IECArchitectureVersion version) {
		ResourceSet resourceSet = new ResourceSetImpl();
		String repositoryfilePath = filename + "." + FILEEXTENSION_REPOSITORY;
		String configurationFilePath = filename + "."+ FILEEXTENSION_CONFIGURATION;
		String internalModFilePath = filename + "." + FILEEXTENSION_MODIFICATIONMARK;
		String fieldOfActivityRepositoryFilePath = filename + "."+ FILEEXTENSION_FIELDOFACTIVITYANNOTATIONS;
		String hmiRepositoryFilePath = filename + "."+ FILEEXTENSION_HMI;
		String hmiModificationMarksFilePath = filename + "."+ FILEEXTENSION_HMI_MODIFICATIONMARKS;
		
		if (version.getIECRepository()!=null)
			saveEmfModelToResource(version.getIECRepository(), targetDirectoryPath, repositoryfilePath, resourceSet);		
		if (version.getConfiguration()!=null)
			saveEmfModelToResource(version.getConfiguration(), targetDirectoryPath, configurationFilePath, resourceSet);		
		if (version.getModificationMarkRepository()!=null)
			saveEmfModelToResource(version.getModificationMarkRepository(), targetDirectoryPath, internalModFilePath, resourceSet);		
		if (version.getFieldOfActivityRepository()!=null)
			saveEmfModelToResource(version.getFieldOfActivityRepository(), targetDirectoryPath, fieldOfActivityRepositoryFilePath, resourceSet);
		if (version.getHMIRepository()!=null)
			saveEmfModelToResource(version.getHMIRepository(), targetDirectoryPath, hmiRepositoryFilePath, resourceSet);
		if (version.getHMIModificationRepository()!=null)
			saveEmfModelToResource(version.getHMIModificationRepository(), targetDirectoryPath, hmiModificationMarksFilePath, resourceSet);
	}

}
