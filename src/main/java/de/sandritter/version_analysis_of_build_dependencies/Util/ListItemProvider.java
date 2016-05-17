package de.sandritter.version_analysis_of_build_dependencies.Util;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import hudson.util.ListBoxModel;

public class ListItemProvider {

	/**
	 * is looking for all files underneath the workspace directory of a
	 * build-job by given file name
	 * 
	 * @param workspace
	 * @param type
	 * @return
	 */
	public static ListBoxModel fillPathItems(Path workspace, FileType type)
	{
		ListBoxModel items = new ListBoxModel();
		List<Path> matches = getPathItems(workspace, type.toString());
		if (matches.size() != 0 && matches != null) {
			for (Path path : matches) {
				Path relative = workspace.relativize(path);
				items.add(relative.toString());
			}
		}
		items.add("default");
		return items;
	}
	
	/**
	 * @param workspace
	 * @param compare
	 * @return
	 */
	private static List<Path> getPathItems(Path workspace, String compare)
	{
		List<Path> matches = new ArrayList<Path>();
		if (workspace != null) {
			return findFiles(workspace, compare, matches);
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
	private static List<Path> findFiles(Path root, String compare, List<Path> lst)
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
}
