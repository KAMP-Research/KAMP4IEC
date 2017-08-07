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
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Function;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethodImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;

public class IECArchitectureModelLookup {

	/**
	 * Looks up all {@link Program}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link IECInterface}s.
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
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link Configuration} which implement the given {@link IECInterface}s.
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
	 * Looks up all {@link Program}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link FunctionBlock}s.
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
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link FunctionBlock}s via ComponentInternalDependencies.
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
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link FunctionBlock}s via ComponentInternalDependencies.
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
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link Function}s.
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
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link Function}s.
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
	 * Looks up all {@link Program}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link Function}s.
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
	 * Looks up all {@link Function}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link Function}s.
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
	 * Looks up all {@link IECMethodImplementation}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link GlobalVariable}s.
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
	 * Looks up all {@link FunctionBlock}s of the {@link IECArchitectureVersion}s {@link Configuration} which access the given {@link GlobalVariable}s.
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
	 * @return The {@link Program}s which accesses the {@link GlobalVariable}s in a Map.
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
