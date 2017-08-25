/**
 */
package IECFieldOfActivityAnnotations;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IEC Development Artefact Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IECFieldOfActivityAnnotations.IECDevelopmentArtefactSpecification#getParent <em>Parent</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECDevelopmentArtefactSpecification#getSourceFiles <em>Source Files</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECDevelopmentArtefactSpecification#getSourceFileAggregations <em>Source File Aggregations</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECDevelopmentArtefactSpecification#getMetadataFiles <em>Metadata Files</em>}</li>
 *   <li>{@link IECFieldOfActivityAnnotations.IECDevelopmentArtefactSpecification#getMetadataFileAggregations <em>Metadata File Aggregations</em>}</li>
 * </ul>
 *
 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECDevelopmentArtefactSpecification()
 * @model
 * @generated
 */
public interface IECDevelopmentArtefactSpecification extends EObject {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository#getDevelopmentArtefactSpecification <em>Development Artefact Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(IECFieldOfActivityAnnotationsRepository)
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECDevelopmentArtefactSpecification_Parent()
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsRepository#getDevelopmentArtefactSpecification
	 * @model opposite="developmentArtefactSpecification" required="true" transient="false"
	 * @generated
	 */
	IECFieldOfActivityAnnotationsRepository getParent();

	/**
	 * Sets the value of the '{@link IECFieldOfActivityAnnotations.IECDevelopmentArtefactSpecification#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(IECFieldOfActivityAnnotationsRepository value);

	/**
	 * Returns the value of the '<em><b>Source Files</b></em>' containment reference list.
	 * The list contents are of type {@link IECFieldOfActivityAnnotations.IECSourceFile}.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECSourceFile#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Files</em>' containment reference list.
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECDevelopmentArtefactSpecification_SourceFiles()
	 * @see IECFieldOfActivityAnnotations.IECSourceFile#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<IECSourceFile> getSourceFiles();

	/**
	 * Returns the value of the '<em><b>Source File Aggregations</b></em>' containment reference list.
	 * The list contents are of type {@link IECFieldOfActivityAnnotations.IECSourceFileAggregation}.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECSourceFileAggregation#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source File Aggregations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source File Aggregations</em>' containment reference list.
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECDevelopmentArtefactSpecification_SourceFileAggregations()
	 * @see IECFieldOfActivityAnnotations.IECSourceFileAggregation#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<IECSourceFileAggregation> getSourceFileAggregations();

	/**
	 * Returns the value of the '<em><b>Metadata Files</b></em>' containment reference list.
	 * The list contents are of type {@link IECFieldOfActivityAnnotations.IECMetadataFile}.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECMetadataFile#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata Files</em>' containment reference list.
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECDevelopmentArtefactSpecification_MetadataFiles()
	 * @see IECFieldOfActivityAnnotations.IECMetadataFile#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<IECMetadataFile> getMetadataFiles();

	/**
	 * Returns the value of the '<em><b>Metadata File Aggregations</b></em>' containment reference list.
	 * The list contents are of type {@link IECFieldOfActivityAnnotations.IECMetadataFileAggregation}.
	 * It is bidirectional and its opposite is '{@link IECFieldOfActivityAnnotations.IECMetadataFileAggregation#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata File Aggregations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata File Aggregations</em>' containment reference list.
	 * @see IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsPackage#getIECDevelopmentArtefactSpecification_MetadataFileAggregations()
	 * @see IECFieldOfActivityAnnotations.IECMetadataFileAggregation#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<IECMetadataFileAggregation> getMetadataFileAggregations();

} // IECDevelopmentArtefactSpecification
