package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.ipd.sdq.kamp.util.MapUtil;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.ActorStep;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.For;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.HMIElement;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.If;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Mode;
import edu.kit.ipd.sdq.kamp4hmi.model.Kamp4hmiModel.Step;
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
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IsMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.IsProperty;

public class IECArchitectureModelLookup {

	/**
	 * Looks up all {@link Program}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link Program}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<Program, Set<IECInterface>> lookUpProgramsOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<Program, Set<IECInterface>> results = new HashMap<Program, Set<IECInterface>>();
		for (Program program : version.getConfiguration().getInstantiatesProgram()) {
			for(IECInterface accessed : program.getInstantiatesInterface()) {
				for(IECInterface interf : interfaces) {
					putOrAddToMap(results, program, accessed, interf);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECInterface} which implement the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link IECInterface}s they are implementing.
	 */
	public static Map<FunctionBlock, Set<IECInterface>> lookUpFunctionBlocksOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<FunctionBlock, Set<IECInterface>> results = new HashMap<FunctionBlock, Set<IECInterface>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECInterface accessed : functionBlock.getInstantiatesInterface()) {
				for(IECInterface interf : interfaces) {
					putOrAddToMap(results, functionBlock, accessed, interf);
				}
			}
			for(IECInterface accessed : functionBlock.getImplements()) {
				for(IECInterface interf : interfaces) {
					putOrAddToMap(results, functionBlock, accessed, interf);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECInterface}s of the {@link IECArchitectureVersion}s {@link IECInterface} which extend the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link IECInterface}s and which {@link IECInterface}s they are extending.
	 */
	public static Map<IECInterface, Set<IECInterface>> lookUpInterfacesOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<IECInterface, Set<IECInterface>> results = new HashMap<IECInterface, Set<IECInterface>>();
		for (IECInterface calling : version.getIECRepository().getContainsInterface()) {
			for(IECInterface interf : interfaces) {
				if(calling.getExtendsInterface() != null) {
						putOrAddToMap(results, calling, calling.getExtendsInterface(), interf);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link GlobalVariable}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link GlobalVariable}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<GlobalVariable, Set<IECInterface>> lookUpGlobalVariablesOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<GlobalVariable, Set<IECInterface>> results = new HashMap<GlobalVariable, Set<IECInterface>>();
		for (GlobalVariable globalVariable : getGlobalVariablesOfVersion(version)) {
			if(globalVariable.getHasDerivedType() != null) {
				if(globalVariable.getHasDerivedType() instanceof IECInterface) {
					for(IECInterface interf : interfaces) {
						putOrAddToMap(results, globalVariable, (IECInterface)globalVariable.getHasDerivedType(), interf);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link Function}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link Function}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<Function, Set<IECInterface>> lookUpFunctionsOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<Function, Set<IECInterface>> results = new HashMap<Function, Set<IECInterface>>();
		for (Function function : version.getIECRepository().getContainsFunction()) {
			if(function.getHasDerivedReturnType() != null) {
				if(function.getHasDerivedReturnType() instanceof IECInterface) {
					for(IECInterface interf : interfaces) {
						putOrAddToMap(results, function, (IECInterface)function.getHasDerivedReturnType(), interf);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethod}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link IECMethod}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<IECMethod, Set<IECInterface>> lookUpMethodsOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<IECMethod, Set<IECInterface>> results = new HashMap<IECMethod, Set<IECInterface>>();
		for(FunctionBlock fb : version.getIECRepository().getContainsFunctionBlock()) {
			for (IECMethod method : fb.getHasMethod()) {
				if(method.getHasDerivedReturnType() != null) {
					if(method.getHasDerivedReturnType() instanceof IECInterface) {
						for(IECInterface interf : interfaces) {
							putOrAddToMap(results, method, (IECInterface)method.getHasDerivedReturnType(), interf);
						}
					}
				}
				for(IECInterface accessed : method.getInstantiatesInterface()) {
					for(IECInterface interf : interfaces) {
						putOrAddToMap(results, method, accessed, interf);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECAbstractMethod}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link IECAbstractMethod}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<IECAbstractMethod, Set<IECInterface>> lookUpAbstractMethodsOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<IECAbstractMethod, Set<IECInterface>> results = new HashMap<IECAbstractMethod, Set<IECInterface>>();
		for(IECInterface fb : version.getIECRepository().getContainsInterface()) {
			for (IECAbstractMethod method : fb.getHasMethod()) {
				if(method.getHasDerivedReturnType() != null) {
					if(method.getHasDerivedReturnType() instanceof IECInterface) {
						for(IECInterface interf : interfaces) {
							putOrAddToMap(results, method, (IECInterface)method.getHasDerivedReturnType(), interf);
						}
					}
				}
			}
		}
		for(IECInterface iecInter : version.getIECRepository().getContainsInterface()) {
			for(IECAbstractMethod method : iecInter.getHasMethod()) {
				for(IECInterface interf : interfaces) {
					putOrAddToMap(results, method, iecInter, interf);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECProperty}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link IECProperty}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<IECProperty, Set<IECInterface>> lookUpPropertiesOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<IECProperty, Set<IECInterface>> results = new HashMap<IECProperty, Set<IECInterface>>();
		for(FunctionBlock fb : version.getIECRepository().getContainsFunctionBlock()) {
			for (IECProperty property : fb.getHasProperty()) {
				if(property.getHasDerivedType() != null) {
					if(property.getHasDerivedType() instanceof IECInterface) {
						for(IECInterface interf : interfaces) {
							putOrAddToMap(results, property, (IECInterface)property.getHasDerivedType(), interf);
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECAbstractProperty}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link IECInterface}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param interfaces The {@link IECInterface}s to look up.
	 * @return A map of all {@link IECAbstractProperty}s and which {@link IECInterface}s they are accessing.
	 */
	public static Map<IECAbstractProperty, Set<IECInterface>> lookUpAbstractPropertiesOfInterface(
			IECArchitectureVersion version, Collection<IECInterface> interfaces) {
		Map<IECAbstractProperty, Set<IECInterface>> results = new HashMap<IECAbstractProperty, Set<IECInterface>>();
		for(IECInterface iecInterface : version.getIECRepository().getContainsInterface()) {
			for (IECAbstractProperty property : iecInterface.getHasProperty()) {
				if(property.getHasDerivedType() != null) {
					if(property.getHasDerivedType() instanceof IECInterface) {
						for(IECInterface interf : interfaces) {
							putOrAddToMap(results, property, (IECInterface)property.getHasDerivedType(), interf);
						}
					}
				}
			}
		}
		for(IECInterface iecInter : version.getIECRepository().getContainsInterface()) {
			for(IECAbstractProperty prop : iecInter.getHasProperty()) {
				for(IECInterface interf : interfaces) {
					putOrAddToMap(results, prop, iecInter, interf);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link Program}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link Program}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<Program, Set<FunctionBlock>> lookUpProgramsOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<Program, Set<FunctionBlock>> results = new HashMap<Program, Set<FunctionBlock>>();
		for (Program program : version.getConfiguration().getInstantiatesProgram()) {
			for(FunctionBlock functionBlock : functionBlocks) {
				for(FunctionBlock accessed : program.getInstantiatesFunctionBlock()) {
					putOrAddToMap(results, program, accessed, functionBlock);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<FunctionBlock, Set<FunctionBlock>> lookUpFunctionBlocksOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<FunctionBlock, Set<FunctionBlock>> results = new HashMap<FunctionBlock, Set<FunctionBlock>>();
		for(FunctionBlock calling : version.getIECRepository().getContainsFunctionBlock()) {
			for(FunctionBlock accessed : calling.getInstantiatesFunctionBlock()) {
				for(FunctionBlock funct : functionBlocks) {
					putOrAddToMap(results, calling, accessed, funct);
				}
			}
			for(FunctionBlock accessed : calling.getExtendsFunctionBlock()) {
				for(FunctionBlock funct : functionBlocks) {
					putOrAddToMap(results, calling, accessed, funct);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethod}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link IECMethod}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<IECMethod, Set<FunctionBlock>> lookUpMethodsOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<IECMethod, Set<FunctionBlock>> results = new HashMap<IECMethod, Set<FunctionBlock>>();
		for (FunctionBlock fb : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod calling : fb.getHasMethod()) {
				if(calling.getHasDerivedReturnType() != null) {
					for(FunctionBlock funct : functionBlocks) {
						if(calling.getHasDerivedReturnType() instanceof FunctionBlock) {
							putOrAddToMap(results, calling, (FunctionBlock) calling.getHasDerivedReturnType(), funct);
						}
					}
				}
				for(FunctionBlock accessed : calling.getInstantiatesFunctionBlock()) {
					for(FunctionBlock funct : functionBlocks) {
						putOrAddToMap(results, calling, accessed, funct);
					}
				}
				for(FunctionBlock funct : functionBlocks) {
					putOrAddToMap(results, calling, fb, funct);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECAbstractMethod}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link IECAbstractMethod}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<IECAbstractMethod, Set<FunctionBlock>> lookUpAbstractMethodsOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<IECAbstractMethod, Set<FunctionBlock>> results = new HashMap<IECAbstractMethod, Set<FunctionBlock>>();
		for (IECInterface iecInterface : version.getIECRepository().getContainsInterface()) {
			for(IECAbstractMethod calling : iecInterface.getHasMethod()) {
				if(calling.getHasDerivedReturnType() != null) {
					for(FunctionBlock funct : functionBlocks) {
						if(calling.getHasDerivedReturnType() instanceof FunctionBlock) {
							putOrAddToMap(results, calling, (FunctionBlock) calling.getHasDerivedReturnType(), funct);
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECProperty}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link IECProperty}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<IECProperty, Set<FunctionBlock>> lookUpPropertiesOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<IECProperty, Set<FunctionBlock>> results = new HashMap<IECProperty, Set<FunctionBlock>>();
		for (FunctionBlock fb : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECProperty calling : fb.getHasProperty()) {
				if(calling.getHasDerivedType() != null) {
					for(FunctionBlock funct : functionBlocks) {
						if(calling.getHasDerivedType() instanceof FunctionBlock) {
							putOrAddToMap(results, calling, (FunctionBlock) calling.getHasDerivedType(), funct);
						}
					}
				}
				for(FunctionBlock funct : functionBlocks) {
					putOrAddToMap(results, calling, fb, funct);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECAbstractProperty}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link IECAbstractProperty}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<IECAbstractProperty, Set<FunctionBlock>> lookUpAbstractPropertiesOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<IECAbstractProperty, Set<FunctionBlock>> results = new HashMap<IECAbstractProperty, Set<FunctionBlock>>();
		for (IECInterface iecInterface : version.getIECRepository().getContainsInterface()) {
			for(IECAbstractProperty calling : iecInterface.getHasProperty()) {
				if(calling.getHasDerivedType() != null) {
					for(FunctionBlock funct : functionBlocks) {
						if(calling.getHasDerivedType() instanceof FunctionBlock) {
							putOrAddToMap(results, calling, (FunctionBlock) calling.getHasDerivedType(), funct);
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link GlobalVariable}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link GlobalVariable}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<GlobalVariable, Set<FunctionBlock>> lookUpGlobalVariablesOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<GlobalVariable, Set<FunctionBlock>> results = new HashMap<GlobalVariable, Set<FunctionBlock>>();
		for (GlobalVariable calling : getGlobalVariablesOfVersion(version)) {
			if(calling.getHasDerivedType() != null) {
				for(FunctionBlock funct : functionBlocks) {
					if(calling.getHasDerivedType() instanceof FunctionBlock) {
						putOrAddToMap(results, calling, (FunctionBlock) calling.getHasDerivedType(), funct);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link Function}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link Function}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<Function, Set<FunctionBlock>> lookUpFunctionsOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<Function, Set<FunctionBlock>> results = new HashMap<Function, Set<FunctionBlock>>();
		for (Function calling : version.getIECRepository().getContainsFunction()) {
			if(calling.getHasDerivedReturnType() != null) {
				for(FunctionBlock funct : functionBlocks) {
					if(calling.getHasDerivedReturnType() instanceof FunctionBlock) {
						putOrAddToMap(results, calling, (FunctionBlock) calling.getHasDerivedReturnType(), funct);
					}
				}
			}
			for(FunctionBlock accessed : calling.getCallsFunctionBlockConstructor()) {
				for(FunctionBlock funct : functionBlocks) {
						putOrAddToMap(results, calling, accessed, funct);
				}
			}
		}
		return results;
	}
	
	

	/**
	 * Looks up all {@link IECMethod}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link Function}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functions The {@link Function}s to look up.
	 * @return A map of all {@link IECMethod}s and which {@link Function}s they are accessing.
	 */
	public static Map<IECMethod, Set<Function>> lookUpMethodsOfFunction(
			IECArchitectureVersion version, Collection<Function> functions) {
		Map<IECMethod, Set<Function>> results = new HashMap<IECMethod, Set<Function>>();
		for (FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod method : functionBlock.getHasMethod()) {
				for(Function function : functions) {
					for(Function accessed : method.getCallsFunction()) {
						putOrAddToMap(results, method, accessed, function);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link Function}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functions The {@link Function}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link Function}s they are accessing.
	 */
	public static Map<FunctionBlock, Set<Function>> lookUpFunctionBlocksOfFunction(
			IECArchitectureVersion version, Collection<Function> functions) {
		Map<FunctionBlock, Set<Function>> results = new HashMap<FunctionBlock, Set<Function>>();
		for (FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(Function function : functions) {
				for(Function accessed : functionBlock.getCallsFunction()) {
					putOrAddToMap(results, functionBlock, accessed, function);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link Program}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link Function}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functions The {@link Function}s to look up.
	 * @return A map of all {@link Program}s and which {@link Function}s they are accessing.
	 */
	public static Map<Program, Set<Function>> lookUpProgramsOfFunction(
			IECArchitectureVersion version, Collection<Function> functions) {
		Map<Program, Set<Function>> results = new HashMap<Program, Set<Function>>();
		for (Program program : version.getConfiguration().getInstantiatesProgram()) {
			for(Function function : functions) {
				for(Function accessed : program.getCallsFunction()) {
					putOrAddToMap(results, program, accessed, function);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link Function}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link Function}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functions The {@link Function}s to look up.
	 * @return A map of all {@link Function}s and which {@link Function}s they are accessing.
	 */
	public static Map<Function, Set<Function>> lookUpFunctionsOfFunction(
			IECArchitectureVersion version, Collection<Function> functions) {
		Map<Function, Set<Function>> results = new HashMap<Function, Set<Function>>();
		for(Function callingFunction : version.getIECRepository().getContainsFunction()) {
			for(Function function : functions) {
				for(Function accessed : callingFunction.getCallsFunction()) {
					putOrAddToMap(results, callingFunction, accessed, function);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethod}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link GlobalVariable}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param globalVariables The {@link GlobalVariable}s to look up.
	 * @return A map of all {@link IECMethod}s and which {@link GlobalVariable}s they are accessing.
	 */
	public static Map<IECMethod, Set<GlobalVariable>> lookUpMethodsOfGlobalVariable(
			IECArchitectureVersion version, Collection<GlobalVariable> globalVariables) {
		Map<IECMethod, Set<GlobalVariable>> results = new HashMap<IECMethod, Set<GlobalVariable>>();
		for (FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod method : functionBlock.getHasMethod()) {
				for(GlobalVariable globVar : globalVariables) {
					for(GlobalVariable accessed : method.getReadsGlobalVariable()) {
						putOrAddToMap(results, method, accessed, globVar);
					}
					for(GlobalVariable accessed : method.getWritesGlobalVariable()) {
						putOrAddToMap(results, method, accessed, globVar);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link GlobalVariable}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param globalVariables The {@link GlobalVariable}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link GlobalVariable}s they are accessing.
	 */
	public static Map<FunctionBlock, Set<GlobalVariable>> lookUpFunctionBlocksOfGlobalVariable(
			IECArchitectureVersion version, Collection<GlobalVariable> globalVariables) {
		Map<FunctionBlock, Set<GlobalVariable>> results = new HashMap<FunctionBlock, Set<GlobalVariable>>();
		for (FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(GlobalVariable globVar : globalVariables) {
				for(GlobalVariable accessed : functionBlock.getReadsGlobalVariable()) {
					putOrAddToMap(results, functionBlock, accessed, globVar);
				}
				for(GlobalVariable accessed : functionBlock.getWritesGlobalVariable()) {
					putOrAddToMap(results, functionBlock, accessed, globVar);
				}
			}
		} 
		return results;
	}

	/**
	 * Looks up the {@link Program}s of the {@link IECArchitectureVersion} which access the given {@link GlobalVariable}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param globalVariables The {@link GlobalVariable}s to look up.
	 * @return The {@link Program}s which access the {@link GlobalVariable}s in a Map.
	 */
	public static Map<Program, Set<GlobalVariable>> lookUpProgramsOfGlobalVariable(
			IECArchitectureVersion version, Collection<GlobalVariable> globalVariables) {
		Map<Program, Set<GlobalVariable>> results = new HashMap<Program, Set<GlobalVariable>>();
		for(Program prog : version.getConfiguration().getInstantiatesProgram()) {
			for(GlobalVariable toCompare : globalVariables) {
				for(GlobalVariable accessed : prog.getReadsGlobalVariable()) {
					putOrAddToMap(results, prog, accessed, toCompare);
				}
				for(GlobalVariable accessed : prog.getWritesGlobalVariable()) {
					putOrAddToMap(results, prog, accessed, toCompare);
				}
				for(GlobalVariable accessed : prog.getDeclaresGlobalVariable()) {
					putOrAddToMap(results, prog, accessed, toCompare);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link Configuration} of the {@link IECArchitectureVersion} which access the given {@link GlobalVariable}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param globalVariables The {@link GlobalVariable}s to look up.
	 * @return The {@link Configuration} which accesses the {@link GlobalVariable}s in a Map.
	 */
	public static Map<Configuration, Set<GlobalVariable>> lookUpConfigurationOfGlobalVariable(
			IECArchitectureVersion version, Collection<GlobalVariable> globalVariables) {
		Map<Configuration, Set<GlobalVariable>> results = new HashMap<Configuration, Set<GlobalVariable>>();
		Configuration config = version.getConfiguration();
		for(GlobalVariable toCompare : globalVariables) {
			for(GlobalVariable declared : config.getDeclaresGlobalVariable()) {
				putOrAddToMap(results, config, declared, toCompare);
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link IECInterface} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractMethod}s to look up.
	 * @return The {@link IECInterface}s which access the {@link IECAbstractMethod}s in a Map.
	 */
	public static Map<IECInterface, Set<IECAbstractMethod>> lookUpInterfacesOfAbstractMethod(
			IECArchitectureVersion version, Collection<IECAbstractMethod> methods) {
		Map<IECInterface, Set<IECAbstractMethod>> results = new HashMap<IECInterface, Set<IECAbstractMethod>>();
		for(IECInterface iecInterface : version.getIECRepository().getContainsInterface()) {
			for(IECAbstractMethod toCompare: methods) {
				for(IECAbstractMethod method: iecInterface.getHasMethod()) {
					putOrAddToMap(results, iecInterface, method, toCompare);
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Program} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractMethod}s to look up.
	 * @return The {@link Program}s which access the {@link IECMethod}s in a Map.
	 */
	public static Map<Program, Set<IECAbstractMethod>> lookUpProgramsOfAbstractMethod(
			IECArchitectureVersion version, Collection<IECAbstractMethod> methods) {
		Map<Program, Set<IECAbstractMethod>> results = new HashMap<Program, Set<IECAbstractMethod>>();
		for(Program prog : version.getConfiguration().getInstantiatesProgram()) {
			for(IECAbstractMethod toCompare: methods) {
				for(IsMethod accessed: prog.getCallsMethod()) {
					if(accessed instanceof IECAbstractMethod)
					putOrAddToMap(results, prog, (IECAbstractMethod)accessed, toCompare);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link FunctionBlock} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractMethod}s to look up.
	 * @return The {@link FunctionBlock}s which access the {@link IECAbstractMethod}s in a Map.
	 */
	public static Map<FunctionBlock, Set<IECAbstractMethod>> lookUpFunctionBlocksOfAbstractMethod(
			IECArchitectureVersion version, Collection<IECAbstractMethod> methods) {
		Map<FunctionBlock, Set<IECAbstractMethod>> results = new HashMap<FunctionBlock, Set<IECAbstractMethod>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECAbstractMethod toCompare: methods) {
				for(IsMethod method: functionBlock.getCallsMethod()) {
					if(method instanceof IECAbstractMethod) {
						putOrAddToMap(results, functionBlock, (IECAbstractMethod)method, toCompare);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link IECMethod} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractMethod}s to look up.
	 * @return The {@link IECMethod}s which access the {@link IECAbstractMethod}s in a Map.
	 */
	public static Map<IECMethod, Set<IECAbstractMethod>> lookUpMethodsOfAbstractMethod(
			IECArchitectureVersion version, Collection<IECAbstractMethod> methods) {
		Map<IECMethod, Set<IECAbstractMethod>> results = new HashMap<IECMethod, Set<IECAbstractMethod>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod calling: functionBlock.getHasMethod()) {
				for(IECAbstractMethod toCompare: methods) {
					if(calling.getImplements() != null) {
						putOrAddToMap(results, calling, calling.getImplements(), toCompare);
					}
					for(IsMethod accessed : calling.getCallsMethod()) {
						if(accessed instanceof IECAbstractMethod) {
							putOrAddToMap(results, calling, (IECAbstractMethod) accessed, toCompare);
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link IECInterface} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractProperty}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractProperty}s to look up.
	 * @return The {@link IECInterface}s which access the {@link IECAbstractProperty}s in a Map.
	 */
	public static Map<IECInterface, Set<IECAbstractProperty>> lookUpInterfacesOfAbstractProperty(
			IECArchitectureVersion version, Collection<IECAbstractProperty> properties) {
		Map<IECInterface, Set<IECAbstractProperty>> results = new HashMap<IECInterface, Set<IECAbstractProperty>>();
		for(IECInterface iecInterface : version.getIECRepository().getContainsInterface()) {
			for(IECAbstractProperty toCompare: properties) {
				for(IECAbstractProperty property: iecInterface.getHasProperty()) {
					putOrAddToMap(results, iecInterface, property, toCompare);
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Program} of the {@link IECArchitectureVersion} which access the given {@link IECMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethod}s to look up.
	 * @return The {@link Program}s which access the {@link IECMethod}s in a Map.
	 */
	public static Map<Program, Set<IECAbstractProperty>> lookUpProgramsOfAbstractProperty(
			IECArchitectureVersion version, Collection<IECAbstractProperty> properties) {
		Map<Program, Set<IECAbstractProperty>> results = new HashMap<Program, Set<IECAbstractProperty>>();
		for(Program prog : version.getConfiguration().getInstantiatesProgram()) {
			for(IECAbstractProperty toCompare: properties) {
				for(IsProperty property: prog.getReadsProperty()) {
					if(property instanceof IECAbstractProperty)
						putOrAddToMap(results, prog, (IECAbstractProperty)property, toCompare);
				}
				for(IsProperty property: prog.getWritesProperty()) {
					if(property instanceof IECAbstractProperty)
						putOrAddToMap(results, prog, (IECAbstractProperty)property, toCompare);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link FunctionBlock} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractProperty}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractProperty}s to look up.
	 * @return The {@link FunctionBlock}s which access the {@link IECAbstractProperty}s in a Map.
	 */
	public static Map<FunctionBlock, Set<IECAbstractProperty>> lookUpFunctionBlocksOfAbstractProperty(
			IECArchitectureVersion version, Collection<IECAbstractProperty> properties) {
		Map<FunctionBlock, Set<IECAbstractProperty>> results = new HashMap<FunctionBlock, Set<IECAbstractProperty>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECAbstractProperty toCompare: properties) {
				for(IsProperty method: functionBlock.getReadsProperty()) {
					if(method instanceof IECAbstractProperty) {
						putOrAddToMap(results, functionBlock, (IECAbstractProperty)method, toCompare);
					}
				}
				for(IsProperty method: functionBlock.getWritesProperty()) {
					if(method instanceof IECAbstractProperty) {
						putOrAddToMap(results, functionBlock, (IECAbstractProperty)method, toCompare);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link IECMethod} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractProperty}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractProperty}s to look up.
	 * @return The {@link IECMethod}s which access the {@link IECAbstractProperty}s in a Map.
	 */
	public static Map<IECMethod, Set<IECAbstractProperty>> lookUpMethodsOfAbstractProperty(
			IECArchitectureVersion version, Collection<IECAbstractProperty> properties) {
		Map<IECMethod, Set<IECAbstractProperty>> results = new HashMap<IECMethod, Set<IECAbstractProperty>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod calling : functionBlock.getHasMethod()) {
				for(IECAbstractProperty toCompare: properties) {
					for(IsProperty property: functionBlock.getReadsProperty()) {
						if(property instanceof IECAbstractProperty) {
							putOrAddToMap(results, calling, (IECAbstractProperty) property, toCompare);
						}
					}
					for(IsProperty property: functionBlock.getWritesProperty()) {
						if(property instanceof IECAbstractProperty) {
							putOrAddToMap(results, calling, (IECAbstractProperty) property, toCompare);
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link IECProperty} of the {@link IECArchitectureVersion} which access the given {@link IECAbstractMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECAbstractMethod}s to look up.
	 * @return The {@link IECProperty}s which access the {@link IECAbstractMethod}s in a Map.
	 */
	public static Map<IECProperty, Set<IECAbstractProperty>> lookUpPropertiesOfAbstractProperty(
			IECArchitectureVersion version, Collection<IECAbstractProperty> properties) {
		Map<IECProperty, Set<IECAbstractProperty>> results = new HashMap<IECProperty, Set<IECAbstractProperty>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECProperty calling: functionBlock.getHasProperty()) {
				for(IECAbstractProperty toCompare: properties) {
					if(calling.getImplements() != null) {
						putOrAddToMap(results, calling, calling.getImplements(), toCompare);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link FunctionBlock} which access the given {@link IECMethod}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param methods The {@link IECMethod}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link IECMethod}s they are accessing.
	 */
	public static Map<FunctionBlock, Set<IECMethod>> lookUpFunctionBlocksOfMethod(
			IECArchitectureVersion version, Collection<IECMethod> methods) {
		Map<FunctionBlock, Set<IECMethod>> results = new HashMap<FunctionBlock, Set<IECMethod>>();
		for(FunctionBlock calling : version.getIECRepository().getContainsFunctionBlock()) {
			for(IsMethod accessed : calling.getCallsMethod()) {
				if(accessed instanceof IECMethod) {
					for(IECMethod compared : methods) {
						putOrAddToMap(results, calling, (IECMethod) accessed, compared);
					}
				}
			}
//			Üver diese Verbindung soll nicht propagiert werden, einen ganzen FB zu markieren wenn sich eine Methode ändert macht keinen Sinn
//			for(IECMethod accessed : calling.getHasMethod()) {
//				for(IECMethod compared : methods) {
//					putOrAddToMap(results, calling, (IECMethod) accessed, compared);
//				}
//			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethod}s of the {@link IECArchitectureVersion}s {@link IECMethod} which access the given {@link IECMethod}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param methods The {@link IECMethod}s to look up.
	 * @return A map of all {@link IECMethod}s and which {@link IECMethod}s they are accessing.
	 */
	public static Map<IECMethod, Set<IECMethod>> lookUpMethodsOfMethod(
			IECArchitectureVersion version, Collection<IECMethod> methods) {
		Map<IECMethod, Set<IECMethod>> results = new HashMap<IECMethod, Set<IECMethod>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod calling : functionBlock.getHasMethod()) {
				for(IECMethod compared : methods) {
					for(IsMethod accessed : calling.getCallsMethod()) {
						if(accessed instanceof IECMethod) {
							putOrAddToMap(results, calling, (IECMethod)accessed, compared);
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Program} of the {@link IECArchitectureVersion} which access the given {@link IECMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethod}s to look up.
	 * @return The {@link Program}s which access the {@link IECMethod}s in a Map.
	 */
	public static Map<Program, Set<IECMethod>> lookUpProgramsOfMethod(
			IECArchitectureVersion version, Collection<IECMethod> methods) {
		Map<Program, Set<IECMethod>> results = new HashMap<Program, Set<IECMethod>>();
		for(Program prog : version.getConfiguration().getInstantiatesProgram()) {
			for(IECMethod toCompare: methods) {
				for(IsMethod accessed: prog.getCallsMethod()) {
					if(accessed instanceof IECMethod)
					putOrAddToMap(results, prog, (IECMethod)accessed, toCompare);
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up all {@link IECMethod}s of the {@link IECArchitectureVersion}s {@link IECMethod} which access the given {@link IECProperty}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param properties The {@link IECProperty}s to look up.
	 * @return The {@link IECMethod}s which access the {@link IECProperty}s in a Map.
	 */
	public static Map<IECMethod, Set<IECProperty>> lookUpMethodsOfProperty(
			IECArchitectureVersion version, Collection<IECProperty> properties) {
		Map<IECMethod, Set<IECProperty>> results = new HashMap<IECMethod, Set<IECProperty>>();
		for(FunctionBlock functionBlock : version.getIECRepository().getContainsFunctionBlock()) {
			for(IECMethod calling : functionBlock.getHasMethod()) {
				for(IsProperty accessed : calling.getReadsProperty()) {
					if(accessed instanceof IECProperty) {
						for(IECProperty compared : properties) {
							putOrAddToMap(results, calling, (IECProperty)accessed, compared);
						}
					}
				}
				for(IsProperty accessed : calling.getWritesProperty()) {
					if(accessed instanceof IECProperty) {
						for(IECProperty compared : properties) {
							putOrAddToMap(results, calling, (IECProperty)accessed, compared);
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECMethod} which access the given {@link IECProperty}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param properties The {@link IECProperty}s to look up.
	 * @return The {@link FunctionBlock}s which access the {@link IECProperty}s in a Map.
	 */
	public static Map<FunctionBlock, Set<IECProperty>> lookUpFunctionBlocksOfProperty(
			IECArchitectureVersion version, Collection<IECProperty> properties) {
		Map<FunctionBlock, Set<IECProperty>> results = new HashMap<FunctionBlock, Set<IECProperty>>();
		for(FunctionBlock calling : version.getIECRepository().getContainsFunctionBlock()) {
			for(IsProperty accessed : calling.getReadsProperty()) {
				if(accessed instanceof IECProperty) {
					for(IECProperty compared : properties) {
						putOrAddToMap(results, calling, (IECProperty)accessed, compared);
					}
				}
			}
			for(IsProperty accessed : calling.getWritesProperty()) {
				if(accessed instanceof IECProperty) {
					for(IECProperty compared : properties) {
						putOrAddToMap(results, calling, (IECProperty)accessed, compared);
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Program} of the {@link IECArchitectureVersion} which access the given {@link IECMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethod}s to look up.
	 * @return The {@link Program}s which access the {@link IECMethod}s in a Map.
	 */
	public static Map<Program, Set<IECProperty>> lookUpProgramsOfProperty(
			IECArchitectureVersion version, Collection<IECProperty> properties) {
		Map<Program, Set<IECProperty>> results = new HashMap<Program, Set<IECProperty>>();
		for(Program prog : version.getConfiguration().getInstantiatesProgram()) {
			for(IECProperty toCompare: properties) {
				for(IsProperty property: prog.getReadsProperty()) {
					if(property instanceof IECProperty)
						putOrAddToMap(results, prog, (IECProperty)property, toCompare);
				}
				for(IsProperty property: prog.getWritesProperty()) {
					if(property instanceof IECProperty)
						putOrAddToMap(results, prog, (IECProperty)property, toCompare);
				}
			}
		}
		return results;
	}
	
	//KAMP4HMI lookup methods

	public static Map<SystemStep, Set<FunctionBlock>> lookUpSystemStepsOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<SystemStep, Set<FunctionBlock>> results = new HashMap<SystemStep, Set<FunctionBlock>>();
		for(HMIElement hmiElement : version.getHMIRepository().getHmiElement()) {
			if(hmiElement instanceof SystemStep) {
				for(Mode mode: ((SystemStep)hmiElement).getMode()) {
					for(FunctionBlock accessed: mode.getAffectedFunctionBlocks()) {
						for(FunctionBlock toCompare: functionBlocks) {
							putOrAddToMap(results, (SystemStep)hmiElement, accessed, toCompare);
						}
					}
				}
			}
		}
		return results;
	}

	public static Map<SystemStep, Set<IECMethod>> lookUpSystemStepsOfMethod(
			IECArchitectureVersion version, Collection<IECMethod> methods) {
		Map<SystemStep, Set<IECMethod>> results = new HashMap<SystemStep, Set<IECMethod>>();
		for(HMIElement hmiElement : version.getHMIRepository().getHmiElement()) {
			if(hmiElement instanceof SystemStep) {
				for(Mode mode: ((SystemStep)hmiElement).getMode()) {
					for(IsMethod accessed: mode.getAffectedMethods()) {
						if(accessed instanceof IECMethod) {
							for(IECMethod toCompare: methods) {
								putOrAddToMap(results, (SystemStep)hmiElement, (IECMethod)accessed, toCompare);
							}
						}
					}
				}
			}
		}
		return results;
	}

	public static Map<ActorStep, Set<SystemStep>> lookUpActorStepsOfSystemStep(
			IECArchitectureVersion version, Collection<SystemStep> systemSteps) {
		Map<ActorStep, Set<SystemStep>> results = new HashMap<ActorStep, Set<SystemStep>>();
		for(SystemStep toCompare : systemSteps) {
			if(toCompare.getSuccessor() instanceof If) {
				HMIElement tSuccessor = ((If)toCompare).getTrueSuccessor();
				HMIElement fSuccessor = ((If)toCompare).getFalseSuccessor();
				if(tSuccessor instanceof ActorStep)
					putOrAddToMap(results, (ActorStep)tSuccessor, toCompare, toCompare);
				if(fSuccessor instanceof ActorStep)
					putOrAddToMap(results, (ActorStep)fSuccessor, toCompare, toCompare);
			} else if (toCompare.getSuccessor() instanceof For) {
				HMIElement tSuccessor = ((For)toCompare).getLoopSuccessor();
				HMIElement fSuccessor = ((For)toCompare).getLoopEndSuccessor();
				if(tSuccessor instanceof ActorStep)
					putOrAddToMap(results, (ActorStep)tSuccessor, toCompare, toCompare);
				if(fSuccessor instanceof ActorStep)
					putOrAddToMap(results, (ActorStep)fSuccessor, toCompare, toCompare);
			} else if (toCompare.getSuccessor() instanceof ActorStep) {
				putOrAddToMap(results, (ActorStep)toCompare.getSuccessor(), toCompare, toCompare);
			}
		}
		return results;
	}

	public static Map<ActorStep, Set<HMIElement>> lookUpActorStepsOfActorStep(
			IECArchitectureVersion version, Collection<ActorStep> actorSteps) {
		Map<ActorStep, Set<HMIElement>> results = new HashMap<ActorStep, Set<HMIElement>>();
		for(ActorStep toCompare : actorSteps) {
			if(toCompare.getSuccessor() instanceof If) {
					HMIElement tSuccessor = ((If)toCompare.getSuccessor()).getTrueSuccessor();
					HMIElement fSuccessor = ((If)toCompare.getSuccessor()).getFalseSuccessor();
					if(tSuccessor instanceof ActorStep)
						putOrAddToMap(results, (ActorStep)tSuccessor, toCompare.getSuccessor(), toCompare.getSuccessor());
					if(fSuccessor instanceof ActorStep)
						putOrAddToMap(results, (ActorStep)fSuccessor, toCompare.getSuccessor(), toCompare.getSuccessor());
			} else if (toCompare.getSuccessor() instanceof For) {
					HMIElement tSuccessor = ((For)toCompare.getSuccessor()).getLoopSuccessor();
					HMIElement fSuccessor = ((For)toCompare.getSuccessor()).getLoopEndSuccessor();
					if(tSuccessor instanceof ActorStep)
						putOrAddToMap(results, (ActorStep)tSuccessor, toCompare.getSuccessor(), toCompare.getSuccessor());
					else if (!(tSuccessor instanceof SystemStep)) {
						results.putAll(lookUpActorStepsOfHMIElement(version, tSuccessor));
					}
					if(fSuccessor instanceof ActorStep)
						putOrAddToMap(results, (ActorStep)fSuccessor, toCompare, toCompare);
					else if (!(tSuccessor instanceof SystemStep)) {
						results.putAll(lookUpActorStepsOfHMIElement(version, fSuccessor));
					}
			} else if (toCompare.getSuccessor() instanceof ActorStep) {
					putOrAddToMap(results, (ActorStep)toCompare.getSuccessor(), toCompare, toCompare);
			}
		}
		return results;
	}
	
	public static Map<ActorStep, Set<HMIElement>> lookUpActorStepsOfHMIElement(
			IECArchitectureVersion version, HMIElement hmiElement) {
		Map<ActorStep, Set<HMIElement>> results = new HashMap<ActorStep, Set<HMIElement>>();
		if(hmiElement instanceof If) {
			HMIElement tSuccessor = ((If)hmiElement).getTrueSuccessor();
			HMIElement fSuccessor = ((If)hmiElement).getFalseSuccessor();
			if(tSuccessor instanceof ActorStep) {
				putOrAddToMap(results, (ActorStep)tSuccessor, hmiElement, hmiElement);
			} else if (!(tSuccessor instanceof SystemStep)) {
				results.putAll(lookUpActorStepsOfHMIElement(version, fSuccessor));
			}
		} else 
			if(hmiElement instanceof For) {
				HMIElement tSuccessor = ((For)hmiElement).getLoopSuccessor();
				HMIElement fSuccessor = ((For)hmiElement).getLoopEndSuccessor();
				if(tSuccessor instanceof ActorStep) {
					putOrAddToMap(results, (ActorStep)tSuccessor, hmiElement, hmiElement);
				} else if (!(tSuccessor instanceof SystemStep)) {
					results.putAll(lookUpActorStepsOfHMIElement(version, fSuccessor));
				}
			}
		return results;
	}
	
	
	
//	/**
//	 * Looks up the {@link Configuration} of the {@link IECArchitectureVersion} which access the given {@link IECMethod}s and lists it in a Map.
//	 * @param version The current {@link IECArchitectureVersion}.
//	 * @param method The {@link IECMethod}s to look up.
//	 * @return The {@link Configuration} which accesses the {@link IECMethod}s in a Map.
//	 */
//	public static Map<Configuration, Set<IECProperty>> lookUpConfigurationsOfProperty(
//			IECArchitectureVersion version, Collection<IECProperty> properties) {
//		Map<Configuration, Set<IECProperty>> results = new HashMap<Configuration, Set<IECProperty>>();
//		for(IECProperty toCompare: properties) {
//			for(IsProperty property: version.getConfiguration().getReadsProperty()) {
//				if(property instanceof IECProperty)
//				putOrAddToMap(results, version.getConfiguration(), (IECProperty) property, toCompare);
//			}
//			for(IsProperty property: version.getConfiguration().getWritesProperty()) {
//				if(property instanceof IECProperty)
//				putOrAddToMap(results, version.getConfiguration(), (IECProperty) property, toCompare);
//			}
//		}
//		return results;
//	}
	
	/**
	 * Looks up the {@link Configuration} of the {@link IECArchitectureVersion} which access the given {@link Program}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param programs The {@link Program}s to look up.
	 * @return The {@link Configuration} which accesses the {@link Program}s in a Map.
	 */
	public static Map<Configuration, Set<Program>> lookUpConfigurationOfProgram(
			IECArchitectureVersion version, Collection<Program> programs) {
		Map<Configuration, Set<Program>> results = new HashMap<Configuration, Set<Program>>();
		for(Program toCompare: programs) {
			for(Program property: version.getConfiguration().getInstantiatesProgram()) {
				putOrAddToMap(results, version.getConfiguration(), property, toCompare);
			}
		}
		return results;
	}
	
//	private static <X extends IECComponent, Y extends IECComponent> void putOrAddToMap(Map<X, Set<Y>> map, X key, Y accessedValue, Y comparedValue) {
//		if (accessedValue.getId().equals(comparedValue.getId())) {
//			MapUtil.putOrAddToMap(map, key, comparedValue);
//		}
//	}
	
	private static <X extends Identifier, Y extends Identifier> void putOrAddToMap(Map<X, Set<Y>> map, X key, Y accessedValue, Y comparedValue) {
		if (accessedValue.getId().equals(comparedValue.getId())) {
			MapUtil.putOrAddToMap(map, key, comparedValue);
		}
	}
	
	private static List<GlobalVariable> getGlobalVariablesOfVersion(IECArchitectureVersion version) {
		List<GlobalVariable> globalVariables = new LinkedList<GlobalVariable>();
		globalVariables.addAll(version.getConfiguration().getDeclaresGlobalVariable());
		for(Program program : version.getConfiguration().getInstantiatesProgram()) {
			globalVariables.addAll(program.getDeclaresGlobalVariable());
		}
		return globalVariables;
	}
	
//	private static <X extends IECComponent, Y extends IECComponent> void addToMap(Map<X, Set<Y>> map, X key, Y accessedValue, Y comparedValue) {
//		if (accessedValue.getId().equals(comparedValue.getId())) {
//			Set<Y> markedGlobalVariables = results.get(key);
//			if((markedGlobalVariables != null) && !markedGlobalVariables.isEmpty()) {
//				markedGlobalVariables.add(accessedValue);
//				MapUtil.putOrAddToMap(map, key, markedGlobalVariables);
//			} else {
//				MapUtil.putOrAddToMap(map, key, comparedValue);
//			}
//		}
//	}
}
