package edu.kit.ipd.sdq.kamp4iec.core.derivation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.kit.ipd.sdq.kamp.architecture.ArchitectureModelLookup;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.ChangePropagationStep;
import edu.kit.ipd.sdq.kamp.workplan.AbstractActivityElementType;
import edu.kit.ipd.sdq.kamp.workplan.Activity;
import edu.kit.ipd.sdq.kamp.workplan.BasicActivity;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModifyActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModifySystemStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.ActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.SystemStep;
import edu.kit.ipd.sdq.kamp4iec.core.IECActivityElementType;
import edu.kit.ipd.sdq.kamp4iec.core.IECActivityType;
import edu.kit.ipd.sdq.kamp4iec.core.IECArchitectureVersion;
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
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.AbstractKAMP4IECModificationRepository;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECChangePropagationDueToDataDependency;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModificationmarksFactory;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyAbstractMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyAbstractProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyConfiguration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyFunction;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyFunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyGlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyProgram;
import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.IECModifyProperty;

public class IECInternalModificationDerivation {
	
//	private IECChangePropagationDueToDataDependency changePropagationDueToDataDependency =
//			IECModificationmarksFactory.eINSTANCE.createIECChangePropagationDueToDataDependency();
	
	public List<Activity> deriveInternalModifications(IECArchitectureVersion targetVersion) {
		List<Activity> activityList = new ArrayList<Activity>();
		if (targetVersion.getModificationMarkRepository() != null) {
//			this.findRelevantPropagationSteps(targetVersion.getModificationMarkRepository());
			this.deriveFunctionBlockModifications(targetVersion, activityList);
			this.deriveGlobalVariableModifications(targetVersion, activityList);
			this.deriveConfigurationModifications(targetVersion, activityList);
			this.deriveProgramModifications(targetVersion, activityList);
			this.deriveFunctionModifications(targetVersion, activityList);
			this.deriveAbstractMethodModifications(targetVersion, activityList);
			this.deriveMethodModifications(targetVersion, activityList);
			this.deriveAbstractPropertyModifications(targetVersion, activityList);
			this.derivePropertyModifications(targetVersion, activityList);
			this.deriveInterfaceModifications(targetVersion, activityList);
			this.deriveHMIModifications(targetVersion, activityList);
		}
		return activityList;
	}

//	private void findRelevantPropagationSteps(AbstractKAMP4IECModificationRepository<?> 
//			modificationRepository) {
//		for (ChangePropagationStep step: modificationRepository.getChangePropagationSteps()) {
//			if (step instanceof IECChangePropagationDueToDataDependency) {
//				this.setIECChangePropagationDueToDataDependency(
//						(IECChangePropagationDueToDataDependency) step);
//			}
//		}
//	}

	private void deriveFunctionBlockModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyFunctionBlock> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyFunctionBlock.class);
		for (IECModifyFunctionBlock modifyFunctionBlock : modifyComponents) {
			activityList.add(IECInternalModificationDerivation.createModificationActivity(
					modifyFunctionBlock, getCausingElementsNames(modifyFunctionBlock), IECActivityElementType.FUNCTIONBLOCK));
		}
	}
	
	private void deriveGlobalVariableModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyGlobalVariable> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyGlobalVariable.class);
		for (IECModifyGlobalVariable modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.GLOBALVARIABLE);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveConfigurationModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyConfiguration> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyConfiguration.class);
		for (IECModifyConfiguration modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.CONFIGURATION);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveProgramModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyProgram> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyProgram.class);
		for (IECModifyProgram modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.PROGRAM);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveFunctionModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyFunction> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyFunction.class);
		for (IECModifyFunction modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.FUNCTION);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveAbstractMethodModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyMethod> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyMethod.class);
		for (IECModifyMethod modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.ABSTRACTMETHOD);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveMethodModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyAbstractMethod> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyAbstractMethod.class);
		for (IECModifyAbstractMethod modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.METHOD);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveAbstractPropertyModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyAbstractProperty> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyAbstractProperty.class);
		for (IECModifyAbstractProperty modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.ABSTRACTPROPERTY);
			activityList.add(componentActivity);
		}
	}
	
	private void derivePropertyModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyProperty> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyProperty.class);
		for (IECModifyProperty modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.PROPERTY);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveInterfaceModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<IECModifyInterface> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion, IECModifyInterface.class);
		for (IECModifyInterface modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.INTERFACE);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveHMIModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		deriveActorStepModifications(targetVersion, activityList);
		deriveSystemStepModifications(targetVersion, activityList);
	}
	
	private void deriveActorStepModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<HMIModifyActorStep> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion.getHMIModificationRepository(), HMIModifyActorStep.class);
		for (HMIModifyActorStep modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.ACTORSTEP);
			activityList.add(componentActivity);
		}
	}
	
	private void deriveSystemStepModifications(IECArchitectureVersion targetVersion, List<Activity> activityList) {
		Collection<HMIModifySystemStep> modifyComponents = ArchitectureModelLookup.lookUpAllModificationMarksOfAType(targetVersion.getHMIModificationRepository(), HMIModifySystemStep.class);
		for (HMIModifySystemStep modifyComponent : modifyComponents) {
			Activity componentActivity = createModificationActivity(modifyComponent, 
					   IECActivityElementType.SYSTEMSTEP);
			activityList.add(componentActivity);
		}
	}

	public static List<String> getCausingElementsNames(AbstractModification<?, ?> modification) {
		List<String> causingElementNames = new LinkedList<String>();
		for (Object causingElement : modification.getCausingElements()) {
			if (causingElement instanceof GlobalVariable) {
				GlobalVariable causingModule = (GlobalVariable) causingElement;
				causingElementNames.add(causingModule.eClass().
						getName() + " \"" + causingModule.getId() + "\"");
			} else if (causingElement instanceof FunctionBlock) {
				FunctionBlock causingInterface = (FunctionBlock) causingElement;
				causingElementNames.add(causingInterface.eClass().
						getName() + " \"" + causingInterface.getId() + "\"");
			} else if (causingElement instanceof IECComponent) {
				IECComponent causingCompoenent = (IECComponent) causingElement;
				causingElementNames.add(causingCompoenent.eClass().
						getName() + " \"" + causingCompoenent.getId() + "\"");
			} 
		}
		return causingElementNames;
	}

	public static Activity createModificationActivity(AbstractModification<?, ?> modification,
			List<String> causingElementNames, AbstractActivityElementType activityElementType) {
		if (modification instanceof IECModifyFunctionBlock){
			FunctionBlock component = ((IECModifyFunctionBlock) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, component,
					component.getName(), causingElementNames, BasicActivity.MODIFY, "Modify " + component.eClass().getName() + ".");
		} else if (modification instanceof IECModifyGlobalVariable){
			GlobalVariable globalVariable = ((IECModifyGlobalVariable) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, globalVariable, globalVariable.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + globalVariable.eClass().getName() + ".");
		} else if (modification instanceof IECModifyConfiguration){
			Configuration config = ((IECModifyConfiguration) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, config, config.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + config.eClass().getName() + ".");
		} else if (modification instanceof IECModifyFunction){
			Function function = ((IECModifyFunction) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, function, function.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + function.eClass().getName() + ".");
		} else if (modification instanceof IECModifyMethod){
			IECMethod method = ((IECModifyMethod) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, method, method.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + method.eClass().getName() + ".");
		} else if (modification instanceof IECModifyAbstractMethod){
			IECAbstractMethod method = ((IECModifyAbstractMethod) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, method, method.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + method.eClass().getName() + ".");
		} else if (modification instanceof IECModifyProperty){
			IECProperty function = ((IECModifyProperty) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, function, function.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + function.eClass().getName() + ".");
		} else if (modification instanceof IECModifyAbstractProperty){
			IECAbstractProperty function = ((IECModifyAbstractProperty) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, function, function.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + function.eClass().getName() + ".");
		} else if (modification instanceof IECModifyProgram){
			Program program = ((IECModifyProgram) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, program, program.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + program.eClass().getName() + ".");
		} else if (modification instanceof IECModifyInterface){
			IECInterface iecInterface = ((IECModifyInterface) modification).getAffectedElement();
			return new Activity(IECActivityType.INTERNALMODIFICATIONMARK, activityElementType, iecInterface, iecInterface.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + iecInterface.eClass().getName() + ".");
		} else if (modification instanceof HMIModifyActorStep){
			ActorStep actorStep = ((HMIModifyActorStep) modification).getAffectedElement();
			return new Activity(IECActivityType.HMIMODIFICATIONMARK, activityElementType, actorStep, actorStep.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + actorStep.eClass().getName() + ".");
		} else if (modification instanceof HMIModifySystemStep){
			SystemStep systemStep = ((HMIModifySystemStep) modification).getAffectedElement();
			return new Activity(IECActivityType.HMIMODIFICATIONMARK, activityElementType, systemStep, systemStep.getName(), 
					causingElementNames, BasicActivity.MODIFY, "Modify " + systemStep.eClass().getName() + ".");
		} else {
			return null;
		}
	}

	public static Activity createModificationActivity(AbstractModification<?, ?> modification,
			AbstractActivityElementType activityElementType) {
		List<String> causingElementNames = getCausingElementsNames(modification);
		return createModificationActivity(modification, causingElementNames, activityElementType);
	}
	
//	protected IECChangePropagationDueToDataDependency getIECChangePropagationDueToDataDependency() {
//		return changePropagationDueToDataDependency;
//	}
//
//	protected void setIECChangePropagationDueToDataDependency(
//			IECChangePropagationDueToDataDependency changePropagationDueToDataDependency) {
//		this.changePropagationDueToDataDependency = changePropagationDueToDataDependency;
//	}
	
}
