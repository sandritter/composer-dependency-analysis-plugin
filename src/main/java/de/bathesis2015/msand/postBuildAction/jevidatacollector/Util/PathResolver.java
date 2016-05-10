package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import hudson.model.AbstractBuild;

public class PathResolver {
	
	private Logger logger;
	private AbstractBuild<?, ?> build;
	
	public PathResolver(Logger logger, AbstractBuild<?, ?> build) {
		this.logger = logger;
		this.build = build;
	}
	
	/**
	 * is concatenating the composer.lock file path from the configuration with
	 * the workspace path of a project (build-job)
	 * 
	 * @param build
	 *            {@link AbstractBuild}
	 * @return absolute path of the composer.lock path
	 */
	public String getLockPath(String lockPath) {
		String workspacePath = loadWorkspacePath(build);
		if (lockPath.equals("default")) {
			return getAbsolutePath("source/composer.lock", workspacePath);
		}
		return getAbsolutePath(lockPath, workspacePath);
	}

	/**
	 * is concatenating the composer.json file path from the configuration with
	 * the workspace path of a project (build-job)
	 * 
	 * @param build
	 *            {@link AbstractBuild}
	 * @return absolute path to the composer.json file
	 */
	public String getJsonPath(String jsonPath) {
		String workspacePath = loadWorkspacePath(build);
		if (jsonPath.equals("default")) {
			return getAbsolutePath("source/composer.json", workspacePath);
		}
		return getAbsolutePath(jsonPath, workspacePath);
	}
	
	/**
	 * is loading the workspace path of a build instance
	 * 
	 * @param build
	 *            {@link AbstractBuild}
	 * @return workspace path
	 */
	private String loadWorkspacePath(AbstractBuild<?, ?> build) {
		String workspace = "";
		try {
			workspace = build.getWorkspace().toURI().toURL().getPath();
		} catch (MalformedURLException e) {
			logger.logFailure(e, "LOADING WORKSPACE FAILED");
		} catch (IOException e) {
			logger.logFailure(e, "LOADING WORKSPACE FAILED");
		} catch (InterruptedException e) {
			logger.logFailure(e, "LOADING WORKSPACE FAILED");
		}
		return workspace;
	}
	
	/**
	 * concatenates a file name and directory and returns a absolute path of
	 * that file
	 * 
	 * @param value
	 *            relative path
	 * @param workspace
	 *            workspace path
	 * @return absolute path
	 */
	private String getAbsolutePath(String value, String workspace) {
		File f = new File(value);
		File workspacePath = new File(workspace);
		File absolutePath = new File(workspacePath, value);
		if (f.isFile() && f.isAbsolute()) {
			return f.getAbsolutePath();
		}
		return absolutePath.getAbsolutePath();
	}

}
