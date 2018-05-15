package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.NoSuchElementException;

import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.ActorStep;
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

public enum IECModificationType {
		SEED,
		CONFIGURATION,
		PROGRAM,
		FUNCTION,
		FUNCTIONBLOCK,
		METHOD,
		PROPERTY,
		INTERFACE,
		ABSTRACTMETHOD,
		ABSTRACTPROPERTY,
		GLOBALVARIABLE,
		
		SYSTEMSTEP,
		ACTORSTEP;
	public static IECModificationType getFomComponent(IECComponent comp) {
		if(comp instanceof Configuration) {
			return CONFIGURATION;
		} else if(comp instanceof Program) {
			return PROGRAM;
		} else if(comp instanceof Function) {
			return FUNCTION;
		} else if(comp instanceof FunctionBlock) {
			return FUNCTIONBLOCK;
		} else if(comp instanceof IECMethod) {
			return METHOD;
		} else if(comp instanceof IECProperty) {
			return PROPERTY;
		} else if(comp instanceof IECInterface) {
			return INTERFACE;
		} else if(comp instanceof IECAbstractMethod) {
			return ABSTRACTMETHOD;
		} else if(comp instanceof IECAbstractProperty) {
			return ABSTRACTPROPERTY;
		} else if(comp instanceof GlobalVariable) {
			return GLOBALVARIABLE;
		} else if(comp instanceof SystemStep) {
			return SYSTEMSTEP;
		} else if(comp instanceof ActorStep) {
			return ACTORSTEP;
		} else {
			throw new NoSuchElementException("IEC Component of Type <" + comp.toString() + "> not found.");
		}
	}
}
