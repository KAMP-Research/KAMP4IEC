/**
 */
package IECFieldOfActivityAnnotations;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IEC Staff Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IECFieldOfActivityAnnotations.IECStaffSpecification#getParent <em>Parent</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECStaffSpecification#getPersonList <em>Person List</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECStaffSpecification#getRoleList <em>Role List</em>}</li>
 * </ul>
 *
 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECStaffSpecification()
 * @model
 * @generated
 */
public interface IECStaffSpecification extends EObject {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository#getStaffSpecification <em>Staff Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(IECFieldOfActivityAnnotationsRepository)
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECStaffSpecification_Parent()
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository#getStaffSpecification
	 * @model opposite="staffSpecification" required="true" transient="false"
	 * @generated
	 */
	IECFieldOfActivityAnnotationsRepository getParent();

	/**
	 * Sets the value of the '{@link IECFieldOfActivityAnnotations.IECStaffSpecification#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(IECFieldOfActivityAnnotationsRepository value);

	/**
	 * Returns the value of the '<em><b>Person List</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECPersonList#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Person List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Person List</em>' containment reference.
	 * @see #setPersonList(IECPersonList)
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECStaffSpecification_PersonList()
	 * @see IECFieldOfActivityAnnotations.IECPersonList#getParent
	 * @model opposite="parent" containment="true" required="true"
	 * @generated
	 */
	IECPersonList getPersonList();

	/**
	 * Sets the value of the '{@link IECFieldOfActivityAnnotations.IECStaffSpecification#getPersonList <em>Person List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Person List</em>' containment reference.
	 * @see #getPersonList()
	 * @generated
	 */
	void setPersonList(IECPersonList value);

	/**
	 * Returns the value of the '<em><b>Role List</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECRoleList#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role List</em>' containment reference.
	 * @see #setRoleList(IECRoleList)
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECStaffSpecification_RoleList()
	 * @see IECFieldOfActivityAnnotations.IECRoleList#getParent
	 * @model opposite="parent" containment="true" required="true"
	 * @generated
	 */
	IECRoleList getRoleList();

	/**
	 * Sets the value of the '{@link IECFieldOfActivityAnnotations.IECStaffSpecification#getRoleList <em>Role List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role List</em>' containment reference.
	 * @see #getRoleList()
	 * @generated
	 */
	void setRoleList(IECRoleList value);

} // IECStaffSpecification
