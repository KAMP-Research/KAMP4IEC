/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl;

import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.BuildSpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.DeploymentSpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.DesignPatternSpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.DevelopmentArtefactSpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.FieldOfActivityAnnotationsPackage;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.FieldOfActivityAnnotationsRepository;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.ReleaseSpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.StaffSpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.TechnologySpecification;
import edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.TestSpecification;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getDevelopmentArtefactSpecification <em>Development Artefact Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getBuildSpecification <em>Build Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getTestSpecification <em>Test Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getReleaseSpecification <em>Release Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getDeploymentSpecification <em>Deployment Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getStaffSpecification <em>Staff Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getTechnologySpecification <em>Technology Specification</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.kamp4iec.model.FieldOfActivityAnnotations.impl.FieldOfActivityAnnotationsRepositoryImpl#getDesignPatternSpecification <em>Design Pattern Specification</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FieldOfActivityAnnotationsRepositoryImpl extends MinimalEObjectImpl.Container implements FieldOfActivityAnnotationsRepository {
	/**
	 * The cached value of the '{@link #getDevelopmentArtefactSpecification() <em>Development Artefact Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDevelopmentArtefactSpecification()
	 * @generated
	 * @ordered
	 */
	protected DevelopmentArtefactSpecification developmentArtefactSpecification;

	/**
	 * The cached value of the '{@link #getBuildSpecification() <em>Build Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildSpecification()
	 * @generated
	 * @ordered
	 */
	protected BuildSpecification buildSpecification;

	/**
	 * The cached value of the '{@link #getTestSpecification() <em>Test Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestSpecification()
	 * @generated
	 * @ordered
	 */
	protected TestSpecification testSpecification;

	/**
	 * The cached value of the '{@link #getReleaseSpecification() <em>Release Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReleaseSpecification()
	 * @generated
	 * @ordered
	 */
	protected ReleaseSpecification releaseSpecification;

	/**
	 * The cached value of the '{@link #getDeploymentSpecification() <em>Deployment Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentSpecification()
	 * @generated
	 * @ordered
	 */
	protected DeploymentSpecification deploymentSpecification;

	/**
	 * The cached value of the '{@link #getStaffSpecification() <em>Staff Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStaffSpecification()
	 * @generated
	 * @ordered
	 */
	protected StaffSpecification staffSpecification;

	/**
	 * The cached value of the '{@link #getTechnologySpecification() <em>Technology Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTechnologySpecification()
	 * @generated
	 * @ordered
	 */
	protected TechnologySpecification technologySpecification;

	/**
	 * The cached value of the '{@link #getDesignPatternSpecification() <em>Design Pattern Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesignPatternSpecification()
	 * @generated
	 * @ordered
	 */
	protected DesignPatternSpecification designPatternSpecification;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldOfActivityAnnotationsRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FieldOfActivityAnnotationsPackage.Literals.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DevelopmentArtefactSpecification getDevelopmentArtefactSpecification() {
		return developmentArtefactSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDevelopmentArtefactSpecification(DevelopmentArtefactSpecification newDevelopmentArtefactSpecification, NotificationChain msgs) {
		DevelopmentArtefactSpecification oldDevelopmentArtefactSpecification = developmentArtefactSpecification;
		developmentArtefactSpecification = newDevelopmentArtefactSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION, oldDevelopmentArtefactSpecification, newDevelopmentArtefactSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDevelopmentArtefactSpecification(DevelopmentArtefactSpecification newDevelopmentArtefactSpecification) {
		if (newDevelopmentArtefactSpecification != developmentArtefactSpecification) {
			NotificationChain msgs = null;
			if (developmentArtefactSpecification != null)
				msgs = ((InternalEObject)developmentArtefactSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.DEVELOPMENT_ARTEFACT_SPECIFICATION__PARENT, DevelopmentArtefactSpecification.class, msgs);
			if (newDevelopmentArtefactSpecification != null)
				msgs = ((InternalEObject)newDevelopmentArtefactSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.DEVELOPMENT_ARTEFACT_SPECIFICATION__PARENT, DevelopmentArtefactSpecification.class, msgs);
			msgs = basicSetDevelopmentArtefactSpecification(newDevelopmentArtefactSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION, newDevelopmentArtefactSpecification, newDevelopmentArtefactSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildSpecification getBuildSpecification() {
		return buildSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuildSpecification(BuildSpecification newBuildSpecification, NotificationChain msgs) {
		BuildSpecification oldBuildSpecification = buildSpecification;
		buildSpecification = newBuildSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION, oldBuildSpecification, newBuildSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildSpecification(BuildSpecification newBuildSpecification) {
		if (newBuildSpecification != buildSpecification) {
			NotificationChain msgs = null;
			if (buildSpecification != null)
				msgs = ((InternalEObject)buildSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.BUILD_SPECIFICATION__PARENT, BuildSpecification.class, msgs);
			if (newBuildSpecification != null)
				msgs = ((InternalEObject)newBuildSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.BUILD_SPECIFICATION__PARENT, BuildSpecification.class, msgs);
			msgs = basicSetBuildSpecification(newBuildSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION, newBuildSpecification, newBuildSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestSpecification getTestSpecification() {
		return testSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTestSpecification(TestSpecification newTestSpecification, NotificationChain msgs) {
		TestSpecification oldTestSpecification = testSpecification;
		testSpecification = newTestSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION, oldTestSpecification, newTestSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTestSpecification(TestSpecification newTestSpecification) {
		if (newTestSpecification != testSpecification) {
			NotificationChain msgs = null;
			if (testSpecification != null)
				msgs = ((InternalEObject)testSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.TEST_SPECIFICATION__PARENT, TestSpecification.class, msgs);
			if (newTestSpecification != null)
				msgs = ((InternalEObject)newTestSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.TEST_SPECIFICATION__PARENT, TestSpecification.class, msgs);
			msgs = basicSetTestSpecification(newTestSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION, newTestSpecification, newTestSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReleaseSpecification getReleaseSpecification() {
		return releaseSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReleaseSpecification(ReleaseSpecification newReleaseSpecification, NotificationChain msgs) {
		ReleaseSpecification oldReleaseSpecification = releaseSpecification;
		releaseSpecification = newReleaseSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION, oldReleaseSpecification, newReleaseSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReleaseSpecification(ReleaseSpecification newReleaseSpecification) {
		if (newReleaseSpecification != releaseSpecification) {
			NotificationChain msgs = null;
			if (releaseSpecification != null)
				msgs = ((InternalEObject)releaseSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.RELEASE_SPECIFICATION__PARENT, ReleaseSpecification.class, msgs);
			if (newReleaseSpecification != null)
				msgs = ((InternalEObject)newReleaseSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.RELEASE_SPECIFICATION__PARENT, ReleaseSpecification.class, msgs);
			msgs = basicSetReleaseSpecification(newReleaseSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION, newReleaseSpecification, newReleaseSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentSpecification getDeploymentSpecification() {
		return deploymentSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentSpecification(DeploymentSpecification newDeploymentSpecification, NotificationChain msgs) {
		DeploymentSpecification oldDeploymentSpecification = deploymentSpecification;
		deploymentSpecification = newDeploymentSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION, oldDeploymentSpecification, newDeploymentSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentSpecification(DeploymentSpecification newDeploymentSpecification) {
		if (newDeploymentSpecification != deploymentSpecification) {
			NotificationChain msgs = null;
			if (deploymentSpecification != null)
				msgs = ((InternalEObject)deploymentSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.DEPLOYMENT_SPECIFICATION__PARENT, DeploymentSpecification.class, msgs);
			if (newDeploymentSpecification != null)
				msgs = ((InternalEObject)newDeploymentSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.DEPLOYMENT_SPECIFICATION__PARENT, DeploymentSpecification.class, msgs);
			msgs = basicSetDeploymentSpecification(newDeploymentSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION, newDeploymentSpecification, newDeploymentSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaffSpecification getStaffSpecification() {
		return staffSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStaffSpecification(StaffSpecification newStaffSpecification, NotificationChain msgs) {
		StaffSpecification oldStaffSpecification = staffSpecification;
		staffSpecification = newStaffSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION, oldStaffSpecification, newStaffSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStaffSpecification(StaffSpecification newStaffSpecification) {
		if (newStaffSpecification != staffSpecification) {
			NotificationChain msgs = null;
			if (staffSpecification != null)
				msgs = ((InternalEObject)staffSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.STAFF_SPECIFICATION__PARENT, StaffSpecification.class, msgs);
			if (newStaffSpecification != null)
				msgs = ((InternalEObject)newStaffSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.STAFF_SPECIFICATION__PARENT, StaffSpecification.class, msgs);
			msgs = basicSetStaffSpecification(newStaffSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION, newStaffSpecification, newStaffSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TechnologySpecification getTechnologySpecification() {
		return technologySpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTechnologySpecification(TechnologySpecification newTechnologySpecification, NotificationChain msgs) {
		TechnologySpecification oldTechnologySpecification = technologySpecification;
		technologySpecification = newTechnologySpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION, oldTechnologySpecification, newTechnologySpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTechnologySpecification(TechnologySpecification newTechnologySpecification) {
		if (newTechnologySpecification != technologySpecification) {
			NotificationChain msgs = null;
			if (technologySpecification != null)
				msgs = ((InternalEObject)technologySpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.TECHNOLOGY_SPECIFICATION__PARENT, TechnologySpecification.class, msgs);
			if (newTechnologySpecification != null)
				msgs = ((InternalEObject)newTechnologySpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.TECHNOLOGY_SPECIFICATION__PARENT, TechnologySpecification.class, msgs);
			msgs = basicSetTechnologySpecification(newTechnologySpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION, newTechnologySpecification, newTechnologySpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignPatternSpecification getDesignPatternSpecification() {
		return designPatternSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDesignPatternSpecification(DesignPatternSpecification newDesignPatternSpecification, NotificationChain msgs) {
		DesignPatternSpecification oldDesignPatternSpecification = designPatternSpecification;
		designPatternSpecification = newDesignPatternSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION, oldDesignPatternSpecification, newDesignPatternSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesignPatternSpecification(DesignPatternSpecification newDesignPatternSpecification) {
		if (newDesignPatternSpecification != designPatternSpecification) {
			NotificationChain msgs = null;
			if (designPatternSpecification != null)
				msgs = ((InternalEObject)designPatternSpecification).eInverseRemove(this, FieldOfActivityAnnotationsPackage.DESIGN_PATTERN_SPECIFICATION__PARENT, DesignPatternSpecification.class, msgs);
			if (newDesignPatternSpecification != null)
				msgs = ((InternalEObject)newDesignPatternSpecification).eInverseAdd(this, FieldOfActivityAnnotationsPackage.DESIGN_PATTERN_SPECIFICATION__PARENT, DesignPatternSpecification.class, msgs);
			msgs = basicSetDesignPatternSpecification(newDesignPatternSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION, newDesignPatternSpecification, newDesignPatternSpecification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION:
				if (developmentArtefactSpecification != null)
					msgs = ((InternalEObject)developmentArtefactSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION, null, msgs);
				return basicSetDevelopmentArtefactSpecification((DevelopmentArtefactSpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION:
				if (buildSpecification != null)
					msgs = ((InternalEObject)buildSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION, null, msgs);
				return basicSetBuildSpecification((BuildSpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION:
				if (testSpecification != null)
					msgs = ((InternalEObject)testSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION, null, msgs);
				return basicSetTestSpecification((TestSpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION:
				if (releaseSpecification != null)
					msgs = ((InternalEObject)releaseSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION, null, msgs);
				return basicSetReleaseSpecification((ReleaseSpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION:
				if (deploymentSpecification != null)
					msgs = ((InternalEObject)deploymentSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION, null, msgs);
				return basicSetDeploymentSpecification((DeploymentSpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION:
				if (staffSpecification != null)
					msgs = ((InternalEObject)staffSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION, null, msgs);
				return basicSetStaffSpecification((StaffSpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION:
				if (technologySpecification != null)
					msgs = ((InternalEObject)technologySpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION, null, msgs);
				return basicSetTechnologySpecification((TechnologySpecification)otherEnd, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION:
				if (designPatternSpecification != null)
					msgs = ((InternalEObject)designPatternSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION, null, msgs);
				return basicSetDesignPatternSpecification((DesignPatternSpecification)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION:
				return basicSetDevelopmentArtefactSpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION:
				return basicSetBuildSpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION:
				return basicSetTestSpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION:
				return basicSetReleaseSpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION:
				return basicSetDeploymentSpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION:
				return basicSetStaffSpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION:
				return basicSetTechnologySpecification(null, msgs);
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION:
				return basicSetDesignPatternSpecification(null, msgs);
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
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION:
				return getDevelopmentArtefactSpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION:
				return getBuildSpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION:
				return getTestSpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION:
				return getReleaseSpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION:
				return getDeploymentSpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION:
				return getStaffSpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION:
				return getTechnologySpecification();
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION:
				return getDesignPatternSpecification();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION:
				setDevelopmentArtefactSpecification((DevelopmentArtefactSpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION:
				setBuildSpecification((BuildSpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION:
				setTestSpecification((TestSpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION:
				setReleaseSpecification((ReleaseSpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION:
				setDeploymentSpecification((DeploymentSpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION:
				setStaffSpecification((StaffSpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION:
				setTechnologySpecification((TechnologySpecification)newValue);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION:
				setDesignPatternSpecification((DesignPatternSpecification)newValue);
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
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION:
				setDevelopmentArtefactSpecification((DevelopmentArtefactSpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION:
				setBuildSpecification((BuildSpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION:
				setTestSpecification((TestSpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION:
				setReleaseSpecification((ReleaseSpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION:
				setDeploymentSpecification((DeploymentSpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION:
				setStaffSpecification((StaffSpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION:
				setTechnologySpecification((TechnologySpecification)null);
				return;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION:
				setDesignPatternSpecification((DesignPatternSpecification)null);
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
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEVELOPMENT_ARTEFACT_SPECIFICATION:
				return developmentArtefactSpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__BUILD_SPECIFICATION:
				return buildSpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TEST_SPECIFICATION:
				return testSpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__RELEASE_SPECIFICATION:
				return releaseSpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DEPLOYMENT_SPECIFICATION:
				return deploymentSpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__STAFF_SPECIFICATION:
				return staffSpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__TECHNOLOGY_SPECIFICATION:
				return technologySpecification != null;
			case FieldOfActivityAnnotationsPackage.FIELD_OF_ACTIVITY_ANNOTATIONS_REPOSITORY__DESIGN_PATTERN_SPECIFICATION:
				return designPatternSpecification != null;
		}
		return super.eIsSet(featureID);
	}

} //FieldOfActivityAnnotationsRepositoryImpl
