/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl;

import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECModelPackage;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECPropertyImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Konfiguration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;

import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.impl.IdentifierImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Konfiguration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.KonfigurationImpl#getContainsProgram <em>Contains Program</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.KonfigurationImpl#getDeclaresVariable <em>Declares Variable</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.KonfigurationImpl#getAccessesProperty <em>Accesses Property</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.KonfigurationImpl#getAccessesGlobalVariable <em>Accesses Global Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KonfigurationImpl extends IdentifierImpl implements Konfiguration {
	/**
	 * The cached value of the '{@link #getContainsProgram() <em>Contains Program</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainsProgram()
	 * @generated
	 * @ordered
	 */
	protected EList<Program> containsProgram;

	/**
	 * The cached value of the '{@link #getDeclaresVariable() <em>Declares Variable</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaresVariable()
	 * @generated
	 * @ordered
	 */
	protected EList<GlobalVariable> declaresVariable;

	/**
	 * The cached value of the '{@link #getAccessesProperty() <em>Accesses Property</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessesProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<IECPropertyImplementation> accessesProperty;

	/**
	 * The cached value of the '{@link #getAccessesGlobalVariable() <em>Accesses Global Variable</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessesGlobalVariable()
	 * @generated
	 * @ordered
	 */
	protected EList<GlobalVariable> accessesGlobalVariable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KonfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IECModelPackage.Literals.KONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Program> getContainsProgram() {
		if (containsProgram == null) {
			containsProgram = new EObjectContainmentEList<Program>(Program.class, this, IECModelPackage.KONFIGURATION__CONTAINS_PROGRAM);
		}
		return containsProgram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalVariable> getDeclaresVariable() {
		if (declaresVariable == null) {
			declaresVariable = new EObjectContainmentEList<GlobalVariable>(GlobalVariable.class, this, IECModelPackage.KONFIGURATION__DECLARES_VARIABLE);
		}
		return declaresVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IECPropertyImplementation> getAccessesProperty() {
		if (accessesProperty == null) {
			accessesProperty = new EObjectResolvingEList<IECPropertyImplementation>(IECPropertyImplementation.class, this, IECModelPackage.KONFIGURATION__ACCESSES_PROPERTY);
		}
		return accessesProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalVariable> getAccessesGlobalVariable() {
		if (accessesGlobalVariable == null) {
			accessesGlobalVariable = new EObjectResolvingEList<GlobalVariable>(GlobalVariable.class, this, IECModelPackage.KONFIGURATION__ACCESSES_GLOBAL_VARIABLE);
		}
		return accessesGlobalVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IECModelPackage.KONFIGURATION__CONTAINS_PROGRAM:
				return ((InternalEList<?>)getContainsProgram()).basicRemove(otherEnd, msgs);
			case IECModelPackage.KONFIGURATION__DECLARES_VARIABLE:
				return ((InternalEList<?>)getDeclaresVariable()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IECModelPackage.KONFIGURATION__CONTAINS_PROGRAM:
				return getContainsProgram();
			case IECModelPackage.KONFIGURATION__DECLARES_VARIABLE:
				return getDeclaresVariable();
			case IECModelPackage.KONFIGURATION__ACCESSES_PROPERTY:
				return getAccessesProperty();
			case IECModelPackage.KONFIGURATION__ACCESSES_GLOBAL_VARIABLE:
				return getAccessesGlobalVariable();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IECModelPackage.KONFIGURATION__CONTAINS_PROGRAM:
				getContainsProgram().clear();
				getContainsProgram().addAll((Collection<? extends Program>)newValue);
				return;
			case IECModelPackage.KONFIGURATION__DECLARES_VARIABLE:
				getDeclaresVariable().clear();
				getDeclaresVariable().addAll((Collection<? extends GlobalVariable>)newValue);
				return;
			case IECModelPackage.KONFIGURATION__ACCESSES_PROPERTY:
				getAccessesProperty().clear();
				getAccessesProperty().addAll((Collection<? extends IECPropertyImplementation>)newValue);
				return;
			case IECModelPackage.KONFIGURATION__ACCESSES_GLOBAL_VARIABLE:
				getAccessesGlobalVariable().clear();
				getAccessesGlobalVariable().addAll((Collection<? extends GlobalVariable>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IECModelPackage.KONFIGURATION__CONTAINS_PROGRAM:
				getContainsProgram().clear();
				return;
			case IECModelPackage.KONFIGURATION__DECLARES_VARIABLE:
				getDeclaresVariable().clear();
				return;
			case IECModelPackage.KONFIGURATION__ACCESSES_PROPERTY:
				getAccessesProperty().clear();
				return;
			case IECModelPackage.KONFIGURATION__ACCESSES_GLOBAL_VARIABLE:
				getAccessesGlobalVariable().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IECModelPackage.KONFIGURATION__CONTAINS_PROGRAM:
				return containsProgram != null && !containsProgram.isEmpty();
			case IECModelPackage.KONFIGURATION__DECLARES_VARIABLE:
				return declaresVariable != null && !declaresVariable.isEmpty();
			case IECModelPackage.KONFIGURATION__ACCESSES_PROPERTY:
				return accessesProperty != null && !accessesProperty.isEmpty();
			case IECModelPackage.KONFIGURATION__ACCESSES_GLOBAL_VARIABLE:
				return accessesGlobalVariable != null && !accessesGlobalVariable.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //KonfigurationImpl
