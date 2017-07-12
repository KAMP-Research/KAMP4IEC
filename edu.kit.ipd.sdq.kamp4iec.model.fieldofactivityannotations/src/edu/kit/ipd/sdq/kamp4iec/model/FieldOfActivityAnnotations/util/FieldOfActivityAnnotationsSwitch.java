/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.util;

import de.uka.ipd.sdq.identifier.Identifier;

import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.FieldOfActivityAnnotationsPackage
 * @generated
 */
public class FieldOfActivityAnnotationsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static FieldOfActivityAnnotationsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldOfActivityAnnotationsSwitch() {
		if (modelPackage == null) {
			modelPackage = FieldOfActivityAnnotationsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY: {
				FieldOfActivityAnnotationsRepository fieldOfActivityAnnotationsRepository = (FieldOfActivityAnnotationsRepository)theEObject;
				T result = caseFieldOfActivityAnnotationsRepository(fieldOfActivityAnnotationsRepository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.DEVELOPMENT_ARTEFACT_SPECIFICATION: {
				DevelopmentArtefactSpecification developmentArtefactSpecification = (DevelopmentArtefactSpecification)theEObject;
				T result = caseDevelopmentArtefactSpecification(developmentArtefactSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.SOURCE_FILE: {
				SourceFile sourceFile = (SourceFile)theEObject;
				T result = caseSourceFile(sourceFile);
				if (result == null) result = caseFile(sourceFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.SOURCE_FILE_AGGREGATION: {
				SourceFileAggregation sourceFileAggregation = (SourceFileAggregation)theEObject;
				T result = caseSourceFileAggregation(sourceFileAggregation);
				if (result == null) result = caseFileAggregation(sourceFileAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.METADATA_FILE: {
				MetadataFile metadataFile = (MetadataFile)theEObject;
				T result = caseMetadataFile(metadataFile);
				if (result == null) result = caseFile(metadataFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.METADATA_FILE_AGGREGATION: {
				MetadataFileAggregation metadataFileAggregation = (MetadataFileAggregation)theEObject;
				T result = caseMetadataFileAggregation(metadataFileAggregation);
				if (result == null) result = caseFileAggregation(metadataFileAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.BUILD_SPECIFICATION: {
				BuildSpecification buildSpecification = (BuildSpecification)theEObject;
				T result = caseBuildSpecification(buildSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.BUILD_CONFIGURATION: {
				BuildConfiguration buildConfiguration = (BuildConfiguration)theEObject;
				T result = caseBuildConfiguration(buildConfiguration);
				if (result == null) result = caseFile(buildConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.THIRD_PARTY_COMPONENT_OR_LIBRARY: {
				ThirdPartyComponentOrLibrary thirdPartyComponentOrLibrary = (ThirdPartyComponentOrLibrary)theEObject;
				T result = caseThirdPartyComponentOrLibrary(thirdPartyComponentOrLibrary);
				if (result == null) result = caseFile(thirdPartyComponentOrLibrary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TEST_SPECIFICATION: {
				TestSpecification testSpecification = (TestSpecification)theEObject;
				T result = caseTestSpecification(testSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.RELEASE_SPECIFICATION: {
				ReleaseSpecification releaseSpecification = (ReleaseSpecification)theEObject;
				T result = caseReleaseSpecification(releaseSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.RELEASE_CONFIGURATION: {
				ReleaseConfiguration releaseConfiguration = (ReleaseConfiguration)theEObject;
				T result = caseReleaseConfiguration(releaseConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.DEPLOYMENT_SPECIFICATION: {
				DeploymentSpecification deploymentSpecification = (DeploymentSpecification)theEObject;
				T result = caseDeploymentSpecification(deploymentSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.RUNTIME_INSTANCE: {
				RuntimeInstance runtimeInstance = (RuntimeInstance)theEObject;
				T result = caseRuntimeInstance(runtimeInstance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.RUNTIME_INSTANCE_AGGREGATION: {
				RuntimeInstanceAggregation runtimeInstanceAggregation = (RuntimeInstanceAggregation)theEObject;
				T result = caseRuntimeInstanceAggregation(runtimeInstanceAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.UNIT_TEST_CASE: {
				UnitTestCase unitTestCase = (UnitTestCase)theEObject;
				T result = caseUnitTestCase(unitTestCase);
				if (result == null) result = caseTestCase(unitTestCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.ACCEPTANCE_TEST_CASE: {
				AcceptanceTestCase acceptanceTestCase = (AcceptanceTestCase)theEObject;
				T result = caseAcceptanceTestCase(acceptanceTestCase);
				if (result == null) result = caseTestCase(acceptanceTestCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.INTEGRATION_TEST_CASE: {
				IntegrationTestCase integrationTestCase = (IntegrationTestCase)theEObject;
				T result = caseIntegrationTestCase(integrationTestCase);
				if (result == null) result = caseTestCase(integrationTestCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.UNIT_TEST_CASE_AGGREGATION: {
				UnitTestCaseAggregation unitTestCaseAggregation = (UnitTestCaseAggregation)theEObject;
				T result = caseUnitTestCaseAggregation(unitTestCaseAggregation);
				if (result == null) result = caseTestCaseAggregation(unitTestCaseAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.ACCEPTANCE_TEST_CASE_AGGREGATION: {
				AcceptanceTestCaseAggregation acceptanceTestCaseAggregation = (AcceptanceTestCaseAggregation)theEObject;
				T result = caseAcceptanceTestCaseAggregation(acceptanceTestCaseAggregation);
				if (result == null) result = caseTestCaseAggregation(acceptanceTestCaseAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.INTEGRATION_TEST_CASE_AGGREGATION: {
				IntegrationTestCaseAggregation integrationTestCaseAggregation = (IntegrationTestCaseAggregation)theEObject;
				T result = caseIntegrationTestCaseAggregation(integrationTestCaseAggregation);
				if (result == null) result = caseTestCaseAggregation(integrationTestCaseAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.STAFF_SPECIFICATION: {
				StaffSpecification staffSpecification = (StaffSpecification)theEObject;
				T result = caseStaffSpecification(staffSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.PERSON_LIST: {
				PersonList personList = (PersonList)theEObject;
				T result = casePersonList(personList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.PERSON: {
				Person person = (Person)theEObject;
				T result = casePerson(person);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.ROLE: {
				Role role = (Role)theEObject;
				T result = caseRole(role);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.ROLE_LIST: {
				RoleList roleList = (RoleList)theEObject;
				T result = caseRoleList(roleList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.DEVELOPER: {
				Developer developer = (Developer)theEObject;
				T result = caseDeveloper(developer);
				if (result == null) result = caseRole(developer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.BUILD_CONFIGURATOR: {
				BuildConfigurator buildConfigurator = (BuildConfigurator)theEObject;
				T result = caseBuildConfigurator(buildConfigurator);
				if (result == null) result = caseRole(buildConfigurator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TEST_DEVELOPER: {
				TestDeveloper testDeveloper = (TestDeveloper)theEObject;
				T result = caseTestDeveloper(testDeveloper);
				if (result == null) result = caseRole(testDeveloper);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TESTER: {
				Tester tester = (Tester)theEObject;
				T result = caseTester(tester);
				if (result == null) result = caseRole(tester);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.RELEASE_CONFIGURATOR: {
				ReleaseConfigurator releaseConfigurator = (ReleaseConfigurator)theEObject;
				T result = caseReleaseConfigurator(releaseConfigurator);
				if (result == null) result = caseRole(releaseConfigurator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.DEPLOYER: {
				Deployer deployer = (Deployer)theEObject;
				T result = caseDeployer(deployer);
				if (result == null) result = caseRole(deployer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.FILE: {
				File file = (File)theEObject;
				T result = caseFile(file);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.FILE_AGGREGATION: {
				FileAggregation fileAggregation = (FileAggregation)theEObject;
				T result = caseFileAggregation(fileAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TEST_CASE: {
				TestCase testCase = (TestCase)theEObject;
				T result = caseTestCase(testCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TEST_CASE_AGGREGATION: {
				TestCaseAggregation testCaseAggregation = (TestCaseAggregation)theEObject;
				T result = caseTestCaseAggregation(testCaseAggregation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.DESIGN_PATTERN_SPECIFICATION: {
				DesignPatternSpecification designPatternSpecification = (DesignPatternSpecification)theEObject;
				T result = caseDesignPatternSpecification(designPatternSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.DESIGN_PATTERN_ROLE: {
				DesignPatternRole designPatternRole = (DesignPatternRole)theEObject;
				T result = caseDesignPatternRole(designPatternRole);
				if (result == null) result = caseIdentifier(designPatternRole);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TECHNOLOGY_SPECIFICATION: {
				TechnologySpecification technologySpecification = (TechnologySpecification)theEObject;
				T result = caseTechnologySpecification(technologySpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FieldOfActivityAnnotationsPackage.TECHNOLOGICAL_CORRESPONDENCE: {
				TechnologicalCorrespondence technologicalCorrespondence = (TechnologicalCorrespondence)theEObject;
				T result = caseTechnologicalCorrespondence(technologicalCorrespondence);
				if (result == null) result = caseIdentifier(technologicalCorrespondence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldOfActivityAnnotationsRepository(FieldOfActivityAnnotationsRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Development Artefact Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Development Artefact Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDevelopmentArtefactSpecification(DevelopmentArtefactSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSourceFile(SourceFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source File Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source File Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSourceFileAggregation(SourceFileAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metadata File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metadata File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetadataFile(MetadataFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metadata File Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metadata File Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetadataFileAggregation(MetadataFileAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Build Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Build Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuildSpecification(BuildSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Build Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Build Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuildConfiguration(BuildConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Third Party Component Or Library</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Third Party Component Or Library</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThirdPartyComponentOrLibrary(ThirdPartyComponentOrLibrary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestSpecification(TestSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Release Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Release Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReleaseSpecification(ReleaseSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Release Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Release Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReleaseConfiguration(ReleaseConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployment Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployment Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeploymentSpecification(DeploymentSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Runtime Instance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Runtime Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuntimeInstance(RuntimeInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Runtime Instance Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Runtime Instance Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuntimeInstanceAggregation(RuntimeInstanceAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unit Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unit Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnitTestCase(UnitTestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceptance Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceptance Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceptanceTestCase(AcceptanceTestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integration Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integration Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegrationTestCase(IntegrationTestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unit Test Case Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unit Test Case Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnitTestCaseAggregation(UnitTestCaseAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceptance Test Case Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceptance Test Case Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceptanceTestCaseAggregation(AcceptanceTestCaseAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integration Test Case Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integration Test Case Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegrationTestCaseAggregation(IntegrationTestCaseAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Staff Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Staff Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStaffSpecification(StaffSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Person List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Person List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersonList(PersonList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Person</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Person</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePerson(Person object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRole(Role object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoleList(RoleList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Developer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Developer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeveloper(Developer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Build Configurator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Build Configurator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuildConfigurator(BuildConfigurator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Developer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Developer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestDeveloper(TestDeveloper object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tester</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tester</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTester(Tester object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Release Configurator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Release Configurator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReleaseConfigurator(ReleaseConfigurator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deployer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deployer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeployer(Deployer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFile(File object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileAggregation(FileAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestCase(TestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Case Aggregation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Case Aggregation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestCaseAggregation(TestCaseAggregation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Design Pattern Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Design Pattern Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDesignPatternSpecification(DesignPatternSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Design Pattern Role</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Design Pattern Role</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDesignPatternRole(DesignPatternRole object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Technology Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Technology Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTechnologySpecification(TechnologySpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Technological Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Technological Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTechnologicalCorrespondence(TechnologicalCorrespondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifier(Identifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //FieldOfActivityAnnotationsSwitch
