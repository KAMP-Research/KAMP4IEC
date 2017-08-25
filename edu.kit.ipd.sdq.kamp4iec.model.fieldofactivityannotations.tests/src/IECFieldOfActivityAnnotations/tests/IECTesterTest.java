/**
 */
package IECFieldOfActivityAnnotations.tests;

import IECFieldOfActivityAnnotations.IECFieldOfActivityAnnotationsFactory;
import IECFieldOfActivityAnnotations.IECTester;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IEC Tester</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IECTesterTest extends IECRoleTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IECTesterTest.class);
	}

	/**
	 * Constructs a new IEC Tester test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECTesterTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this IEC Tester test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IECTester getFixture() {
		return (IECTester)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IECFieldOfActivityAnnotationsFactory.eINSTANCE.createIECTester());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //IECTesterTest
