package edu.kit.ipd.sdq.kamp4iec.core;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationMarksRepository;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationRepository;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationmarksFactory;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Kamp4hmiModelFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECModelFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.AbstractKAMP4IECModificationRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModificationRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECRepositoryFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Repository;

public class IECArchitectureVersion extends AbstractArchitectureVersion<AbstractKAMP4IECModificationRepository<?>> {
	private Repository _IECRepository;
	private Configuration _configuration;
	private IECFieldOfActivityAnnotationsRepository _fieldOfActivityRepository;
	private edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Repository _hmiRepository;
	private edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationMarksRepository _hmiModificationRepository;
	
	public static class ArchitectureVersionParams{
		public String name;
		public Repository iecRepository;
		public Configuration configuration;
		public IECFieldOfActivityAnnotationsRepository fieldOfActivityRepository;
		public IECModificationRepository iecModificationRepository;
		public edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Repository hmiRepository;
		public HMIModificationMarksRepository hmiModificationMarksRepository;
	}
	
	public IECArchitectureVersion(ArchitectureVersionParams params) {
		super(params.name, params.iecModificationRepository);
		// Some of the files describing the architecture might not exist; prevent NullPointer
		// in propagation algorithm by setting newly created objects (whose EReferences are
		// instantiated with empty collections, so the algorithm can handle them)
		if (params.iecRepository == null) {
			_IECRepository = IECRepositoryFactory.eINSTANCE.
					createRepository();
		}
		this._IECRepository = params.iecRepository;
		if (params.configuration == null) {
			_configuration = IECModelFactory.eINSTANCE.
					createConfiguration();
		}
		this._configuration = params.configuration;
		if (params.fieldOfActivityRepository == null) {
			_fieldOfActivityRepository = IECFieldOfActivityAnnotationsFactory.eINSTANCE.
					createIECFieldOfActivityAnnotationsRepository();
		}
		this._fieldOfActivityRepository = params.fieldOfActivityRepository;	
		if (params.hmiRepository == null) {
			_hmiRepository = Kamp4hmiModelFactory.eINSTANCE.
					createRepository();
		}
		this._fieldOfActivityRepository = params.fieldOfActivityRepository;		
		if (params.hmiRepository == null) {
			_hmiRepository = Kamp4hmiModelFactory.eINSTANCE.createRepository();
		}
		this._hmiRepository = params.hmiRepository;	
		if (params.hmiModificationMarksRepository == null) {
			_hmiModificationRepository = HMIModificationmarksFactory.eINSTANCE.createHMIModificationMarksRepository();
		}
		this._hmiModificationRepository = params.hmiModificationMarksRepository;		
	}

	public Repository getIECRepository() {
		return _IECRepository;
	}

	public void setIECRepository(Repository iecRepository) {
		this._IECRepository = iecRepository;
	}

	public Configuration getConfiguration() {
		return _configuration;
	}

	public void setKonfiguration(Configuration configuration) {
		this._configuration = configuration;
	}

	public IECFieldOfActivityAnnotationsRepository getFieldOfActivityRepository() {
		return _fieldOfActivityRepository;
	}

	public void setFieldOfActivityRepository(IECFieldOfActivityAnnotationsRepository fieldOfActivityRepository) {
		this._fieldOfActivityRepository = fieldOfActivityRepository;
	}

	public edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Repository getHMIRepository() {
		return _hmiRepository;
	}

	public void setHMIRepository(edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Repository _hmiRepository) {
		this._hmiRepository = _hmiRepository;
	}

	public edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationMarksRepository getHMIModificationRepository() {
		return _hmiModificationRepository;
	}

	public void setHMIModificationRepository(
			edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationMarksRepository hmiModificationRepository) {
		this._hmiModificationRepository = hmiModificationRepository;
	}
}
