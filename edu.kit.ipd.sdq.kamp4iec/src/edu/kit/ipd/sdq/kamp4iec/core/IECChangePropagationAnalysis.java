package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.kamp.architecture.ArchitectureModelLookup;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractSeedModifications;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.ChangePropagationStep;
import edu.kit.ipd.sdq.kamp.propagation.AbstractChangePropagationAnalysis;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIChangePropagationDueToSoftwareDependency;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationMarksRepository;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationRepository;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationmarksFactory;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModifyActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModifySystemStep;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMISeedModifications;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIStepModification;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.ActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.HMIElement;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.SystemStep;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Function;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECAbstractMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECAbstractProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Identifier;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECChangePropagationDueToDataDependency;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModificationRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModificationmarksFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModificationmarksPackage;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyAbstractMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyAbstractProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyConfiguration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyFunction;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyFunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyGlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyProgram;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECSeedModifications;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.impl.IECModificationRepositoryImpl;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.impl.IECModificationmarksFactoryImpl;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.impl.IECSeedModificationsImpl;

/**
 * The change propagation analysis of KAPS
 * 1. determines a seed population of affected components (resp. provided roles)
 * 2. calculates in iterations:
 *    a) inter-component propagation
 *    b) intra-component propagation
 * 3. generates internal modification marks for affected elements
 * 
 * - elements which were already part of a seed population are not further investigated
 * 
 * 
 * @author raetz
 *
 */
public class IECChangePropagationAnalysis implements AbstractChangePropagationAnalysis<IECArchitectureVersion> {

	private Collection<IECComponent> seedModifications = new HashSet<IECComponent>();
	private Collection<HMIElement> hmiSeedModifications = new HashSet<HMIElement>();
	private IECChangePropagationDueToDataDependency changePropagationDueToDataDependencies;
	private HMIChangePropagationDueToSoftwareDependency changePropagationDueToSoftwareDependency;

	@Override
	public void runChangePropagationAnalysis(IECArchitectureVersion version) {
		// Setup
		this.prepareAnalysis(version);
		
		// Calculate
		this.calculateChangePropagationDueToDataDependencies(version);		
		
		this.postAnalysis(version);
		
	}
	
	private void prepareAnalysis(IECArchitectureVersion version) {
		this.setIECChangePropagationDueToDataDependencies(IECModificationmarksFactory.eINSTANCE.createIECChangePropagationDueToDataDependency());
		this.setHMIChangePropagationDueToSoftwareDependencies(HMIModificationmarksFactory.eINSTANCE.createHMIChangePropagationDueToSoftwareDependency());
		

		if(version.getModificationMarkRepository() != null && version.getModificationMarkRepository().getChangePropagationSteps() != null) {			
			version.getModificationMarkRepository().getChangePropagationSteps().add(
					this.getIECChangePropagationDueToDataDependencies());
		}
		if(version.getHMIRepository() != null && version.getHMIModificationRepository() != null) {
			if(version.getHMIModificationRepository().getChangePropagationSteps() != null) {
				//remove old modifications
				for (Iterator<ChangePropagationStep> it = version.getHMIModificationRepository().getChangePropagationSteps().iterator(); it.hasNext(); ) {
					ChangePropagationStep step = it.next();
					it.remove();
				}
				version.getHMIModificationRepository().getChangePropagationSteps().add(
						this.getHMIChangePropagationDueToSoftwareDependency());
			}
		}
		
		markSeedModifications(version);
	}
	
	protected void markSeedModifications(IECArchitectureVersion version) {
		//If there is no seedmodifications, create a new one
		if(version.getModificationMarkRepository().getSeedModifications() == null) {
			IECModificationRepositoryImpl repo = (IECModificationRepositoryImpl) version.getModificationMarkRepository();
			repo.setSeedModifications(IECModificationmarksFactory.eINSTANCE.createIECSeedModifications());
		}
		//add existing seedmodifications to local list of seedmods
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, GlobalVariable.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, FunctionBlock.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, Function.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECProperty.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECMethod.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECAbstractProperty.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECAbstractMethod.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECInterface.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, Program.class));
		seedModifications.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, Configuration.class));
		//add local seedmodifications to modificationmarks model
		IECSeedModifications seedMods = version.getModificationMarkRepository().getSeedModifications();
		for(IECComponent component : seedModifications) {
			if(component instanceof GlobalVariable) {
				if(!modificationListContainsElement(seedMods.getGlobalVariableModifications(), (GlobalVariable)component))
				seedMods.getGlobalVariableModifications().add(IECModificationFactory.createIECModification((GlobalVariable) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof FunctionBlock) {
				if(!modificationListContainsElement(seedMods.getFunctionBlockModifications(), (FunctionBlock)component))
				seedMods.getFunctionBlockModifications().add(IECModificationFactory.createIECModification((FunctionBlock) component, new HashSet<IECComponent>(), false));
			} else if (component instanceof Function) {
				seedMods.getFunctionModifications().add(IECModificationFactory.createIECModification((Function) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof IECProperty) {
				if(!modificationListContainsElement(seedMods.getPropertyModifications(), (IECProperty)component))
				seedMods.getPropertyModifications().add(IECModificationFactory.createIECModification((IECProperty) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof IECMethod) {
				if(!modificationListContainsElement(seedMods.getMethodModifications(), (IECMethod)component))
				seedMods.getMethodModifications().add(IECModificationFactory.createIECModification((IECMethod) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof IECAbstractProperty) {
				if(!modificationListContainsElement(seedMods.getAbstractPropertyModifications(), (IECAbstractProperty)component))
				seedMods.getAbstractPropertyModifications().add(IECModificationFactory.createIECModification((IECAbstractProperty) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof IECAbstractMethod) {
				if(!modificationListContainsElement(seedMods.getAbstractMethodModifications(), (IECAbstractMethod)component))
				seedMods.getAbstractMethodModifications().add(IECModificationFactory.createIECModification((IECAbstractMethod) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof IECInterface) {
				if(!modificationListContainsElement(seedMods.getInterfaceModifications(), (IECInterface)component))
				seedMods.getInterfaceModifications().add(IECModificationFactory.createIECModification((IECInterface) component, new HashSet<IECComponent>(), false)); 
			} else if (component instanceof Program) {
				if(!modificationListContainsElement(seedMods.getProgramModifications(), (Program)component))
				seedMods.getProgramModifications().add(IECModificationFactory.createIECModification((Program) component, new HashSet<IECComponent>(), false)); 
			}
		}
		((IECModificationRepository) version.getModificationMarkRepository()).setSeedModifications(seedMods);

		if(version.getHMIRepository() != null && version.getHMIModificationRepository() != null) {
			for(HMIModifyActorStep actorStep : version.getHMIModificationRepository().getSeedModifications().getActorStepModification())
				hmiSeedModifications.add(actorStep.getAffectedElement());
			for(HMIModifySystemStep systemStep : version.getHMIModificationRepository().getSeedModifications().getSystemStepModification())
				hmiSeedModifications.add(systemStep.getAffectedElement());
			
			HMISeedModifications hmiSeedMods = version.getHMIModificationRepository().getSeedModifications();
			for(HMIElement element : hmiSeedModifications) {
				if(element instanceof ActorStep) {
					if(!modificationListContainsElement(hmiSeedMods.getActorStepModification(), (ActorStep)element))
					hmiSeedMods.getActorStepModification().add(IECModificationFactory.createHMIModification((ActorStep)element, new HashSet<Identifier>(), false));
				} else if(element instanceof SystemStep) {
					if(!modificationListContainsElement(hmiSeedMods.getSystemStepModification(), (SystemStep)element))
					hmiSeedMods.getSystemStepModification().add(IECModificationFactory.createHMIModification((SystemStep)element, new HashSet<Identifier>(), false));
				}
			}
			((HMIModificationMarksRepository) version.getHMIModificationRepository()).setSeedModifications(hmiSeedMods);
		}
	}
	
	/**
	 * Calculates the DataType-/DataObject-related changes.
	 */
	public void calculateChangePropagationDueToDataDependencies(IECArchitectureVersion version) {
		
		// Create only one modification mark per element in this propagation step (affects only
		// DataTypes and DataObjects which could be marked by multiple sub-steps)
		List<IECComponent> elementsMarkedInThisStep = 
				new ArrayList<IECComponent>();
		
//		// Add seed modifications
//		calculateAndMarkGlobalVariableSeedModifications(version);

////		// 1 GlobalVariable -> Configuration
//		calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep);
////		// 2 GlobalVariable -> Program
//		calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep);
////		// 3 GlobalVariable -> FunctionBlock
//		calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep);
////		// 4 GlobalVariable -> Method
//		calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep);
		
		// All changes
		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, Program.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkProgramToConfigurationPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);

		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, GlobalVariable.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		
		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, Function.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);

		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, FunctionBlock.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkFunctionBlockToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);

		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECInterface.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkInterfaceToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);

		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECAbstractMethod.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
//		calculateAndMarkAbstractMethodToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
//		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		
		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECAbstractProperty.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
//		calculateAndMarkAbstractPropertyToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
//		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractPropertyToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkAbstractPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);

		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECMethod.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);

		this.setSeedModifications(new HashSet<IECComponent>(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, IECProperty.class)));
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		calculateAndMarkPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.SEED);
		elementsMarkedInThisStep = new ArrayList<IECComponent>();
		
		//HMI propagation

		if(version.getHMIRepository() != null && version.getHMIModificationRepository() != null) {
		
			ArrayList<HMIElement> hmiElementsMarkedInThisStep = new ArrayList<HMIElement>();
			calculateAndMarkFunctionBlockToSystemStepPropagation(version, hmiElementsMarkedInThisStep, IECModificationType.SEED);
			calculateAndMarkMethodToSystemStepPropagation(version, hmiElementsMarkedInThisStep, IECModificationType.SEED);
			for(HMIModifyActorStep mod : version.getHMIModificationRepository().getSeedModifications().getActorStepModification()) {
				hmiElementsMarkedInThisStep.add(mod.getAffectedElement());
			}
			for(HMIModifySystemStep mod : version.getHMIModificationRepository().getSeedModifications().getSystemStepModification()) {
				hmiElementsMarkedInThisStep.add(mod.getAffectedElement());
			}
			this.setHmiSeedModifications(hmiElementsMarkedInThisStep);
			calculateAndMarkSystemStepToActorStepPropagation(version, hmiElementsMarkedInThisStep, IECModificationType.SEED);
			calculateAndMarkActorStepToActorStepPropagation(version, hmiElementsMarkedInThisStep, IECModificationType.SEED);
			
			
			//If no at all changes: remove top-level element from tree
			if (this.getIECChangePropagationDueToDataDependencies().eContents().isEmpty()) {			
				version.getModificationMarkRepository().getChangePropagationSteps().remove(
						this.getIECChangePropagationDueToDataDependencies());	
			}
		}
	}
	
	//GlobalVariable propagations
	
	protected void calculateAndMarkGlobalVariableToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		}
		Map<FunctionBlock, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfGlobalVariable(version, markedGlobalVariables);

		lastModification = IECModificationType.GLOBALVARIABLE;
		for(Map.Entry<FunctionBlock, Set<GlobalVariable>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkGlobalVariableToConfigurationPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		}
		Map<Configuration, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpConfigurationOfGlobalVariable(version, markedGlobalVariables);

		lastModification = IECModificationType.GLOBALVARIABLE;
		for(Map.Entry<Configuration, Set<GlobalVariable>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getConfigurationModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyConfiguration modification = IECModificationmarksFactory.eINSTANCE.createIECModifyConfiguration();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());

			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkGlobalVariableToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		}
		Map<Program, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfGlobalVariable(version, markedGlobalVariables);

		lastModification = IECModificationType.GLOBALVARIABLE;
		 for(Map.Entry<Program, Set<GlobalVariable>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
				 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			 } else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());

				elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
			 }
		 }
	}
	
	protected void calculateAndMarkGlobalVariableToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof GlobalVariable) {
					markedGlobalVariables.add((GlobalVariable) marked);
				}
			}
		}
		Map<IECMethod, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfGlobalVariable(version, markedGlobalVariables);

		lastModification = IECModificationType.GLOBALVARIABLE;
		 for(Map.Entry<IECMethod, Set<GlobalVariable>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
				 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			 } else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
				//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
			 }
		 }
	}
	
	//FunctionBlock propagations
	
	protected void calculateAndMarkFunctionBlockToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<Program, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<Program, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkFunctionBlockToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<IECMethod, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<IECMethod, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkFunctionBlockToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<FunctionBlock, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<FunctionBlock, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkFunctionBlockToGlobalVariablePropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<GlobalVariable, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpGlobalVariablesOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<GlobalVariable, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getGlobalVariableModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyGlobalVariable modification = IECModificationmarksFactory.eINSTANCE.createIECModifyGlobalVariable();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkFunctionBlockToAbstractMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<IECAbstractMethod, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractMethodsOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<IECAbstractMethod, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getAbstractMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyAbstractMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getAbstractMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkFunctionBlockToAbstractPropertyPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<IECAbstractProperty, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractPropertiesOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<IECAbstractProperty, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getAbstractPropertyModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyAbstractProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractProperty();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getAbstractPropertyModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
	 }
	}
	
	protected void calculateAndMarkFunctionBlockToPropertyPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<IECProperty, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpPropertiesOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<IECProperty, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getPropertyModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getPropertyModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkFunctionBlockToFunctionPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof FunctionBlock) {
					markedFunctionBlocks.add((FunctionBlock) marked);
				}
			}
		}
		Map<Function, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionsOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<Function, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//Function propagations
	
	protected void calculateAndMarkFunctionToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<Function> markedFunctions = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		}
		Map<IECMethod, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfFunction(version, markedFunctions);

		lastModification = IECModificationType.FUNCTION;
		for(Map.Entry<IECMethod, Set<Function>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkFunctionToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<Function> markedFunctions = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		}
		Map<FunctionBlock, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfFunction(version, markedFunctions);

		lastModification = IECModificationType.FUNCTION;
		for(Map.Entry<FunctionBlock, Set<Function>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkFunctionToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<Function> markedFunctions = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		}
		Map<Program, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfFunction(version, markedFunctions);

		lastModification = IECModificationType.FUNCTION;
		for(Map.Entry<Program, Set<Function>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkFunctionToFunctionPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<Function> markedFunctions = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof Function) {
					markedFunctions.add((Function) marked);
				}
			}
		}
		Map<Function, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionsOfFunction(version, markedFunctions);

		lastModification = IECModificationType.FUNCTION;
		for(Map.Entry<Function, Set<Function>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//Interface propagations
	
	protected void calculateAndMarkInterfaceToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<FunctionBlock, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<FunctionBlock, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToInterfacePropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<IECInterface, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpInterfacesOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<IECInterface, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getInterfaceModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<Program, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<Program, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToFunctionPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<Function, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionsOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<Function, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToPropertyPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<IECProperty, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpPropertiesOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<IECProperty, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getPropertyModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getPropertyModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToAbstractPropertyPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<IECAbstractProperty, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractPropertiesOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<IECAbstractProperty, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getAbstractPropertyModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyAbstractProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractProperty();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getAbstractPropertyModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<IECMethod, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<IECMethod, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToAbstractMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<IECAbstractMethod, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractMethodsOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<IECAbstractMethod, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getAbstractMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyAbstractMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getAbstractMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkInterfaceToGlobalVariablePropagation(IECArchitectureVersion version, //marker
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECInterface) {
					markedInterfaces.add((IECInterface) marked);
				}
			}
		}
		Map<GlobalVariable, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpGlobalVariablesOfInterface(version, markedInterfaces);

		lastModification = IECModificationType.INTERFACE;
		for(Map.Entry<GlobalVariable, Set<IECInterface>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getGlobalVariableModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyGlobalVariable modification = IECModificationmarksFactory.eINSTANCE.createIECModifyGlobalVariable();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//Abstract Method propagation
	
	protected void calculateAndMarkAbstractMethodToInterfacePropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		}
		Map<IECInterface, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpInterfacesOfAbstractMethod(version, markedMethods);

		lastModification = IECModificationType.ABSTRACTMETHOD;
		for(Map.Entry<IECInterface, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getInterfaceModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractMethodToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		}
		Map<Program, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfAbstractMethod(version, markedMethods);

		lastModification = IECModificationType.ABSTRACTMETHOD;
		for(Map.Entry<Program, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractMethodToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		}
		Map<IECMethod, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfAbstractMethod(version, markedMethods);

		lastModification = IECModificationType.ABSTRACTMETHOD;
		for(Map.Entry<IECMethod, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractMethodToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractMethod) {
					markedMethods.add((IECAbstractMethod) marked);
				}
			}
		}
		Map<FunctionBlock, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfAbstractMethod(version, markedMethods);

		lastModification = IECModificationType.ABSTRACTMETHOD;
		for(Map.Entry<FunctionBlock, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//Abstract Property propagation
	
	protected void calculateAndMarkAbstractPropertyToInterfacePropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		}
		Map<IECInterface, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpInterfacesOfAbstractProperty(version, properties);

		lastModification = IECModificationType.ABSTRACTPROPERTY;
		for(Map.Entry<IECInterface, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getInterfaceModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractPropertyToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		}
		Map<Program, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfAbstractProperty(version, properties);

		lastModification = IECModificationType.ABSTRACTPROPERTY;
		for(Map.Entry<Program, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractPropertyToPropertyPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		}
		Map<IECProperty, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpPropertiesOfAbstractProperty(version, properties);

		lastModification = IECModificationType.ABSTRACTPROPERTY;
		for(Map.Entry<IECProperty, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getPropertyModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getPropertyModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractPropertyToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		}
		Map<FunctionBlock, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfAbstractProperty(version, properties);

		lastModification = IECModificationType.ABSTRACTPROPERTY;
		for(Map.Entry<FunctionBlock, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkAbstractPropertyToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECAbstractProperty) {
					properties.add((IECAbstractProperty) marked);
				}
			}
		}
		Map<IECMethod, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfAbstractProperty(version, properties);

		lastModification = IECModificationType.ABSTRACTPROPERTY;
		for(Map.Entry<IECMethod, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//Program propagation
	
	protected void calculateAndMarkProgramToConfigurationPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<Program> markedPrograms = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof Program) {
					markedPrograms.add((Program) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof Program) {
					markedPrograms.add((Program) marked);
				}
			}
		}
		Map<Configuration, Set<Program>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpConfigurationOfProgram(version, markedPrograms);

		lastModification = IECModificationType.PROGRAM;
		for(Map.Entry<Configuration, Set<Program>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getConfigurationModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyConfiguration modification = IECModificationmarksFactory.eINSTANCE.createIECModifyConfiguration();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.addAll(elementsToBeMarkedEntry.getValue());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//Method propagation
	
	protected void calculateAndMarkMethodToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECMethod) {
					markedMethods.add((IECMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECMethod) {
					markedMethods.add((IECMethod) marked);
				}
			}
		}
		Map<FunctionBlock, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfMethod(version, markedMethods);

		lastModification = IECModificationType.METHOD;
		for(Map.Entry<FunctionBlock, Set<IECMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkMethodToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECMethod) {
					markedMethods.add((IECMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECMethod) {
					markedMethods.add((IECMethod) marked);
				}
			}
		}
		Map<Program, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfMethod(version, markedMethods);

		lastModification = IECModificationType.METHOD;
		for(Map.Entry<Program, Set<IECMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkMethodToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECMethod> markedMethods = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECMethod) {
					markedMethods.add((IECMethod) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECMethod) {
					markedMethods.add((IECMethod) marked);
				}
			}
		}
		Map<IECMethod, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfMethod(version, markedMethods);

		lastModification = IECModificationType.METHOD;
		for(Map.Entry<IECMethod, Set<IECMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}

	//Property propagation
	
	protected void calculateAndMarkPropertyToMethodPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECProperty> markedProperties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECProperty) {
					markedProperties.add((IECProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECProperty) {
					markedProperties.add((IECProperty) marked);
				}
			}
		}
		Map<IECMethod, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfProperty(version, markedProperties);

		lastModification = IECModificationType.PROPERTY;
		for(Map.Entry<IECMethod, Set<IECProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getMethodModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
			//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkPropertyToFunctionBlockPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECProperty> markedProperties = new ArrayList<>();
		if(lastModification.equals(IECModificationType.SEED)) {
			for(IECComponent marked : getSeedModifications()) {
				if(marked instanceof IECProperty) {
					markedProperties.add((IECProperty) marked);
				}
			}
		} else {
			for(IECComponent marked : elementsMarkedInThisStep) {
				if(marked instanceof IECProperty) {
					markedProperties.add((IECProperty) marked);
				}
			}
		}
		Map<FunctionBlock, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfProperty(version, markedProperties);

		lastModification = IECModificationType.PROPERTY;
		for(Map.Entry<FunctionBlock, Set<IECProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getFunctionBlockModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkPropertyToProgramPropagation(IECArchitectureVersion version,
			List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECProperty> markedProperties = new ArrayList<>();
		for(IECComponent marked : getSeedModifications()) {
			if(marked instanceof IECProperty) {
				markedProperties.add((IECProperty) marked);
			}
		}
		Map<Program, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfProperty(version, markedProperties);

		lastModification = IECModificationType.PROPERTY;
		for(Map.Entry<Program, Set<IECProperty>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getProgramModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
					add(modification);
			continuePropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	//HMI propagations
	
	protected void calculateAndMarkFunctionBlockToSystemStepPropagation(IECArchitectureVersion version,
			List<HMIElement> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		List<IECComponent> allMarkedIecComponents = new ArrayList<>();
		allMarkedIecComponents.addAll(getAllChangedComponents());
		allMarkedIecComponents.addAll(getSeedModifications());
		for(IECComponent marked : allMarkedIecComponents) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<SystemStep, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpSystemStepsOfFunctionBlock(version, markedFunctionBlocks);

		lastModification = IECModificationType.FUNCTIONBLOCK;
		for(Map.Entry<SystemStep, Set<FunctionBlock>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getSystemStepModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			HMIModifySystemStep modification = HMIModificationmarksFactory.eINSTANCE.createHMIModifySystemStep();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getHMIChangePropagationDueToSoftwareDependency().getSystemStepModification().
					add(modification);
		 }
		}
	}
	
	protected void calculateAndMarkMethodToSystemStepPropagation(IECArchitectureVersion version,
			List<HMIElement> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<IECMethod> markedMethods = new ArrayList<>();
		List<IECComponent> allMarkedIecComponents = new ArrayList<>();
		allMarkedIecComponents.addAll(getAllChangedComponents());
		allMarkedIecComponents.addAll(getSeedModifications());
		for(IECComponent marked : allMarkedIecComponents) {
			if(marked instanceof IECMethod) {
				markedMethods.add((IECMethod) marked);
			}
		}
		Map<SystemStep, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpSystemStepsOfMethod(version, markedMethods);

		lastModification = IECModificationType.METHOD;
		for(Map.Entry<SystemStep, Set<IECMethod>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getSystemStepModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			HMIModifySystemStep modification = HMIModificationmarksFactory.eINSTANCE.createHMIModifySystemStep();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getHMIChangePropagationDueToSoftwareDependency().getSystemStepModification().
					add(modification);
		 }
		}
	}
	
	protected void calculateAndMarkSystemStepToActorStepPropagation(IECArchitectureVersion version,
			List<HMIElement> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<SystemStep> steps = new ArrayList<>();
		for(HMIElement marked : getHmiSeedModifications()) {
			if(marked instanceof SystemStep) {
				steps.add((SystemStep) marked);
			}
		}
		Map<ActorStep, Set<SystemStep>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpActorStepsOfSystemStep(version, steps);

		lastModification = IECModificationType.SYSTEMSTEP;
		for(Map.Entry<ActorStep, Set<SystemStep>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getSystemStepModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			HMIModifyActorStep modification = HMIModificationmarksFactory.eINSTANCE.createHMIModifyActorStep();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getHMIChangePropagationDueToSoftwareDependency().getActorStepModification().
					add(modification);
			continueHMIPropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
	protected void calculateAndMarkActorStepToActorStepPropagation(IECArchitectureVersion version,
			List<HMIElement> elementsMarkedInThisStep, IECModificationType lastModification) {
		List<ActorStep> steps = new ArrayList<>();
		for(HMIElement marked : getHmiSeedModifications()) {
			if(marked instanceof ActorStep) {
				steps.add((ActorStep) marked);
			}
		}
		Map<ActorStep, Set<HMIElement>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpActorStepsOfActorStep(version, steps);

		lastModification = IECModificationType.ACTORSTEP;
		for(Map.Entry<ActorStep, Set<HMIElement>> elementsToBeMarkedEntry: 
		 	elementsToBeMarked.entrySet()) {
		 if(componentAlreadyMarked(elementsToBeMarkedEntry.getKey())) {
			 getSystemStepModification(elementsToBeMarkedEntry.getKey()).getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
		 } else {
			HMIModifyActorStep modification = HMIModificationmarksFactory.eINSTANCE.createHMIModifyActorStep();
			modification.setToolderived(true);
			modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
			modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
			
			elementsMarkedInThisStep.add(elementsToBeMarkedEntry.getKey());
//			this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
			this.getHMIChangePropagationDueToSoftwareDependency().getActorStepModification().
					add(modification);
			continueHMIPropagation(version, elementsToBeMarkedEntry.getKey(), elementsMarkedInThisStep, lastModification);
		 }
		}
	}
	
//	private  List<ModificationMarker> modificationPaths;
//	
//	private <T extends IECComponent> boolean isNewModification(IECArchitectureVersion version, T entry, List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
//		if(modificationPaths.contains(new ModificationMarker(lastModification, IECModificationType.getFomComponent(entry)))) {
//			return false;
//		} else {
//			modificationPaths.add(new ModificationMarker(lastModification, IECModificationType.getFomComponent(entry)));
//			return true;
//		}
//	}
	
	public  <T extends HMIElement>void continueHMIPropagation(IECArchitectureVersion version, T entry, List<HMIElement> elementsMarkedInThisStep, IECModificationType lastModification) {
		switch (lastModification) {
		case FUNCTIONBLOCK:
		case METHOD:
		case SEED:
			break;
		case ACTORSTEP:
			calculateAndMarkActorStepToActorStepPropagation(version, elementsMarkedInThisStep, lastModification);
			break;
		case SYSTEMSTEP:
			break;
		default:
			break;
		}
		
	}
	
	public <T extends IECComponent>void continuePropagation(IECArchitectureVersion version, T entry, List<IECComponent> elementsMarkedInThisStep, IECModificationType lastModification) {
		switch (lastModification) {
		case SEED:
			if(entry instanceof GlobalVariable) {
				calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
			} else if (entry instanceof Function) {
				calculateAndMarkFunctionToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
			} else if (entry instanceof FunctionBlock) {
				calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
			} else if (entry instanceof IECInterface) {
				calculateAndMarkInterfaceToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECAbstractMethod) {
//				calculateAndMarkAbstractMethodToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECAbstractProperty) {
//				calculateAndMarkAbstractPropertyToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
			} else if (entry instanceof IECMethod) {
				calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
			} else if (entry instanceof IECProperty) {
				calculateAndMarkPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
			} else if (entry instanceof Program) {
//				calculateAndMarkProgramToConfigurationPropagation(version, elementsMarkedInThisStep); Nicht sinnvoll, Änderungen werden immer in Program angepasst
			}
			break;
		case FUNCTIONBLOCK : 
			if (entry instanceof IECAbstractMethod) {
//				calculateAndMarkAbstractMethodToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECAbstractProperty) {
//				calculateAndMarkAbstractPropertyToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
			} else if (entry instanceof Function) {
				calculateAndMarkFunctionToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
			} else if (entry instanceof IECMethod) {
				calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
			} else if (entry instanceof IECProperty) {
				calculateAndMarkPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
			} else if(entry instanceof GlobalVariable) {
				calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
			} 
//			else if (entry instanceof FunctionBlock) {
//				calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//				calculateAndMarkFunctionBlockToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
//			}
			break;
		case INTERFACE:

			if(entry instanceof GlobalVariable) {
				calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
				calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.GLOBALVARIABLE);
			} else if (entry instanceof Function) {
				calculateAndMarkFunctionToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
				calculateAndMarkFunctionToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTION);
			} else if (entry instanceof FunctionBlock) {
				calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
			} else if (entry instanceof IECInterface) {
				calculateAndMarkInterfaceToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECAbstractMethod) {
//				calculateAndMarkAbstractMethodToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkAbstractMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECAbstractProperty) {
//				calculateAndMarkAbstractPropertyToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
				calculateAndMarkAbstractPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.ABSTRACTPROPERTY);
			} else if (entry instanceof IECMethod) {
				calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
			} else if (entry instanceof IECProperty) {
				calculateAndMarkPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
			}
			break;
		case ABSTRACTMETHOD:
			if (entry instanceof FunctionBlock) {
				calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
			} else if (entry instanceof IECInterface) {
				calculateAndMarkInterfaceToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECMethod) {
				calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
			} 
			break;
		case ABSTRACTPROPERTY:
			if (entry instanceof FunctionBlock) {
				calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
				calculateAndMarkFunctionBlockToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.FUNCTIONBLOCK);
			} else if (entry instanceof IECInterface) {
				calculateAndMarkInterfaceToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToInterfacePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToFunctionPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToGlobalVariablePropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractPropertyPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
				calculateAndMarkInterfaceToAbstractMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.INTERFACE);
			} else if (entry instanceof IECMethod) {
				calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
				calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.METHOD);
			} else if (entry instanceof IECProperty) {
				calculateAndMarkPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToMethodPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
				calculateAndMarkPropertyToProgramPropagation(version, elementsMarkedInThisStep, IECModificationType.PROPERTY);
			}
			break;
			
		default:
			break;
		}
	}
	
	private IECModifyConfiguration getConfigurationModification(IECComponent component) {
		for(IECModifyConfiguration modification : this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyProgram getProgramModification(IECComponent component) {
		for(IECModifyProgram modification : this.getIECChangePropagationDueToDataDependencies().getProgramModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyInterface getInterfaceModification(IECComponent component) {
		for(IECModifyInterface modification : this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyProperty getPropertyModification(IECComponent component) {
		for(IECModifyProperty modification : this.getIECChangePropagationDueToDataDependencies().getPropertyModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyFunction getFunctionModification(IECComponent component) {
		for(IECModifyFunction modification : this.getIECChangePropagationDueToDataDependencies().getFunctionModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyMethod getMethodModification(IECComponent component) {
		for(IECModifyMethod modification : this.getIECChangePropagationDueToDataDependencies().getMethodModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyAbstractMethod getAbstractMethodModification(IECComponent component) {
		for(IECModifyAbstractMethod modification : this.getIECChangePropagationDueToDataDependencies().getAbstractMethodModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyAbstractProperty getAbstractPropertyModification(IECComponent component) {
		for(IECModifyAbstractProperty modification : this.getIECChangePropagationDueToDataDependencies().getAbstractPropertyModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyFunctionBlock getFunctionBlockModification(IECComponent component) {
		for(IECModifyFunctionBlock modification : this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private IECModifyGlobalVariable getGlobalVariableModification(IECComponent component) {
		for(IECModifyGlobalVariable modification : this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications()) {
			if(modification.getAffectedElement().getId().equals(component.getId()));
				return modification;
		}
		return null;
	}
	
	private HMIModifyActorStep getActorStepModification(HMIElement element) {
		for(HMIModifyActorStep modification : this.getHMIChangePropagationDueToSoftwareDependency().getActorStepModification()) {
			if(modification.getAffectedElement().getId().equals(element.getId()));
				return modification;
		}
		return null;
	}
	
	private HMIModifySystemStep getSystemStepModification(HMIElement element) {
		for(HMIModifySystemStep modification : this.getHMIChangePropagationDueToSoftwareDependency().getSystemStepModification()) {
			if(modification.getAffectedElement().getId().equals(element.getId()));
				return modification;
		}
		return null;
	}
	
	private boolean componentAlreadyMarked(IECComponent component) {
		 for(IECComponent comp : getAllChangedComponents()) {
			 if(component.getId().equals(comp.getId())) return true;
		 }
		 return false;
	}
	
	private boolean componentAlreadyMarked(HMIElement component) {
		 for(HMIElement comp : getAllChangedHMIElements()) {
			 if(component.getId().equals(comp.getId())) return true;
		 }
		 return false;
	}
	
	private List<IECComponent> getAllChangedComponents() {
		List<IECComponent> components = new ArrayList<>();
		for(IECModifyAbstractMethod component : this.getIECChangePropagationDueToDataDependencies().getAbstractMethodModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyAbstractProperty component : this.getIECChangePropagationDueToDataDependencies().getAbstractPropertyModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyConfiguration component : this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyFunctionBlock component : this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyFunction component : this.getIECChangePropagationDueToDataDependencies().getFunctionModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyGlobalVariable component : this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyInterface component : this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyMethod component : this.getIECChangePropagationDueToDataDependencies().getMethodModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyProperty component : this.getIECChangePropagationDueToDataDependencies().getPropertyModifications()) {
			components.add(component.getAffectedElement());
		}
		for(IECModifyProgram component : this.getIECChangePropagationDueToDataDependencies().getProgramModifications()) {
			components.add(component.getAffectedElement());
		}
		return components;
	}
	
	private List<HMIElement> getAllChangedHMIElements() {
		List<HMIElement> elements = new ArrayList<>();
		for(HMIModifyActorStep step : this.getHMIChangePropagationDueToSoftwareDependency().getActorStepModification()) {
			elements.add(step.getAffectedElement());
		}
		for(HMIModifySystemStep step : this.getHMIChangePropagationDueToSoftwareDependency().getSystemStepModification()) {
			elements.add(step.getAffectedElement());
		}
		return elements;
	}
	
	public void postAnalysis(IECArchitectureVersion version) {
	}
	
	public IECChangePropagationDueToDataDependency getIECChangePropagationDueToDataDependencies() {
		return changePropagationDueToDataDependencies;
	}
	
	public HMIChangePropagationDueToSoftwareDependency getHMIChangePropagationDueToSoftwareDependency() {
		return changePropagationDueToSoftwareDependency;
	}

	public Collection<IECComponent> getSeedModifications() {
		return seedModifications;
	}

	public Collection<HMIElement> getHMISeedModifications() {
		return hmiSeedModifications;
	}

	public void setSeedModifications(Collection<IECComponent> markedComponents) {
		this.seedModifications = markedComponents;
	}

	public void setIECChangePropagationDueToDataDependencies(
			IECChangePropagationDueToDataDependency changePropagationDueToDataDependencies) {
		this.changePropagationDueToDataDependencies = changePropagationDueToDataDependencies;
	}

	public void setHMIChangePropagationDueToSoftwareDependencies(
			HMIChangePropagationDueToSoftwareDependency changePropagationDueToSoftwareDependency) {
		this.changePropagationDueToSoftwareDependency = changePropagationDueToSoftwareDependency;
	}

	public Collection<HMIElement> getHmiSeedModifications() {
		return hmiSeedModifications;
	}

	public void setHmiSeedModifications(Collection<HMIElement> hmiSeedModifications) {
		this.hmiSeedModifications = hmiSeedModifications;
	}
	
	public <S, T> boolean modificationContainsElement(AbstractModification<S, T> am, S element) {
		return am.getAffectedElement().equals(element);
	}
	
	public boolean modificationListContainsElement(EList<IECModifyGlobalVariable> ml, GlobalVariable element) {
		for(IECModifyGlobalVariable am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyFunctionBlock> ml, FunctionBlock element) {
		for(IECModifyFunctionBlock am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyFunction> ml, Function element) {
		for(IECModifyFunction am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyMethod> ml, IECMethod element) {
		for(IECModifyMethod am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyAbstractMethod> ml, IECAbstractMethod element) {
		for(IECModifyAbstractMethod am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyProperty> ml, IECProperty element) {
		for(IECModifyProperty am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyAbstractProperty> ml, IECAbstractProperty element) {
		for(IECModifyAbstractProperty am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyInterface> ml, IECInterface element) {
		for(IECModifyInterface am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<IECModifyProgram> ml, Program element) {
		for(IECModifyProgram am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<HMIModifyActorStep> ml, ActorStep element) {
		for(HMIModifyActorStep am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean modificationListContainsElement(EList<HMIModifySystemStep> ml, SystemStep element) {
		for(HMIModifySystemStep am : ml) {
			if (am.getAffectedElement().equals(element)) {
				return true;
			}
		}
		return false;
	}
	  

}
