package de.sandritter.version_analysis_of_build_dependencies.DslConfiguration;

import javaposse.jobdsl.dsl.Context;

public class DSLContext implements Context {

	String lockPath;
	String jsonPath;

	public void lockPath(String value)
	{
		lockPath = value;
	}

	public void jsonPath(String value)
	{
		jsonPath = value;
	}
}
