/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.impl;

import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;

import edu.kit.ipd.sdq.kamp4iec.model.IECModificationmarks.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IECModificationmarksFactoryImpl extends EFactoryImpl implements IECModificationmarksFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IECModificationmarksFactory init() {
		try {
			IECModificationmarksFactory theIECModificationmarksFactory = (IECModificationmarksFactory)EPackage.Registry.INSTANCE.getEFactory(IECModificationmarksPackage.eNS_URI);
			if (theIECModificationmarksFactory != null) {
				return theIECModificationmarksFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IECModificationmarksFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECModificationmarksFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IECModificationmarksPackage.IEC_MODIFICATION_REPOSITORY: return createIECModificationRepository();
			case IECModificationmarksPackage.IEC_SEED_MODIFICATIONS: return createIECSeedModifications();
			case IECModificationmarksPackage.IEC_CHANGE_PROPAGATION_DUE_TO_DATA_DEPENDENCY: return createIECChangePropagationDueToDataDependency();
			case IECModificationmarksPackage.IEC_MODIFY_ELEMENT: return createIECModifyElement();
			case IECModificationmarksPackage.IEC_MODIFY_GLOBAL_VARIABLE: return createIECModifyGlobalVariable();
			case IECModificationmarksPackage.IEC_MODIFY_FUNCTION_BLOCK: return createIECModifyFunctionBlock();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECModificationRepository createIECModificationRepository() {
		IECModificationRepositoryImpl iecModificationRepository = new IECModificationRepositoryImpl();
		return iecModificationRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECSeedModifications createIECSeedModifications() {
		IECSeedModificationsImpl iecSeedModifications = new IECSeedModificationsImpl();
		return iecSeedModifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECChangePropagationDueToDataDependency createIECChangePropagationDueToDataDependency() {
		IECChangePropagationDueToDataDependencyImpl iecChangePropagationDueToDataDependency = new IECChangePropagationDueToDataDependencyImpl();
		return iecChangePropagationDueToDataDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends IECComponent> IECModifyElement<T> createIECModifyElement() {
		IECModifyElementImpl<T> iecModifyElement = new IECModifyElementImpl<T>();
		return iecModifyElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECModifyGlobalVariable createIECModifyGlobalVariable() {
		IECModifyGlobalVariableImpl iecModifyGlobalVariable = new IECModifyGlobalVariableImpl();
		return iecModifyGlobalVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECModifyFunctionBlock createIECModifyFunctionBlock() {
		IECModifyFunctionBlockImpl iecModifyFunctionBlock = new IECModifyFunctionBlockImpl();
		return iecModifyFunctionBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECModificationmarksPackage getIECModificationmarksPackage() {
		return (IECModificationmarksPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IECModificationmarksPackage getPackage() {
		return IECModificationmarksPackage.eINSTANCE;
	}

} //IECModificationmarksFactoryImpl