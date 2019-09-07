package com.mobiquityinc.factory;

public class PackageSolverFactory {
	private PackageSolverFactory() {
	}

	public static PackageSolverFactory getInstance() {
		return new PackageSolverFactory();
	}

	public PackageSolver newDynamicProgrammingPackageSolver() {
		return new DynamicProgrammingPackageSolver();
	}
}
