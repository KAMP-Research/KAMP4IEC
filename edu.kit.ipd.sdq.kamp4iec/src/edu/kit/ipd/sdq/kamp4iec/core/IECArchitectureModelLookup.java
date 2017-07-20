package edu.kit.ipd.sdq.kamp4iec.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.kit.ipd.sdq.kamp.util.MapUtil;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;

public class IECArchitectureModelLookup {
	/**
	 * Recursive lookup of Composite- and CollectionDataTypes containing certain
	 * DataTypes. Duplicates are eliminated.
	 */
	public static Map<FunctionBlock, Set<GlobalVariable>> lookUpFunctionBlocksOfGlobalVariable(
			IECArchitectureVersion version, Collection<GlobalVariable> globalVariables) {
		Map<FunctionBlock, Set<GlobalVariable>> results = new HashMap<FunctionBlock, Set<GlobalVariable>>();
		
		for (Program program : version.getKonfiguration().getContainsProgram()) {
			for (FunctionBlock functionBlock : program.getCallsFunctionBlock()) {
				for(GlobalVariable globVar : globalVariables) {
					for(GlobalVariable accessed : functionBlock.getAccessesGlobalVariable()) {
						if (accessed.getId().equals(globVar.getId())) {
							MapUtil.putOrAddToMap(results, functionBlock, globVar);
						}
					}
				}
			} 
		}
		return results;
	}
}