package de.sandritter.version_analysis_of_build_dependencies;

import java.io.File;

public class Starter { 
	
	public static void main(String[] args) {
		BuildDependencyPublisher build = new BuildDependencyPublisher("", "");
		build.loadProperties();
	}
	
}
