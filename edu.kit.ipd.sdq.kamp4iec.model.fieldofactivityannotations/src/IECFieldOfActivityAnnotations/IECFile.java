/**
 */
package IECFieldOfActivityAnnotations;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IEC File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IECFieldOfActivityAnnotations.IECFile#getFilename <em>Filename</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECFile#getTechnology <em>Technology</em>}</li>
 * </ul>
 *
 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECFile()
 * @model abstract="true"
 * @generated
 */
public interface IECFile extends EObject {
	/**
	 * Returns the value of the '<em><b>Filename</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filename</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filename</em>' attribute.
	 * @see #setFilename(String)
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECFile_Filename()
	 * @model
	 * @generated
	 */
	String getFilename();

	/**
	 * Sets the value of the '{@link IECFieldOfActivityAnnotations.IECFile#getFilename <em>Filename</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filename</em>' attribute.
	 * @see #getFilename()
	 * @generated
	 */
	void setFilename(String value);

	/**
	 * Returns the value of the '<em><b>Technology</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Technology</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Technology</em>' attribute.
	 * @see #setTechnology(String)
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECFile_Technology()
	 * @model
	 * @generated
	 */
	String getTechnology();

	/**
	 * Sets the value of the '{@link IECFieldOfActivityAnnotations.IECFile#getTechnology <em>Technology</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Technology</em>' attribute.
	 * @see #getTechnology()
	 * @generated
	 */
	void setTechnology(String value);

} // IECFile
