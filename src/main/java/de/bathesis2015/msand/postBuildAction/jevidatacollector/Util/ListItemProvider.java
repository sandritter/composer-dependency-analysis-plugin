package de.bathesis2015.msand.postBuildAction.jevidatacollector.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.AncestorInPath;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.util.ListBoxModel;

public class ListItemProvider {

	/**
	 * 
	 * @param project
	 * @param type
	 * @return
	 */
	public static ListBoxModel fillPathItems(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project, FileType type)
	{
		ListBoxModel items = new ListBoxModel();
		Path workspace = getWorkspace(project);
		items.add(workspace.toAbsolutePath().toString());
		items.add(type.toString());
		List<Path> matches = getPathItems(workspace, type.toString());
		if (matches.size() == 0 && matches != null) {
			for (Path path : matches) {
				items.add("test");
				Path relative = workspace.relativize(path);
				items.add(relative.toString());
			}
		} 
		items.add("default");
		return items;
	}
	
	/**
	 * is looking for all files underneath the workspace directory of a
	 * build-job by given file name
	 * 
	 * @param project {@link AbstractProject}
	 * @param compare file name to look for
	 * @return list of {@link Path} of found files by compare value
	 */
	private static List<Path> getPathItems(Path workspace, String compare)
	{
		List<Path> matches = new ArrayList<Path>();
		if (workspace != null) {
			return findFiles(workspace, compare, matches);
		} else {
			
		}
		return matches;
	}
	
	/**
	 * is searching recursively for file names by given directory and name
	 * to compare with
	 * 
	 * @param root root directory from where to start the search from
	 * @param compare value to look for
	 * @param lst list to store {@link Path} found values
	 * @return list of {@link Path}
	 */
	public static List<Path> findFiles(Path root, String compare, List<Path> lst)
	{
		File dir = new File(root.toString());
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().equals(compare)) {
					lst.add(file.toPath());
				}
			} else if (file.isDirectory()) {
				if (!file.getName().equals("vendor")) {
					findFiles(file.toPath(), compare, lst);
				}
			}
		}
		return lst;
	}

	/**
	 * extracting workspace path of last build
	 * 
	 * @param project {@link AbstractProject}
	 * @return workspace path
	 */
	private static Path getWorkspace(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
	{
		AbstractBuild<?, ?> build = project.getLastBuild();
		FilePath path = build.getWorkspace();
		File root = null;
		
		try {
			root = new File(path.toURI().toURL().getPath());
		} catch (IOException e) {
			//TODO
		} catch (InterruptedException e) {
			//TODO
		}
		return root.toPath();
	}
}
