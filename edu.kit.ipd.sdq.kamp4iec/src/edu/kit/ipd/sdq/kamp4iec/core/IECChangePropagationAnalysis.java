package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.kamp.architecture.ArchitectureModelLookup;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.propagation.AbstractChangePropagationAnalysis;
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
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECChangePropagationDueToDataDependency;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModificationmarksFactory;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyAbstractMethod;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyAbstractProperty;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyConfiguration;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyFunction;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyFunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyGlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyInterface;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyMethod;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyProgram;
import edu.kit.ipd.sdq.kamp4iec.model.modificationmarks.IECModifyProperty;

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

	private Collection<IECComponent> markedComponents;
	private IECChangePropagationDueToDataDependency changePropagationDueToDataDependencies;

	@Override
	public void runChangePropagationAnalysis(IECArchitectureVersion version) {
		// Setup
		this.prepareAnalysis(version);
		
		// Calculate
		this.calculateChangePropagationDueToDataDependencies(version);		
			
		// Update

		
	}
	
	private void prepareAnalysis(IECArchitectureVersion version) {
		this.setIECChangePropagationDueToDataDependency(IECModificationmarksFactory.eINSTANCE.
				createIECChangePropagationDueToDataDependency());
		/* Link calculated data dependency propagation steps to propagation steps of version,
		 * so the already marked elements are up to date for all sub-steps of the algorithm*/	
		version.getModificationMarkRepository().getChangePropagationSteps().add(
				this.getIECChangePropagationDueToDataDependencies());
		// Store marked model elements to reduce model traversal in calculation methods
		//double cast to avoid type-mismatch
		this.setMarkedComponents(edu.kit.ipd.sdq.kamp.architecture.ArchitectureModelLookup.lookUpMarkedObjectsOfAType(version, IECComponent.class));
	}
	
	/**
	 * Calculates the DataType-/DataObject-related changes.
	 */
	public void calculateChangePropagationDueToDataDependencies(IECArchitectureVersion version) {
		// Create only one modification mark per element in this propagation step (affects only
		// DataTypes and DataObjects which could be marked by multiple sub-steps)
		Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep = 
				new HashMap<EObject, AbstractModification<?,EObject>>();
		
//		// Add seed modifications
		calculateAndMarkGlobalVariableSeedModifications(version);

//		// 1 GlobalVariable -> Configuration
		calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep);
//		// 2 GlobalVariable -> Program
		calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep);
//		// 3 GlobalVariable -> FunctionBlock
		calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep);
//		// 4 GlobalVariable -> Method
		calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep);
		
		//If no at all changes: remove top-level element from tree
		if (this.getIECChangePropagationDueToDataDependencies().eContents().isEmpty()) {			
			version.getModificationMarkRepository().getChangePropagationSteps().remove(
					this.getIECChangePropagationDueToDataDependencies());	
		}
	}
	
	protected void calculateAndMarkGlobalVariableSeedModifications(IECArchitectureVersion version) {
		markedComponents.addAll(ArchitectureModelLookup.lookUpMarkedObjectsOfATypeInSeedModifications(version, GlobalVariable.class));
	}
	
	//GlobalVariable propagations
	
	protected void calculateAndMarkGlobalVariableToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof GlobalVariable) {
				markedGlobalVariables.add((GlobalVariable) marked);
			}
		}
		Map<FunctionBlock, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfGlobalVariable(version, markedGlobalVariables);

		 for(Map.Entry<FunctionBlock, Set<GlobalVariable>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modifyDataType = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modifyDataType.setToolderived(true);
				modifyDataType.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modifyDataType.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modifyDataType);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modifyDataType);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkGlobalVariableToConfigurationPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof GlobalVariable) {
				markedGlobalVariables.add((GlobalVariable) marked);
			}
		}
		Map<Configuration, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpConfigurationOfGlobalVariable(version, markedGlobalVariables);

		 for(Map.Entry<Configuration, Set<GlobalVariable>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyConfiguration modification = IECModificationmarksFactory.eINSTANCE.createIECModifyConfiguration();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkGlobalVariableToProgramPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof GlobalVariable) {
				markedGlobalVariables.add((GlobalVariable) marked);
			}
		}
		Map<Program, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfGlobalVariable(version, markedGlobalVariables);

		 for(Map.Entry<Program, Set<GlobalVariable>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkGlobalVariableToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<GlobalVariable> markedGlobalVariables = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof GlobalVariable) {
				markedGlobalVariables.add((GlobalVariable) marked);
			}
		}
		Map<IECMethod, Set<GlobalVariable>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfGlobalVariable(version, markedGlobalVariables);

		 for(Map.Entry<IECMethod, Set<GlobalVariable>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//FunctionBlock propagations
	
	protected void calculateAndMarkFunctionBlockToProgramPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<Program, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<Program, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<IECMethod, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<IECMethod, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<FunctionBlock, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<FunctionBlock, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToGlobalVariablePropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<GlobalVariable, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpGlobalVariablesOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<GlobalVariable, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyGlobalVariable modification = IECModificationmarksFactory.eINSTANCE.createIECModifyGlobalVariable();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToAbstractMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<IECAbstractMethod, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractMethodsOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<IECAbstractMethod, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyAbstractMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getAbstractMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToAbstractPropertyPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<IECAbstractProperty, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractPropertiesOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<IECAbstractProperty, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyAbstractProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractProperty();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getAbstractPropertyModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToPropertyPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<IECProperty, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpPropertiesOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<IECProperty, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getPropertyModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionBlockToFunctionPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<FunctionBlock> markedFunctionBlocks = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof FunctionBlock) {
				markedFunctionBlocks.add((FunctionBlock) marked);
			}
		}
		Map<Function, Set<FunctionBlock>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionsOfFunctionBlock(version, markedFunctionBlocks);

		 for(Map.Entry<Function, Set<FunctionBlock>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//Function propagations
	
	protected void calculateAndMarkFunctionToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<Function> markedFunctions = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof Function) {
				markedFunctions.add((Function) marked);
			}
		}
		Map<IECMethod, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfFunction(version, markedFunctions);

		 for(Map.Entry<IECMethod, Set<Function>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<Function> markedFunctions = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof Function) {
				markedFunctions.add((Function) marked);
			}
		}
		Map<FunctionBlock, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfFunction(version, markedFunctions);

		 for(Map.Entry<FunctionBlock, Set<Function>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionToProgramPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<Function> markedFunctions = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof Function) {
				markedFunctions.add((Function) marked);
			}
		}
		Map<Program, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfFunction(version, markedFunctions);

		 for(Map.Entry<Program, Set<Function>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionToGlobalVariablePropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<Function> markedFunctions = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof Function) {
				markedFunctions.add((Function) marked);
			}
		}
		Map<GlobalVariable, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpGlobalVariablesOfFunction(version, markedFunctions);

		 for(Map.Entry<GlobalVariable, Set<Function>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyGlobalVariable modification = IECModificationmarksFactory.eINSTANCE.createIECModifyGlobalVariable();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkFunctionToFunctionPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<Function> markedFunctions = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof Function) {
				markedFunctions.add((Function) marked);
			}
		}
		Map<Function, Set<Function>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionsOfFunction(version, markedFunctions);

		 for(Map.Entry<Function, Set<Function>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//Interface propagations
	
	protected void calculateAndMarkInterfaceToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<FunctionBlock, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfInterface(version, markedInterfaces);

		 for(Map.Entry<FunctionBlock, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToInterfacePropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<IECInterface, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpInterfacesOfInterface(version, markedInterfaces);

		 for(Map.Entry<IECInterface, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToProgramPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<Program, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfInterface(version, markedInterfaces);

		 for(Map.Entry<Program, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToFunctionPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<Function, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionsOfInterface(version, markedInterfaces);

		 for(Map.Entry<Function, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToPropertyPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<IECProperty, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpPropertiesOfInterface(version, markedInterfaces);

		 for(Map.Entry<IECProperty, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getPropertyModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToAbstractPropertyPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<IECAbstractProperty, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractPropertiesOfInterface(version, markedInterfaces);

		 for(Map.Entry<IECAbstractProperty, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyAbstractProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractProperty();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getAbstractPropertyModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<IECMethod, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfInterface(version, markedInterfaces);

		 for(Map.Entry<IECMethod, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToAbstractMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<IECAbstractMethod, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpAbstractMethodsOfInterface(version, markedInterfaces);

		 for(Map.Entry<IECAbstractMethod, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyAbstractMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getAbstractMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkInterfaceToGlobalVariablePropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECInterface> markedInterfaces = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECInterface) {
				markedInterfaces.add((IECInterface) marked);
			}
		}
		Map<GlobalVariable, Set<IECInterface>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpGlobalVariablesOfInterface(version, markedInterfaces);

		 for(Map.Entry<GlobalVariable, Set<IECInterface>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyGlobalVariable modification = IECModificationmarksFactory.eINSTANCE.createIECModifyGlobalVariable();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getGlobalVariableModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//Abstract Method propagation
	
	protected void calculateAndMarkAbstractMethodToInterfacePropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECAbstractMethod) {
				markedMethods.add((IECAbstractMethod) marked);
			}
		}
		Map<IECInterface, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpInterfacesOfAbstractMethod(version, markedMethods);

		 for(Map.Entry<IECInterface, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkAbstractMethodToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECAbstractMethod) {
				markedMethods.add((IECAbstractMethod) marked);
			}
		}
		Map<IECMethod, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfAbstractMethod(version, markedMethods);

		 for(Map.Entry<IECMethod, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkAbstractMethodToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECAbstractMethod> markedMethods = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECAbstractMethod) {
				markedMethods.add((IECAbstractMethod) marked);
			}
		}
		Map<FunctionBlock, Set<IECAbstractMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfAbstractMethod(version, markedMethods);

		 for(Map.Entry<FunctionBlock, Set<IECAbstractMethod>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//Abstract Property propagation
	
	protected void calculateAndMarkAbstractPropertyToInterfacePropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECAbstractProperty) {
				properties.add((IECAbstractProperty) marked);
			}
		}
		Map<IECInterface, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpInterfacesOfAbstractProperty(version, properties);

		 for(Map.Entry<IECInterface, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getInterfaceModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkAbstractPropertyToPropertyPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECAbstractProperty) {
				properties.add((IECAbstractProperty) marked);
			}
		}
		Map<IECProperty, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpPropertiesOfAbstractProperty(version, properties);

		 for(Map.Entry<IECProperty, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getPropertyModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkAbstractPropertyToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECAbstractProperty> properties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECAbstractProperty) {
				properties.add((IECAbstractProperty) marked);
			}
		}
		Map<FunctionBlock, Set<IECAbstractProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfAbstractProperty(version, properties);

		 for(Map.Entry<FunctionBlock, Set<IECAbstractProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//Program propagation
	
	protected void calculateAndMarkProgramToConfigurationPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<Program> markedPrograms = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof Program) {
				markedPrograms.add((Program) marked);
			}
		}
		Map<Configuration, Set<Program>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpConfigurationOfProgram(version, markedPrograms);

		 for(Map.Entry<Configuration, Set<Program>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyConfiguration modification = IECModificationmarksFactory.eINSTANCE.createIECModifyConfiguration();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	//Method propagation
	
	protected void calculateAndMarkMethodToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECMethod> markedMethods = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECMethod) {
				markedMethods.add((IECMethod) marked);
			}
		}
		Map<FunctionBlock, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfMethod(version, markedMethods);

		 for(Map.Entry<FunctionBlock, Set<IECMethod>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkMethodToProgramPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECMethod> markedMethods = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECMethod) {
				markedMethods.add((IECMethod) marked);
			}
		}
		Map<Program, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfMethod(version, markedMethods);

		 for(Map.Entry<Program, Set<IECMethod>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkMethodToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECMethod> markedMethods = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECMethod) {
				markedMethods.add((IECMethod) marked);
			}
		}
		Map<IECMethod, Set<IECMethod>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfMethod(version, markedMethods);

		 for(Map.Entry<IECMethod, Set<IECMethod>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}

	//Property propagation
	
	protected void calculateAndMarkPropertyToMethodPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECProperty> markedProperties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECProperty) {
				markedProperties.add((IECProperty) marked);
			}
		}
		Map<IECMethod, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpMethodsOfProperty(version, markedProperties);

		 for(Map.Entry<IECMethod, Set<IECProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getMethodModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkPropertyToFunctionBlockPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECProperty> markedProperties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECProperty) {
				markedProperties.add((IECProperty) marked);
			}
		}
		Map<FunctionBlock, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpFunctionBlocksOfProperty(version, markedProperties);

		 for(Map.Entry<FunctionBlock, Set<IECProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getFunctionBlockModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkPropertyToConfigurationPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECProperty> markedProperties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECProperty) {
				markedProperties.add((IECProperty) marked);
			}
		}
		Map<Configuration, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpConfigurationsOfProperty(version, markedProperties);

		 for(Map.Entry<Configuration, Set<IECProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyConfiguration modification = IECModificationmarksFactory.eINSTANCE.createIECModifyConfiguration();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getConfigurationModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	protected void calculateAndMarkPropertyToProgramPropagation(IECArchitectureVersion version,
			Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		List<IECProperty> markedProperties = new ArrayList<>();
		for(IECComponent marked : getMarkedComponents()) {
			if(marked instanceof IECProperty) {
				markedProperties.add((IECProperty) marked);
			}
		}
		Map<Program, Set<IECProperty>> elementsToBeMarked = IECArchitectureModelLookup.
				lookUpProgramsOfProperty(version, markedProperties);

		 for(Map.Entry<Program, Set<IECProperty>> elementsToBeMarkedEntry: 
			 	elementsToBeMarked.entrySet()) {
			if (elementsMarkedInThisStep.containsKey(elementsToBeMarkedEntry.getKey())) {
				for(IECComponent cause: elementsToBeMarkedEntry.getValue()) {
					if (!elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().contains(cause)) {
						elementsMarkedInThisStep.get(elementsToBeMarkedEntry.getKey()).
							getCausingElements().add(cause);	
					}
				}	
			} else {
				IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
				modification.setToolderived(true);
				modification.setAffectedElement(elementsToBeMarkedEntry.getKey());
				modification.getCausingElements().addAll(elementsToBeMarkedEntry.getValue());
				
				elementsMarkedInThisStep.put(elementsToBeMarkedEntry.getKey(), modification);
				this.getMarkedComponents().add(elementsToBeMarkedEntry.getKey());
				this.getIECChangePropagationDueToDataDependencies().getProgramModifications().
						add(modification);
				continuePropagation(version, elementsToBeMarkedEntry, elementsMarkedInThisStep);
			}
		 }
	}
	
	public <T extends IECComponent, S extends IECComponent>void continuePropagation(IECArchitectureVersion version, Map.Entry<T, Set<S>> entry, Map<EObject, AbstractModification<?,EObject>> elementsMarkedInThisStep) {
		if(entry.getKey() instanceof GlobalVariable) {
			calculateAndMarkGlobalVariableToConfigurationPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkGlobalVariableToProgramPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkGlobalVariableToFunctionBlockPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkGlobalVariableToMethodPropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof Function) {
			calculateAndMarkFunctionToProgramPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkFunctionToFunctionBlockPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkFunctionToMethodPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkFunctionToFunctionPropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof FunctionBlock) {
			calculateAndMarkFunctionBlockToProgramPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkFunctionBlockToMethodPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkFunctionBlockToFunctionBlockPropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof IECInterface) {
			calculateAndMarkInterfaceToProgramPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkInterfaceToFunctionBlockPropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof IECAbstractMethod) {
			calculateAndMarkAbstractMethodToInterfacePropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof IECAbstractProperty) {
			calculateAndMarkAbstractPropertyToInterfacePropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof IECMethod) {
			calculateAndMarkMethodToFunctionBlockPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkMethodToMethodPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkMethodToProgramPropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof IECProperty) {
			calculateAndMarkPropertyToFunctionBlockPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkPropertyToMethodPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkPropertyToProgramPropagation(version, elementsMarkedInThisStep);
			calculateAndMarkPropertyToConfigurationPropagation(version, elementsMarkedInThisStep);
		} else if (entry.getKey() instanceof Program) {
			calculateAndMarkProgramToConfigurationPropagation(version, elementsMarkedInThisStep);
		}
	}
	

	protected void setIECChangePropagationDueToDataDependency(IECChangePropagationDueToDataDependency changePropagationDueToDataDependencies) {
		this.changePropagationDueToDataDependencies = changePropagationDueToDataDependencies;
	}
	
	public IECChangePropagationDueToDataDependency getIECChangePropagationDueToDataDependencies() {
		return changePropagationDueToDataDependencies;
	}

	public Collection<IECComponent> getMarkedComponents() {
		return markedComponents;
	}

	public void setMarkedComponents(Collection<IECComponent> markedComponents) {
		this.markedComponents = markedComponents;
	}

	public void setChangePropagationDueToDataDependencies(
			IECChangePropagationDueToDataDependency changePropagationDueToDataDependencies) {
		this.changePropagationDueToDataDependencies = changePropagationDueToDataDependencies;
	}


}
