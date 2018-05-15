package edu.kit.ipd.sdq.kamp4iec.core;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

//import de.uka.ipd.sdq.componentInternalDependencies.ComponentInternalDependencyRepository;
import edu.kit.ipd.sdq.kamp.util.FileAndFolderManagement;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationMarksRepository;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationRepository;
import edu.kit.ipd.sdq.kamp4iec.core.IECArchitectureVersion.ArchitectureVersionParams;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModificationRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Repository;

public class IECArchitectureVersionPersistency extends AbstractKAMP4IECArchitectureVersionPersistency<IECArchitectureVersion> {	
	
	private ArchitectureVersionParams archParams = new ArchitectureVersionParams();

	@Override
	public IECArchitectureVersion load(String folderpath, String filename, String versionname) {
		ResourceSet loadResourceSet = new ResourceSetImpl();	
		String internalFieldOfActivityFilePath = filename + "." + FILEEXTENSION_FIELDOFACTIVITYANNOTATIONS;
		String internalIecRepositoryFilePath = filename + "." + FILEEXTENSION_REPOSITORY;
		String internalConfigurationFilePath = filename + "." + FILEEXTENSION_CONFIGURATION;
		String internalModFilePath = filename + "." + FILEEXTENSION_MODIFICATIONMARK;
		
		String internalHMIPath = filename + "." + FILEEXTENSION_HMI;
		String internalHMIModificationsPath = filename + "." + FILEEXTENSION_HMI_MODIFICATIONMARKS;
		
		archParams.name = versionname;
		archParams.fieldOfActivityRepository = (IECFieldOfActivityAnnotationsRepository)loadEmfModelFromResource(folderpath, internalFieldOfActivityFilePath, loadResourceSet);
		archParams.iecRepository = (Repository)loadEmfModelFromResource(folderpath, internalIecRepositoryFilePath, loadResourceSet);
		archParams.configuration = (Configuration)loadEmfModelFromResource(folderpath, internalConfigurationFilePath, loadResourceSet);
		archParams.iecModificationRepository = (IECModificationRepository)loadEmfModelFromResource(folderpath, internalModFilePath, loadResourceSet);
		
		archParams.hmiRepository = (edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Repository)loadEmfModelFromResource(folderpath, internalHMIPath, loadResourceSet);
		archParams.hmiModificationMarksRepository = (HMIModificationMarksRepository)loadEmfModelFromResource(folderpath, internalHMIModificationsPath, loadResourceSet);
		
		return new IECArchitectureVersion(archParams);
	}	
	
	@Override
	public IECArchitectureVersion load(IContainer folder, String versionname) {
		ResourceSet loadResourceSet = new ResourceSetImpl();	
		IFile internalFieldOfActivityFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_FIELDOFACTIVITYANNOTATIONS);
		IFile internalIecRepositoryFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_REPOSITORY);
		IFile internalConfigurationFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_CONFIGURATION);
		IFile internalModFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_MODIFICATIONMARK);
		
		IFile internalHMIFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_HMI);
		IFile internalHMIModificationsFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_HMI_MODIFICATIONMARKS);
		
		archParams.fieldOfActivityRepository = null;
		archParams.iecRepository = null;
		archParams.configuration = null;
		archParams.iecModificationRepository = null;

		archParams.hmiRepository = null;
		archParams.hmiModificationMarksRepository = null;
		
		archParams.name = versionname;
		if (internalFieldOfActivityFile != null && internalFieldOfActivityFile.exists())
			archParams.fieldOfActivityRepository = (IECFieldOfActivityAnnotationsRepository)loadEmfModelFromResource(internalFieldOfActivityFile.getFullPath().toString(), null, loadResourceSet);
		if (internalIecRepositoryFile != null && internalIecRepositoryFile.exists())
			archParams.iecRepository = (Repository)loadEmfModelFromResource(internalIecRepositoryFile.getFullPath().toString(), null, loadResourceSet);
		if (internalConfigurationFile != null && internalConfigurationFile.exists())
			archParams.configuration = (Configuration)loadEmfModelFromResource(internalConfigurationFile.getFullPath().toString(), null, loadResourceSet);
		if (internalModFile != null && internalModFile.exists())
			archParams.iecModificationRepository = (IECModificationRepository)loadEmfModelFromResource(internalModFile.getFullPath().toString(), null, loadResourceSet);
		
		if (internalHMIFile != null && internalHMIFile.exists())
			archParams.hmiRepository = (edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Repository)loadEmfModelFromResource(internalHMIFile.getFullPath().toString(), null, loadResourceSet);
		if (internalHMIModificationsFile != null && internalHMIModificationsFile.exists())
			archParams.hmiModificationMarksRepository = (HMIModificationMarksRepository)loadEmfModelFromResource(internalHMIModificationsFile.getFullPath().toString(), null, loadResourceSet);
		return new IECArchitectureVersion(archParams);
	}
	
}
