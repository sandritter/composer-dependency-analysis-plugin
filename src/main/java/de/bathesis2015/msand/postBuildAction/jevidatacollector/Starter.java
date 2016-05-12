package de.bathesis2015.msand.postBuildAction.jevidatacollector;

import java.io.File;

public class Starter { 
	
	public static void main(String[] args) {
		BuildDependencyPublisher build = new BuildDependencyPublisher("", "");
		build.loadProperties();
	}
	
}
