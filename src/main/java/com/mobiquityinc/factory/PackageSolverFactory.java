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
		PackageSolver packageSolver = null;
		if (Approach.DYNAMIC_PROGRAMMING.equals(approach)) {
			packageSolver = new DynamicProgrammingPackageSolver();
		}
		return packageSolver;
	}
}
