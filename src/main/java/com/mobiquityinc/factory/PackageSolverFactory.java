package com.mobiquityinc.factory;

public class PackageSolverFactory {
	public enum Approach {
		DYNAMIC_PROGRAMMING
	}

	private PackageSolverFactory() {
	}

	public static PackageSolverFactory getInstance() {
		return new PackageSolverFactory();
	}

	public PackageSolver newPackageSolver(Approach approach) {
		PackageSolver packageSolver;
		switch (approach) {
		case DYNAMIC_PROGRAMMING:
			packageSolver = new DynamicProgrammingPackageSolver();
			break;

		default:
			packageSolver = null;
			break;
		}
		return packageSolver;
	}
}
