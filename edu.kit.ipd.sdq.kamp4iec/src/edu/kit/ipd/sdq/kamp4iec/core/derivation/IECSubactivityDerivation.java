package edu.kit.ipd.sdq.kamp4iec.core.derivation;

import edu.kit.ipd.sdq.kamp.workplan.Activity;
import edu.kit.ipd.sdq.kamp.workplan.BasicActivity;
import edu.kit.ipd.sdq.kamp4iec.core.IECArchitectureVersion;
import edu.kit.ipd.sdq.kamp4iec.core.IECActivityElementType;
import edu.kit.ipd.sdq.kamp4iec.core.IECActivityType;
import edu.kit.ipd.sdq.kamp4iec.model.ComponentInternalDependencies.FunctionblockDependency;
import edu.kit.ipd.sdq.kamp4iec.model.ComponentInternalDependencies.MethodDependency;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Function;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethodImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Identifier;

public class IECSubactivityDerivation {

	public void deriveSubactivities(Identifier iecComponent, Activity parentActivity, IECArchitectureVersion version) {
		if (iecComponent instanceof GlobalVariable) {
			deriveSubactivity((GlobalVariable) iecComponent, parentActivity, version);
		} else if (iecComponent instanceof FunctionBlock) {
			deriveSubactivity((FunctionBlock) iecComponent, parentActivity, version);
		} else if (iecComponent instanceof Function) {
			deriveSubactivity((Function) iecComponent, parentActivity, version);
		} else if (iecComponent instanceof IECInterface) {
			deriveSubactivity((IECInterface) iecComponent, parentActivity, version);
		} /*else if (iecComponent instanceof IECMethod) {
			deriveSubactivity((IECMethod) iecComponent, parentActivity, version);
		} else if (iecComponent instanceof IECProperty) {
			deriveSubactivity((IECProperty) iecComponent, parentActivity, version);
		} else if (iecComponent instanceof Program) {
			deriveSubactivity((Program) iecComponent, parentActivity, version);
		} else if (iecComponent instanceof edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum) {
			deriveSubactivity((edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum) iecComponent, parentActivity, version);
		}*/
	}

	private void deriveSubactivity(GlobalVariable globalVariable, Activity parentActivity, IECArchitectureVersion version) {
		if (globalVariable instanceof GlobalVariable) {
			Configuration configuration = version.getConfiguration();
			for(Program program : configuration.getContainsProgram()) {
				for(FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
					for(GlobalVariable globVar : functionBlock.getAccessesGlobalVariable()) {
						if(globalVariable.getId() == globVar.getId()) {
							addSubActivity(globalVariable, IECActivityElementType.FUNCTIONBLOCK, functionBlock, parentActivity);
						}
					}
					for(IECMethodImplementation method : functionBlock.getHasMethod()) {
						for(GlobalVariable globVar : method.getAccessesGlobalVariable()) {
							if(globalVariable.getId() == globVar.getId()) {
								addSubActivity(globalVariable, IECActivityElementType.METHOD, method, parentActivity);
							}
						}
					}
				}
				for(GlobalVariable globVar : program.getAccessesGlobalVariable()) {
					if(globalVariable.getId() == globVar.getId()) {
						addSubActivity(globalVariable, IECActivityElementType.PROGRAM, program, parentActivity);
					}
				}
				for(GlobalVariable globVar : program.getDeclaresVariable()) {
					if(globalVariable.getId() == globVar.getId()) {
						addSubActivity(globalVariable, IECActivityElementType.PROGRAM, program, parentActivity);
					}
				}
			}
			for(GlobalVariable globVar : configuration.getDeclaresVariable()) {
				if(globalVariable.getId() == globVar.getId()) {
					addSubActivity(globalVariable, IECActivityElementType.CONFIGURATION, configuration, parentActivity);
				}
			}
			for(GlobalVariable globVar : configuration.getAccessesGlobalVariable()) {
				if(globalVariable.getId() == globVar.getId()) {
					addSubActivity(globalVariable, IECActivityElementType.CONFIGURATION, configuration, parentActivity);
				}
			}
		}
	}

	private void deriveSubactivity(FunctionBlock functionBlock, Activity parentActivity, IECArchitectureVersion version) {
		if (functionBlock instanceof FunctionBlock) {
			Configuration configuration = version.getConfiguration();
			for(Program program : configuration.getContainsProgram()) {
				for(FunctionBlock fb : program.getCallsFunctionBlock()) {
					if(functionBlock.getId() == fb.getId()) {
						addSubActivity(functionBlock, IECActivityElementType.PROGRAM, program, parentActivity);
					}
					for(FunctionblockDependency dependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
						if (dependency.getProvidedFunctionBlock().getId() == functionBlock.getId() && dependency.getRequiredResource().getId() == fb.getId()) {
							addSubActivity(functionBlock, IECActivityElementType.FUNCTIONBLOCK, fb, parentActivity);
						}
					}
				}
			}
		}
	}

	private void deriveSubactivity(Function function, Activity parentActivity, IECArchitectureVersion version) {
		if (function instanceof Function) {
			Configuration configuration = version.getConfiguration();
			for(Program program : configuration.getContainsProgram()) {
				for(FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
					for(Function func : functionBlock.getCallsFunction()) {
						if(function.getId() == func.getId()) {
							addSubActivity(function, IECActivityElementType.FUNCTIONBLOCK, functionBlock, parentActivity);
						}
						for(Function called : func.getCallsFunction()) {
							if(function.getId() == called.getId()) {
								addSubActivity(function, IECActivityElementType.FUNCTION, func, parentActivity);
							}
						}
					}
					for(IECMethodImplementation method : functionBlock.getHasMethod()) {
						for(Function func : method.getCallsFunction()) {
							if(function.getId() == func.getId()) {
								addSubActivity(function, IECActivityElementType.METHOD, method, parentActivity);
							}
						}
					}
				}
				for(Function func : program.getCallsFunction()) {
					if(function.getId() == func.getId()) {
						addSubActivity(function, IECActivityElementType.FUNCTION, func, parentActivity);
					}
				}
			}
		}
	}

	private void deriveSubactivity(IECInterface iecInterface, Activity parentActivity, IECArchitectureVersion version) {
		if (iecInterface instanceof IECInterface) {
			Configuration configuration = version.getConfiguration();
			for(Program program : configuration.getContainsProgram()) {
				for(IECInterface iecIn : program.getImplementsInterface()) {
					if(iecInterface.getId() == iecIn.getId()) {
						addSubActivity(iecInterface, IECActivityElementType.PROGRAM, program, parentActivity);
					}
				}
				for(FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
					for(IECInterface iecIn : functionBlock.getImplements()) {
						if(iecInterface.getId() == iecIn.getId()) {
							addSubActivity(iecInterface, IECActivityElementType.FUNCTIONBLOCK, functionBlock, parentActivity);
						}
					}
				}
			}
		}
	}

	private Activity addSubActivity(GlobalVariable globalVariable, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, globalVariable, globalVariable.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, globalVariable, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(FunctionBlock functionBlock, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, functionBlock, functionBlock.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, functionBlock, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(Function function, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, function, function.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, function, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(IECInterface iecInterface, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, iecInterface, iecInterface.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, iecInterface, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(Program program, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, program, program.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, program, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(IECProperty property, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, property, property.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, property, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(IECMethod method, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, method, method.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, method, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	private Activity addSubActivity(edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum iecEnum, IECActivityElementType elementType, IECComponent parentElement, Activity parentActivity) {
		Activity result = new Activity(IECActivityType.ARCHITECTUREMODELDIFF, elementType, iecEnum, iecEnum.getId(), null, parentActivity.getBasicActivity(), 
				generateDescription(parentElement, iecEnum, parentActivity.getBasicActivity()));
		parentActivity.addSubActivity(result);
		return parentActivity;
	}

	public static String generateDescription(Identifier superElement, Identifier subElement, BasicActivity action) {
		String result = "";
		if (action.equals(BasicActivity.ADD)) {
			result += "Add ";
		} else if (action.equals(BasicActivity.REMOVE)) {
			result += "Remove ";
		} else if (action.equals(BasicActivity.MODIFY)) {
			result += "Modify ";
		}
		result += subElement.eClass().getName() + " " + subElement.getId();
		if (action.equals(BasicActivity.ADD)) {
			result += " to ";
		} else if (action.equals(BasicActivity.REMOVE)) {
			result += " from ";
		} else if (action.equals(BasicActivity.MODIFY)) {
			result += " of ";
		}
		result += superElement.eClass().getName() + " " + superElement.getId() + ".";
		return result;
	}

}