package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.ocl.ecore.delegate.OCLInvocationDelegateFactory.Global;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.util.MapUtil;
import edu.kit.ipd.sdq.kamp4iec.model.ComponentInternalDependencies.FunctionblockDependency;
import edu.kit.ipd.sdq.kamp4iec.model.ComponentInternalDependencies.MethodDependency;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.DependencyResource;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Function;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethodImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECPropertyImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;

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
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for(IECInterface accessed : program.getImplementsInterface()) {
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
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for(FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
				for(IECInterface accessed : functionBlock.getImplements()) {
					for(IECInterface interf : interfaces) {
						putOrAddToMap(results, functionBlock, accessed, interf);
					}
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
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for(FunctionBlock accessed : program.getCallsFunctionBlock()) {
				for(FunctionBlock functionBlock : functionBlocks) {
					putOrAddToMap(results, program, accessed, functionBlock);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s via ComponentInternalDependencies.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link IECMethodImplementation}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<IECMethodImplementation, Set<FunctionBlock>> lookUpMethodsOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<IECMethodImplementation, Set<FunctionBlock>> results = new HashMap<IECMethodImplementation, Set<FunctionBlock>>();
		if(version.getComponentInternalDependencyRepository() != null) {
			for(FunctionblockDependency dependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
				for(FunctionBlock functionBlock : functionBlocks) {
					if(dependency.getProvidedFunctionBlock().getId().equals(functionBlock.getId())) {
						if(dependency.getRequiredResource() instanceof IECMethodImplementation) {
							putOrAddToMap(results, (IECMethodImplementation) dependency.getRequiredResource(), dependency.getProvidedFunctionBlock(), functionBlock);
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link FunctionBlock}s via ComponentInternalDependencies.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functionBlocks The {@link FunctionBlock}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link FunctionBlock}s they are accessing.
	 */
	public static Map<FunctionBlock, Set<FunctionBlock>> lookUpFunctionBlocksOfFunctionBlock(
			IECArchitectureVersion version, Collection<FunctionBlock> functionBlocks) {
		Map<FunctionBlock, Set<FunctionBlock>> results = new HashMap<FunctionBlock, Set<FunctionBlock>>();
		if(version.getComponentInternalDependencyRepository() != null) {
			for(FunctionblockDependency dependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
				for(FunctionBlock functionBlock : functionBlocks) {
					if(dependency.getProvidedFunctionBlock().getId().equals(functionBlock.getId())) {
						if(dependency.getRequiredResource() instanceof FunctionBlock) {
							putOrAddToMap(results, (FunctionBlock) dependency.getRequiredResource(), dependency.getProvidedFunctionBlock(), functionBlock);
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link Function}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param functions The {@link Function}s to look up.
	 * @return A map of all {@link IECMethodImplementation}s and which {@link Function}s they are accessing.
	 */
	public static Map<IECMethodImplementation, Set<Function>> lookUpMethodsOfFunction(
			IECArchitectureVersion version, Collection<Function> functions) {
		Map<IECMethodImplementation, Set<Function>> results = new HashMap<IECMethodImplementation, Set<Function>>();
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for (FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
				for(IECMethodImplementation method : functionBlock.getHasMethod()) {
					for(Function function : functions) {
						for(Function accessed : method.getCallsFunction()) {
							putOrAddToMap(results, method, accessed, function);
						}
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
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for (FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
				for(Function function : functions) {
					for(Function accessed : functionBlock.getCallsFunction()) {
						putOrAddToMap(results, functionBlock, accessed, function);
					}
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
		for (Program program : version.getConfiguration().getContainsProgram()) {
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
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for(Function callingFunction : program.getCallsFunction()) {
				for(Function function : functions) {
					for(Function accessed : callingFunction.getCallsFunction()) {
						putOrAddToMap(results, callingFunction, accessed, function);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link IECInterface} which access the given {@link GlobalVariable}s.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param globalVariables The {@link GlobalVariable}s to look up.
	 * @return A map of all {@link IECMethodImplementation}s and which {@link GlobalVariable}s they are accessing.
	 */
	public static Map<IECMethodImplementation, Set<GlobalVariable>> lookUpMethodsOfGlobalVariable(
			IECArchitectureVersion version, Collection<GlobalVariable> globalVariables) {
		Map<IECMethodImplementation, Set<GlobalVariable>> results = new HashMap<IECMethodImplementation, Set<GlobalVariable>>();
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for (FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
				for(IECMethodImplementation method : functionBlock.getHasMethod()) {
					for(GlobalVariable globVar : globalVariables) {
						for(GlobalVariable accessed : method.getAccessesGlobalVariable()) {
							putOrAddToMap(results, method, accessed, globVar);
						}
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
		for (Program program : version.getConfiguration().getContainsProgram()) {
			for (FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
				for(GlobalVariable globVar : globalVariables) {
					for(GlobalVariable accessed : functionBlock.getAccessesGlobalVariable()) {
						putOrAddToMap(results, functionBlock, accessed, globVar);
					}
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
		for(Program prog : version.getConfiguration().getContainsProgram()) {
			for(GlobalVariable toCompare : globalVariables) {
				for(GlobalVariable accessed : prog.getAccessesGlobalVariable()) {
					putOrAddToMap(results, prog, accessed, toCompare);
				}
				for(GlobalVariable declared : prog.getDeclaresVariable()) {
					putOrAddToMap(results, prog, declared, toCompare);
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
			for(GlobalVariable accesed : config.getAccessesGlobalVariable()) {
				putOrAddToMap(results, config, accesed, toCompare);
			}
			for(GlobalVariable declared : config.getDeclaresVariable()) {
				putOrAddToMap(results, config, declared, toCompare);
			}
		}
		return results;
	}

	/**
	 * Looks up the {@link IECInterface} of the {@link IECArchitectureVersion} which access the given {@link IECMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethod}s to look up.
	 * @return The {@link IECInterface}s which access the {@link IECMethod}s in a Map.
	 */
	public static Map<IECInterface, Set<IECMethod>> lookUpInterfaceOfMethod(
			IECArchitectureVersion version, Collection<IECMethod> methods) {
		Map<IECInterface, Set<IECMethod>> results = new HashMap<IECInterface, Set<IECMethod>>();
		for(IECInterface iecInterface : version.getConfiguration().getInterfaces().getContainsInterface()) {
			for(IECMethod toCompare: methods) {
				for(IECMethod method: iecInterface.getHasMethod()) {
					putOrAddToMap(results, iecInterface, method, toCompare);
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link IECInterface} of the {@link IECArchitectureVersion} which access the given {@link IECMethod}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethod}s to look up.
	 * @return The {@link IECInterface}s which access the {@link IECMethod}s in a Map.
	 */
	public static Map<IECInterface, Set<IECProperty>> lookUpInterfaceOfProperty(
			IECArchitectureVersion version, Collection<IECProperty> properties) {
		Map<IECInterface, Set<IECProperty>> results = new HashMap<IECInterface, Set<IECProperty>>();
		for(IECInterface iecInterface : version.getConfiguration().getInterfaces().getContainsInterface()) {
			for(IECProperty toCompare: properties) {
				for(IECProperty property: iecInterface.getHasProperty()) {
					putOrAddToMap(results, iecInterface, property, toCompare);
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link FunctionBlock} which access the given {@link IECMethodImplementation}s via ComponentInternalDependencies.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param methods The {@link IECMethodImplementation}s to look up.
	 * @return A map of all {@link FunctionBlock}s and which {@link IECMethodImplementation}s they are accessing.
	 */
	public static Map<FunctionBlock, Set<IECMethodImplementation>> lookUpFunctionBlocksOfMethodImplementation(
			IECArchitectureVersion version, Collection<IECMethodImplementation> methods) {
		Map<FunctionBlock, Set<IECMethodImplementation>> results = new HashMap<FunctionBlock, Set<IECMethodImplementation>>();
		if(version.getComponentInternalDependencyRepository() != null) {
			for(FunctionblockDependency fbDependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
				for(IECMethodImplementation compared : methods) {
					if(fbDependency.getRequiredResource() instanceof IECMethodImplementation) {
						putOrAddToMap(results, fbDependency.getProvidedFunctionBlock(), (IECMethodImplementation)fbDependency.getRequiredResource(), compared);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link IECMethodImplementation} which access the given {@link IECMethodImplementation}s via ComponentInternalDependencies.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param methods The {@link IECMethodImplementation}s to look up.
	 * @return A map of all {@link IECMethodImplementation}s and which {@link IECMethodImplementation}s they are accessing.
	 */
	public static Map<IECMethodImplementation, Set<IECMethodImplementation>> lookUpMethodsOfMethodImplementation(
			IECArchitectureVersion version, Collection<IECMethodImplementation> methods) {
		Map<IECMethodImplementation, Set<IECMethodImplementation>> results = new HashMap<IECMethodImplementation, Set<IECMethodImplementation>>();
		if(version.getComponentInternalDependencyRepository() != null) {
			for(FunctionblockDependency fbDependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
				for(MethodDependency methodDependency : fbDependency.getHasMethodDependency()) {
					for(IECMethodImplementation compared : methods) {
						if(methodDependency.getRequiredResource() instanceof IECMethodImplementation) {
							putOrAddToMap(results, methodDependency.getProvidedMethod(), (IECMethodImplementation)fbDependency.getRequiredResource(), compared);
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Program} of the {@link IECArchitectureVersion} which access the given {@link IECMethodImplementation}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethodImplementation}s to look up.
	 * @return The {@link Program}s which access the {@link IECMethodImplementation}s in a Map.
	 */
	public static Map<Program, Set<IECMethodImplementation>> lookUpProgramsOfMethodImplementation(
			IECArchitectureVersion version, Collection<IECMethodImplementation> methods) {
		Map<Program, Set<IECMethodImplementation>> results = new HashMap<Program, Set<IECMethodImplementation>>();
		for(Program prog : version.getConfiguration().getContainsProgram()) {
			for(IECMethodImplementation toCompare: methods) {
				for(IECMethodImplementation property: prog.getCallsMethod()) {
					putOrAddToMap(results, prog, property, toCompare);
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link IECMethodImplementation} which access the given {@link IECPropertyImplementation}s via ComponentInternalDependencies.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param properties The {@link IECPropertyImplementation}s to look up.
	 * @return The {@link IECMethodImplementation}s which access the {@link IECPropertyImplementation}s in a Map.
	 */
	public static Map<IECMethodImplementation, Set<IECPropertyImplementation>> lookUpMethodImplementaionsOfPropertyImplementation(
			IECArchitectureVersion version, Collection<IECPropertyImplementation> properties) {
		Map<IECMethodImplementation, Set<IECPropertyImplementation>> results = new HashMap<IECMethodImplementation, Set<IECPropertyImplementation>>();
		if(version.getComponentInternalDependencyRepository() != null) {
			for(FunctionblockDependency fbDependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
				for(MethodDependency methodDependency : fbDependency.getHasMethodDependency()) {
					for(IECPropertyImplementation compared : properties) {
						if(methodDependency.getRequiredResource() instanceof IECMethodImplementation) {
							putOrAddToMap(results, methodDependency.getProvidedMethod(), (IECPropertyImplementation)fbDependency.getRequiredResource(), compared);
						}
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link IECMethodImplementation} which access the given {@link IECPropertyImplementation}s via ComponentInternalDependencies.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param properties The {@link IECPropertyImplementation}s to look up.
	 * @return The {@link FunctionBlock}s which access the {@link IECPropertyImplementation}s in a Map.
	 */
	public static Map<FunctionBlock, Set<IECPropertyImplementation>> lookUpFunctionBlocksOfPropertyImplementation(
			IECArchitectureVersion version, Collection<IECPropertyImplementation> properties) {
		Map<FunctionBlock, Set<IECPropertyImplementation>> results = new HashMap<FunctionBlock, Set<IECPropertyImplementation>>();
		if(version.getComponentInternalDependencyRepository() != null) {
			for(FunctionblockDependency fbDependency : version.getComponentInternalDependencyRepository().getHasFunctionblockDependency()) {
				for(IECPropertyImplementation compared : properties) {
					if(fbDependency.getRequiredResource() instanceof IECMethodImplementation) {
						putOrAddToMap(results, fbDependency.getProvidedFunctionBlock(), (IECPropertyImplementation)fbDependency.getRequiredResource(), compared);
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Program} of the {@link IECArchitectureVersion} which access the given {@link IECMethodImplementation}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethodImplementation}s to look up.
	 * @return The {@link Program}s which access the {@link IECMethodImplementation}s in a Map.
	 */
	public static Map<Program, Set<IECPropertyImplementation>> lookUpProgramsOfPropertyImplementation(
			IECArchitectureVersion version, Collection<IECPropertyImplementation> properties) {
		Map<Program, Set<IECPropertyImplementation>> results = new HashMap<Program, Set<IECPropertyImplementation>>();
		for(Program prog : version.getConfiguration().getContainsProgram()) {
			for(IECPropertyImplementation toCompare: properties) {
				for(IECPropertyImplementation property: prog.getAccessesProperty()) {
					putOrAddToMap(results, prog, property, toCompare);
				}
			}
		}
		return results;
	}
	
	/**
	 * Looks up the {@link Configuration} of the {@link IECArchitectureVersion} which access the given {@link IECMethodImplementation}s and lists it in a Map.
	 * @param version The current {@link IECArchitectureVersion}.
	 * @param method The {@link IECMethodImplementation}s to look up.
	 * @return The {@link Configuration} which accesses the {@link IECMethodImplementation}s in a Map.
	 */
	public static Map<Configuration, Set<IECPropertyImplementation>> lookUpConfigurationsOfPropertyImplementation(
			IECArchitectureVersion version, Collection<IECPropertyImplementation> properties) {
		Map<Configuration, Set<IECPropertyImplementation>> results = new HashMap<Configuration, Set<IECPropertyImplementation>>();
		for(IECPropertyImplementation toCompare: properties) {
			for(IECPropertyImplementation property: version.getConfiguration().getAccessesProperty()) {
				putOrAddToMap(results, version.getConfiguration(), property, toCompare);
			}
		}
		return results;
	}
	
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
			for(Program property: version.getConfiguration().getContainsProgram()) {
				putOrAddToMap(results, version.getConfiguration(), property, toCompare);
			}
		}
		return results;
	}
	
	private static <X extends IECComponent, Y extends IECComponent> void putOrAddToMap(Map<X, Set<Y>> map, X key, Y accessedValue, Y comparedValue) {
		if (accessedValue.getId().equals(comparedValue.getId())) {
			MapUtil.putOrAddToMap(map, key, comparedValue);
		}
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
