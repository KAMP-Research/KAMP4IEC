package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.function.Function;

import edu.kit.ipd.sdq.kamp.workplan.AbstractActivityElementType;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECMetadataFile;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECPerson;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECSourceFile;
import edu.kit.ipd.sdq.kamp4iec.model.IECFieldOfActivityAnnotations.IECTestCase;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECAbstractMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECAbstractProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IECProperty;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.ActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.SystemStep;

public enum IECActivityElementType implements AbstractActivityElementType {
	//IEC-related
	FUNCTIONBLOCK(FunctionBlock.class),
	GLOBALVARIABLE(GlobalVariable.class),
	CONFIGURATION(Configuration.class),
	PROGRAM(Program.class),
	FUNCTION(Function.class),
	INTERFACE(IECInterface.class),
	ABSTRACTPROPERTY(IECAbstractProperty.class),
	PROPERTY(IECProperty.class),
	ABSTRACTMETHOD(IECAbstractMethod.class),
	METHOD(IECMethod.class),
	//HMI-related
	ACTORSTEP(ActorStep.class),
	SYSTEMSTEP(SystemStep.class),
	// annotation-related
	SOURCECODEFILES(IECSourceFile.class),
	METADATAFILES(IECMetadataFile.class),
	TESTCASE(IECTestCase.class), 
	PERSON(IECPerson.class);
	private final Class<?> clazz;
	private final String name;
	
	private IECActivityElementType(Class<?> clazz) {
		this.clazz = clazz;
		this.name = clazz.getSimpleName();
	}

	@Override
	public Class<?> getElementClass() {
		return clazz;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public static IECActivityElementType[] getTopLevelArchitectureActivityElementTypes() {	
		return new IECActivityElementType[]{GLOBALVARIABLE, FUNCTIONBLOCK, CONFIGURATION, PROGRAM, FUNCTION, INTERFACE, METHOD, PROPERTY,  ABSTRACTPROPERTY, ABSTRACTMETHOD};
	}

	public static IECActivityElementType[] getUnitTestedlArchitectureActivityElementTypes() {	
		return new IECActivityElementType[]{FUNCTIONBLOCK, CONFIGURATION, PROGRAM, FUNCTION, INTERFACE, METHOD, ABSTRACTMETHOD};
	}

	public static IECActivityElementType[] getAcceptanceTestedlArchitectureActivityElementTypes() {	
		return new IECActivityElementType[]{FUNCTIONBLOCK, CONFIGURATION, PROGRAM};
	}
	
	

}
