/**
 */
package edu.kit.ipd.sdq.kamp4iec.model.IECModel.util;

import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Configuration;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.DependencyResource;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enums;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Function;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.FunctionBlock;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.GlobalVariable;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECComponent;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECInterface;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethod;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECMethodImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECModelPackage;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECProperty;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECPropertyImplementation;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Interfaces;
import edu.kit.ipd.sdq.kamp4iec.model.IECModel.Program;

import edu.kit.ipd.sdq.kamp4iec.model.IECRepository.Identifier;

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
 * @see edu.kit.ipd.sdq.kamp4iec.model.IECModel.IECModelPackage
 * @generated
 */
public class IECModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IECModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IECModelSwitch() {
		if (modelPackage == null) {
			modelPackage = IECModelPackage.eINSTANCE;
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
			case IECModelPackage.FUNCTION_BLOCK: {
				FunctionBlock functionBlock = (FunctionBlock)theEObject;
				T result = caseFunctionBlock(functionBlock);
				if (result == null) result = caseIECComponent(functionBlock);
				if (result == null) result = caseDependencyResource(functionBlock);
				if (result == null) result = caseIdentifier(functionBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = caseIECComponent(function);
				if (result == null) result = caseIdentifier(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.PROGRAM: {
				Program program = (Program)theEObject;
				T result = caseProgram(program);
				if (result == null) result = caseIECComponent(program);
				if (result == null) result = caseIdentifier(program);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.CONFIGURATION: {
				Configuration configuration = (Configuration)theEObject;
				T result = caseConfiguration(configuration);
				if (result == null) result = caseIECComponent(configuration);
				if (result == null) result = caseIdentifier(configuration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.IEC_INTERFACE: {
				IECInterface iecInterface = (IECInterface)theEObject;
				T result = caseIECInterface(iecInterface);
				if (result == null) result = caseIECComponent(iecInterface);
				if (result == null) result = caseDependencyResource(iecInterface);
				if (result == null) result = caseIdentifier(iecInterface);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.GLOBAL_VARIABLE: {
				GlobalVariable globalVariable = (GlobalVariable)theEObject;
				T result = caseGlobalVariable(globalVariable);
				if (result == null) result = caseIECComponent(globalVariable);
				if (result == null) result = caseIdentifier(globalVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.IEC_METHOD_IMPLEMENTATION: {
				IECMethodImplementation iecMethodImplementation = (IECMethodImplementation)theEObject;
				T result = caseIECMethodImplementation(iecMethodImplementation);
				if (result == null) result = caseIECComponent(iecMethodImplementation);
				if (result == null) result = caseDependencyResource(iecMethodImplementation);
				if (result == null) result = caseIdentifier(iecMethodImplementation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.IEC_PROPERTY_IMPLEMENTATION: {
				IECPropertyImplementation iecPropertyImplementation = (IECPropertyImplementation)theEObject;
				T result = caseIECPropertyImplementation(iecPropertyImplementation);
				if (result == null) result = caseIECComponent(iecPropertyImplementation);
				if (result == null) result = caseDependencyResource(iecPropertyImplementation);
				if (result == null) result = caseIdentifier(iecPropertyImplementation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.ENUM: {
				edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum enum_ = (edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum)theEObject;
				T result = caseEnum(enum_);
				if (result == null) result = caseIECComponent(enum_);
				if (result == null) result = caseIdentifier(enum_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.IEC_COMPONENT: {
				IECComponent iecComponent = (IECComponent)theEObject;
				T result = caseIECComponent(iecComponent);
				if (result == null) result = caseIdentifier(iecComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.DEPENDENCY_RESOURCE: {
				DependencyResource dependencyResource = (DependencyResource)theEObject;
				T result = caseDependencyResource(dependencyResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.IEC_PROPERTY: {
				IECProperty iecProperty = (IECProperty)theEObject;
				T result = caseIECProperty(iecProperty);
				if (result == null) result = caseIECComponent(iecProperty);
				if (result == null) result = caseIdentifier(iecProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.IEC_METHOD: {
				IECMethod iecMethod = (IECMethod)theEObject;
				T result = caseIECMethod(iecMethod);
				if (result == null) result = caseIECComponent(iecMethod);
				if (result == null) result = caseIdentifier(iecMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.INTERFACES: {
				Interfaces interfaces = (Interfaces)theEObject;
				T result = caseInterfaces(interfaces);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IECModelPackage.ENUMS: {
				Enums enums = (Enums)theEObject;
				T result = caseEnums(enums);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionBlock(FunctionBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Program</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Program</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProgram(Program object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfiguration(Configuration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEC Interface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEC Interface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIECInterface(IECInterface object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalVariable(GlobalVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEC Method Implementation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEC Method Implementation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIECMethodImplementation(IECMethodImplementation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEC Property Implementation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEC Property Implementation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIECPropertyImplementation(IECPropertyImplementation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnum(edu.kit.ipd.sdq.kamp4iec.model.IECModel.Enum object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEC Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEC Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIECComponent(IECComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dependency Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dependency Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDependencyResource(DependencyResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEC Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEC Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIECProperty(IECProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEC Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEC Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIECMethod(IECMethod object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Interfaces</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Interfaces</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInterfaces(Interfaces object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enums</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enums</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnums(Enums object) {
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

} //IECModelSwitch
