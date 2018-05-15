package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.Set;

import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModificationmarksFactory;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModifyActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.HMIModificationmarks.HMIModifySystemStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.ActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.SystemStep;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;
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

public class IECModificationFactory {
	
	public static IECModifyConfiguration createIECModification(Configuration affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyConfiguration modification = IECModificationmarksFactory.eINSTANCE.createIECModifyConfiguration();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyProgram createIECModification(Program affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyProgram modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProgram();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyGlobalVariable createIECModification(GlobalVariable affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyGlobalVariable modification = IECModificationmarksFactory.eINSTANCE.createIECModifyGlobalVariable();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyFunctionBlock createIECModification(FunctionBlock affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyFunctionBlock modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunctionBlock();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyAbstractProperty createIECModification(IECAbstractProperty affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyAbstractProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractProperty();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyAbstractMethod createIECModification(IECAbstractMethod affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyAbstractMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyAbstractMethod();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyInterface createIECModification(IECInterface affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyInterface modification = IECModificationmarksFactory.eINSTANCE.createIECModifyInterface();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyProperty createIECModification(IECProperty affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyProperty modification = IECModificationmarksFactory.eINSTANCE.createIECModifyProperty();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyMethod createIECModification(IECMethod affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyMethod modification = IECModificationmarksFactory.eINSTANCE.createIECModifyMethod();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static IECModifyFunction createIECModification(Function affected, Set<IECComponent> causing, boolean toolDerived) {
		IECModifyFunction modification = IECModificationmarksFactory.eINSTANCE.createIECModifyFunction();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static HMIModifyActorStep createHMIModification(ActorStep affected, Set<Identifier> causing, boolean toolDerived) {
		HMIModifyActorStep modification = HMIModificationmarksFactory.eINSTANCE.createHMIModifyActorStep();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}
	
	public static HMIModifySystemStep createHMIModification(SystemStep affected, Set<Identifier> causing, boolean toolDerived) {
		HMIModifySystemStep modification = HMIModificationmarksFactory.eINSTANCE.createHMIModifySystemStep();
		modification.setToolderived(toolDerived);
		modification.setAffectedElement(affected);
		modification.getCausingElements().addAll(causing);
		return modification;
	}

}
