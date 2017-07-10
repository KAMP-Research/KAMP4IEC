package edu.kit.ipd.sdq.kamp4iec.core;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp4iec.model.ComponentInternalDependencies.ComponentInternalDependenciesFactory;
import edu.kit.ipd.sdq.kamp4iec.model.ComponentInternalDependencies.ComponentInternalDependenciesRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECModelFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.SPSSystem;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECRepositoryFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Repository;
import edu.kit.ipd.sdq.kamp4iec.model.Modificationmarks.AbstractKAMP4IECModificationRepository;


public class ArchitectureVersion extends AbstractArchitectureVersion<AbstractKAMP4IECModificationRepository<?>> {
	private ComponentInternalDependenciesRepository _componentInternalDependencyRepository;
	private Repository _IECRepository;
	private SPSSystem _SPSSystem;
	
	public static class ArchitectureVersionParams{
		public String name;
		public AbstractKAMP4IECModificationRepository<?> modificationMarkRepository;
		public ComponentInternalDependenciesRepository componentInternalDependencyRepository;
		public Repository iecRepository;
		public SPSSystem spsSystem;
	}
	
	public ArchitectureVersion(ArchitectureVersionParams params) {
		super(params.name, params.modificationMarkRepository);
		// Some of the files describing the architecture might not exist; prevent NullPointer
		// in propagation algorithm by setting newly created objects (whose EReferences are
		// instantiated with empty collections, so the algorithm can handle them)
		if (params.componentInternalDependencyRepository == null) {
			_componentInternalDependencyRepository = ComponentInternalDependenciesFactory.eINSTANCE.
					createComponentInternalDependenciesRepository();
		}
		this._componentInternalDependencyRepository = params.componentInternalDependencyRepository;
		if (params.iecRepository == null) {
			_IECRepository = IECRepositoryFactory.eINSTANCE.
					createRepository();
		}
		this._IECRepository = params.iecRepository;
		if (params.spsSystem == null) {
			_SPSSystem = IECModelFactory.eINSTANCE.
					createSPSSystem();
		}
		this._SPSSystem = params.spsSystem;
		
	}
	
	public ComponentInternalDependenciesRepository getComponentInternalDependencyRepository() {
		return _componentInternalDependencyRepository;
	}

	public void setComponentInternalDependencyRepository(
			ComponentInternalDependenciesRepository componentInternalDependencyRepository) {
		this._componentInternalDependencyRepository = componentInternalDependencyRepository;
	}

	public Repository getIECRepository() {
		return _IECRepository;
	}

	public void setIECRepository(Repository iecRepository) {
		this._IECRepository = iecRepository;
	}

	public SPSSystem getSPSSystem() {
		return _SPSSystem;
	}

	public void setSPSSystem(SPSSystem spsSystem) {
		this._SPSSystem = spsSystem;
	}
}
