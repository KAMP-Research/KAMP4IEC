# KAMP4IEC

KAMP4IEC extends KAMP for PLC software and IEC 61131-style automation software repositories. It provides EMF models, generated editors, field-of-activity annotations, and IEC-specific modification marks for analysing changes in function blocks, functions, interfaces, methods, properties, programs, and configurations.

## Installation

Install KAMP4IEC from the KAMP update-site page:

```text
https://kamp-research.github.io/KAMP/kamp4iec/
```

For a complete setup, use the composite update site instead:

```text
https://kamp-research.github.io/KAMP/all/
```

In Eclipse, open `Help > Install New Software...`, paste the update-site URL into `Work with`, select the KAMP4IEC feature, and finish the wizard. Restart Eclipse after installation.

## Dependencies

KAMP4IEC depends on:

- KAMP core for base change-propagation and modification mark infrastructure.
- Eclipse Modeling Framework (EMF), EMF.Edit, and generated EMF editor support.
- Palladio build dependencies from the Palladio 5.2.1 update sites.
- Eclipse Platform/PDE dependencies from the Eclipse 2023-03 target.
- KAMP4HMI in scenarios where HMI interaction models reference IEC artefacts.

KAMP4IEC and KAMP4HMI have circular model dependencies and should be installed from a consistent KAMP update-site set, preferably through KAMP-All.

## Domain

The IEC domain describes PLC software repositories and related engineering artefacts. It focuses on software-level elements such as function blocks, global variables, functions, interfaces, methods, properties, programs, and configurations. It also contains annotations for development artefacts, tests, metadata, and staff responsibilities.

## Important Models

- `IECRepository.ecore`: repository model with `Repository`, `FunctionBlock`, `GlobalVariable`, `Function`, `IECInterface`, `IECMethod`, `IECProperty`, `IECComponent`, `Identifier`, and derived type concepts.
- `IECModel.ecore`: top-level IEC software model with `Program` and `Configuration`.
- `IECFieldOfActivityAnnotations.ecore`: field-of-activity annotations for source files, metadata files, unit and acceptance tests, staff, roles, developers, and test developers.
- `IECModificationmarks.ecore`: modification marks such as `IECSeedModifications`, `IECChangePropagationDueToDataDependency`, `IECModifyComponent`, `IECModifyGlobalVariable`, `IECModifyFunctionBlock`, `IECModifyFunction`, `IECModifyInterface`, `IECModifyMethod`, `IECModifyProperty`, `IECModifyProgram`, and `IECModifyConfiguration`.

## Usage

Create or import IEC repository models and annotate related development artefacts, tests, and staff responsibilities. Represent changed IEC elements with IEC modification marks. KAMP4IEC then contributes the IEC-specific vocabulary needed by KAMP to propagate changes and derive follow-up activities.

When HMI models are involved, install KAMP4HMI alongside KAMP4IEC or use the KAMP-All update site.
