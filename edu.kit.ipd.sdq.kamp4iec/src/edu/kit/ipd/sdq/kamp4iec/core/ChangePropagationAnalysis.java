package edu.kit.ipd.sdq.kamp4iec.core;

import edu.kit.ipd.sdq.kamp4iec.model.Modifikationmarks.ChangePropagationDueToDataDeploy;
import edu.kit.ipd.sdq.kamp4iec.model.Modifikationmarks.ModifikationmarksFactory;

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
 * @author stammel
 *
 */
public class ChangePropagationAnalysis  extends AbstractKAMP4IECChangePropagationAnalysis<ArchitectureVersion, ChangePropagationDueToDataDeploy> {
	
	@Override
	public void runChangePropagationAnalysis(ArchitectureVersion version) {
		long timeBefore = System.currentTimeMillis();
		// Setup

		
		// Calculate

		
			
		// Update

		System.out.println("Time: " + (System.currentTimeMillis() - timeBefore));
		
	}


}