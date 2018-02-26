package edu.kit.ipd.sdq.kamp4iec.core;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

//import de.uka.ipd.sdq.componentInternalDependencies.ComponentInternalDependencyRepository;
import edu.kit.ipd.sdq.kamp.util.FileAndFolderManagement;
import edu.kit.ipd.sdq.kamp4iec.core.IECArchitectureVersion.ArchitectureVersionParams;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.AbstractKAMP4IECModificationRepository;
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
		
		archParams.name = versionname;
		archParams.fieldOfActivityRepository = (IECFieldOfActivityAnnotationsRepository)loadEmfModelFromResource(folderpath, internalFieldOfActivityFilePath, loadResourceSet);
		archParams.iecRepository = (Repository)loadEmfModelFromResource(folderpath, internalIecRepositoryFilePath, loadResourceSet);
		archParams.configuration = (Configuration)loadEmfModelFromResource(folderpath, internalConfigurationFilePath, loadResourceSet);
		archParams.modificationMarkRepository = (AbstractKAMP4IECModificationRepository<?>)loadEmfModelFromResource(folderpath, internalModFilePath, loadResourceSet);
		
		return new IECArchitectureVersion(archParams);
	}	
	
	@Override
	public IECArchitectureVersion load(IContainer folder, String versionname) {
		ResourceSet loadResourceSet = new ResourceSetImpl();	
		IFile internalFieldOfActivityFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_FIELDOFACTIVITYANNOTATIONS);
		IFile internalIecRepositoryFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_REPOSITORY);
		IFile internalConfigurationFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_CONFIGURATION);
		IFile internalModFile = FileAndFolderManagement.retrieveFileWithExtension(folder, FILEEXTENSION_MODIFICATIONMARK);
		
		archParams.fieldOfActivityRepository = null;
		archParams.iecRepository = null;
		archParams.configuration = null;
		archParams.modificationMarkRepository = null;
		
		archParams.name = versionname;
		if (internalFieldOfActivityFile != null && internalFieldOfActivityFile.exists())
			archParams.fieldOfActivityRepository = (IECFieldOfActivityAnnotationsRepository)loadEmfModelFromResource(internalFieldOfActivityFile.getFullPath().toString(), null, loadResourceSet);
		if (internalIecRepositoryFile != null && internalIecRepositoryFile.exists())
			archParams.iecRepository = (Repository)loadEmfModelFromResource(internalIecRepositoryFile.getFullPath().toString(), null, loadResourceSet);
		if (internalConfigurationFile != null && internalConfigurationFile.exists())
			archParams.configuration = (Configuration)loadEmfModelFromResource(internalConfigurationFile.getFullPath().toString(), null, loadResourceSet);
		if (internalModFile != null && internalModFile.exists())
			archParams.modificationMarkRepository = (AbstractKAMP4IECModificationRepository<?>)loadEmfModelFromResource(internalModFile.getFullPath().toString(), null, loadResourceSet);
		return new IECArchitectureVersion(archParams);
	}
	
}
