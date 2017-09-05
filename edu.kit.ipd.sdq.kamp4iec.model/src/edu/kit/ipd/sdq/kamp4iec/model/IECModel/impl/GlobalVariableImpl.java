/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl;

import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECModelPackage;

import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.DerivedType;
import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.impl.IdentifierImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.GlobalVariableImpl#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.GlobalVariableImpl#getUsesEnum <em>Uses Enum</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.IECModel.impl.GlobalVariableImpl#getHasDerivedType <em>Has Derived Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GlobalVariableImpl extends IdentifierImpl implements GlobalVariable {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable type;

	/**
	 * The cached value of the '{@link #getUsesEnum() <em>Uses Enum</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsesEnum()
	 * @generated
	 * @ordered
	 */
	protected EList<edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum> usesEnum;

	/**
	 * The cached value of the '{@link #getHasDerivedType() <em>Has Derived Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHasDerivedType()
	 * @generated
	 * @ordered
	 */
	protected DerivedType hasDerivedType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IECModelPackage.Literals.GLOBAL_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IECModelPackage.GLOBAL_VARIABLE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable newType) {
		edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IECModelPackage.GLOBAL_VARIABLE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum> getUsesEnum() {
		if (usesEnum == null) {
			usesEnum = new EObjectResolvingEList<edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum>(edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum.class, this, IECModelPackage.GLOBAL_VARIABLE__USES_ENUM);
		}
		return usesEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DerivedType getHasDerivedType() {
		if (hasDerivedType != null && hasDerivedType.eIsProxy()) {
			InternalEObject oldHasDerivedType = (InternalEObject)hasDerivedType;
			hasDerivedType = (DerivedType)eResolveProxy(oldHasDerivedType);
			if (hasDerivedType != oldHasDerivedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IECModelPackage.GLOBAL_VARIABLE__HAS_DERIVED_TYPE, oldHasDerivedType, hasDerivedType));
			}
		}
		return hasDerivedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DerivedType basicGetHasDerivedType() {
		return hasDerivedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasDerivedType(DerivedType newHasDerivedType) {
		DerivedType oldHasDerivedType = hasDerivedType;
		hasDerivedType = newHasDerivedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IECModelPackage.GLOBAL_VARIABLE__HAS_DERIVED_TYPE, oldHasDerivedType, hasDerivedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IECModelPackage.GLOBAL_VARIABLE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case IECModelPackage.GLOBAL_VARIABLE__USES_ENUM:
				return getUsesEnum();
			case IECModelPackage.GLOBAL_VARIABLE__HAS_DERIVED_TYPE:
				if (resolve) return getHasDerivedType();
				return basicGetHasDerivedType();
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
			case IECModelPackage.GLOBAL_VARIABLE__TYPE:
				setType((edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable)newValue);
				return;
			case IECModelPackage.GLOBAL_VARIABLE__USES_ENUM:
				getUsesEnum().clear();
				getUsesEnum().addAll((Collection<? extends edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum>)newValue);
				return;
			case IECModelPackage.GLOBAL_VARIABLE__HAS_DERIVED_TYPE:
				setHasDerivedType((DerivedType)newValue);
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
			case IECModelPackage.GLOBAL_VARIABLE__TYPE:
				setType((edu.kit.ipd.sdq.kamp4iec.model.IECRepository.GlobalVariable)null);
				return;
			case IECModelPackage.GLOBAL_VARIABLE__USES_ENUM:
				getUsesEnum().clear();
				return;
			case IECModelPackage.GLOBAL_VARIABLE__HAS_DERIVED_TYPE:
				setHasDerivedType((DerivedType)null);
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
			case IECModelPackage.GLOBAL_VARIABLE__TYPE:
				return type != null;
			case IECModelPackage.GLOBAL_VARIABLE__USES_ENUM:
				return usesEnum != null && !usesEnum.isEmpty();
			case IECModelPackage.GLOBAL_VARIABLE__HAS_DERIVED_TYPE:
				return hasDerivedType != null;
		}
		return super.eIsSet(featureID);
	}

} //GlobalVariableImpl
