/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.Modifikationmarks;

import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Element;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.kamp4iec.model.Modifikationmarks.ModifikationmarksPackage
 * @generated
 */
public interface ModifikationmarksFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModifikationmarksFactory eINSTANCE = edu.kit.ipd.sdq.kamp4iec.model.Modifikationmarks.impl.ModifikationmarksFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>KAMP4IEC Modification Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>KAMP4IEC Modification Repository</em>'.
	 * @generated
	 */
	KAMP4IECModificationRepository createKAMP4IECModificationRepository();

	/**
	 * Returns a new object of class '<em>KAMP4IEC Seed Modifications</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>KAMP4IEC Seed Modifications</em>'.
	 * @generated
	 */
	KAMP4IECSeedModifications createKAMP4IECSeedModifications();

	/**
	 * Returns a new object of class '<em>Change Propagation Due To Data Deploy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Propagation Due To Data Deploy</em>'.
	 * @generated
	 */
	ChangePropagationDueToDataDeploy createChangePropagationDueToDataDeploy();

	/**
	 * Returns a new object of class '<em>Modify Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modify Element</em>'.
	 * @generated
	 */
	<T extends Element> ModifyElement<T> createModifyElement();

	/**
	 * Returns a new object of class '<em>Modify Global Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modify Global Variable</em>'.
	 * @generated
	 */
	ModifyGlobalVariable createModifyGlobalVariable();

	/**
	 * Returns a new object of class '<em>Modify Function Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modify Function Block</em>'.
	 * @generated
	 */
	ModifyFunctionBlock createModifyFunctionBlock();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModifikationmarksPackage getModifikationmarksPackage();

} //ModifikationmarksFactory