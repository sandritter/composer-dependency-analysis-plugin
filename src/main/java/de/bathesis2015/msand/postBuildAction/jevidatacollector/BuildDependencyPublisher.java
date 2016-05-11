package de.bathesis2015.msand.postBuildAction.jevidatacollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Transport;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.BuildEnvVars;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.FileType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.SourceType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Exception.DataMappingFailedException;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Facade.MappingFacade;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Module.MappingModule;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataLoaderFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Factory.DataStorageFactory;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.IO.Interface.IOAccess;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module.FileLoadModule;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Module.PersistenceModule;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.Logger;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.PathResolver;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.Interface.Resolver;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import net.sf.json.JSONObject;

/**
 * 
 * BuildDependencyPublisher.java
 * 
 * This object is implementing the {@link Recorder} Extension Point therefore it
 * can be used as Post-Build-Action in every build-job of a jenkins server
 * 
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked and a new
 * {@link BuildDependencyPublisher} is created. The created instance is
 * persisted to the project configuration XML by using XStream, so this allows
 * to use instance fields (like {@link #lockPath}) to remember the
 * configuration.
 *
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 *
 * @author Michael Sandritter
 */
public class BuildDependencyPublisher extends Recorder {

	/**
	 * path to the composer.lock file
	 */
	private final String lockPath;

	/**
	 * path to the composer.json file
	 */
	private final String jsonPath;

	/**
	 * logger that logs all warnings and errors to console output
	 */
	public static Logger logger;

	private IOAccess ioAccess;
	private MappingFacade mappingFacade;
	private DataLoader dataLoader;
	private DataStorage dataStorage;

	/**
	 * file name of the xml file where the information of the jevi data
	 * collector is saved
	 */
	@SuppressWarnings("unused")
	private final String XML_FILE_NAME = "dependencies.xml";

	/**
	 * constructor retrieving the paths to the composer.lock an composer.json
	 * file from the project configuration
	 * 
	 * @param jsonPath
	 *            - file path of a composer.json file
	 * @param lockPath
	 *            - file path of a composer.lock file
	 */
	@DataBoundConstructor
	public BuildDependencyPublisher(String jsonPath, String lockPath)
	{
		this.lockPath = lockPath;
		this.jsonPath = jsonPath;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
	{

		logger = Logger.getInstance(listener);
		logger.logPluginStart();

		Resolver pathResolver = new PathResolver(logger, build);

		createModules(getDescriptor().getDbPath());

		Map<FileType, File> dependencyReflectionFiles = loadDependencyReflectionFiles(pathResolver);

		BuildData buildData = collectBuildData(build, listener);

		Transferable transport = mapData(buildData, dependencyReflectionFiles);

		storeData(transport);

		analyseBuild(build, buildData);

		resolveDependencies(build, buildData);

		logger.logFinalProcessStatus();
		return true;
	}

	private Map<FileType, File> loadDependencyReflectionFiles(Resolver pathResolver)
	{
		String finalLockPath = pathResolver.getAbsolutePath(FileType.COMPOSER_LOCK, lockPath);
		String finalJsonPath = pathResolver.getAbsolutePath(FileType.COMPOSER_JSON, jsonPath);
		Map<FileType, File> files = new HashMap<FileType, File>();
		files.put(FileType.COMPOSER_JSON, loadFile(finalJsonPath));
		files.put(FileType.COMPOSER_LOCK, loadFile(finalLockPath));
		return files;
	}

	/**
	 * TODO
	 * 
	 * @param buildData
	 * @param files
	 * @return
	 */
	private Transferable mapData(BuildData buildData, Map<FileType, File> files)
	{
		Transferable transport = null;
		try {
			transport = mappingFacade.mapRowData(buildData, files);
			logger.println(
				Logger.LABEL + Logger.SUCCESS
				+ "collected build- and version-information of this build has been successfully mapped to data access objects"
			);
		} catch (DataMappingFailedException e1) {
			logger.logFailure(e1, "DATA MAPPING FAILED");
		}
		return transport;
	}

	/**
	 * TODO
	 * 
	 * @param build
	 * @param buildData
	 */
	private void resolveDependencies(AbstractBuild<?, ?> build, BuildData buildData)
	{
		try {
			build.addAction(new DependentComponentResolver(dataLoader, buildData));
			logger.println(Logger.LABEL + Logger.SUCCESS + "dependent components have been sucessfully resolved");
		} catch (Exception e) {
			logger.logFailure(e, "RESOLVING DEPENDENT COMPONENTS FAILED");
		}
	}

	/**
	 * TODO
	 * 
	 * @param transport
	 */
	private void storeData(Transferable transport)
	{
		try {
			dataStorage.storeData(transport);
			logger.println(
					Logger.LABEL + Logger.SUCCESS + "data access objects have been successfully stored to database");
		} catch (Exception e) {
			logger.logFailure(e, "STORAGE FAILED");
		}
	}

	/**
	 * TODO
	 * 
	 * @param build
	 * @param buildData
	 */
	private void analyseBuild(AbstractBuild<?, ?> build, BuildData buildData)
	{
		try {
			build.addAction(new IntegrationAnalyser(buildData, dataLoader));
			logger.println(
				Logger.LABEL + Logger.SUCCESS
				+ "analysis of all installed components of this build have been successful"
			);
		} catch (Exception e) {
			logger.logFailure(e, "ANALYSIS FAILED");
		}
	}

	/**
	 * is creating all persistence and mapping objects via dependency injection
	 * 
	 * @param path
	 */
	private void createModules(String path)
	{
		Injector injector = Guice.createInjector(new FileLoadModule(), new MappingModule(), new PersistenceModule());
		DataLoaderFactory dataLoaderFactory = injector.getInstance(DataLoaderFactory.class);
		this.dataLoader = dataLoaderFactory.create(path);

		DataStorageFactory dataStorageFactory = injector.getInstance(DataStorageFactory.class);
		this.dataStorage = dataStorageFactory.create(path);

		this.ioAccess = injector.getInstance(IOAccess.class);

		this.mappingFacade = injector.getInstance(MappingFacade.class);
	}

	/**
	 * collects build specific data
	 * 
	 * @param build
	 *            {@link AbstractBuild}
	 * @param l
	 *            {@link BuildListener}
	 * @return BuildData object that holds all relevant build-specific data
	 */
	private BuildData collectBuildData(AbstractBuild<?, ?> build, BuildListener l)
	{
		BuildData data = new BuildData();
		EnvVars env = null;
		try {
			env = build.getEnvironment(l);
		} catch (IOException e) {
			logger.logFailure(e, "LOADING ENVIRONMENT OF BUILD FAILED");
		} catch (InterruptedException e) {
			logger.logFailure(e, "LOADING ENVIRONMENT OF BUILD STOPPED");
		}
		data.setJobName(env.get(BuildEnvVars.JOB_NAME.toString()));
		data.setJenkinsUrl(env.get(BuildEnvVars.JENKINS_URL.toString()));
		data.setBuildId(build.getId() + Long.toString(build.getTimeInMillis()));
		data.setNumber(build.getNumber());
		data.setTimestamp(build.getTimeInMillis());
		data.setDbPath(getDescriptor().getDbPath());
		injectVersionControlInfo(data, env);
		return data;
	}

	/**
	 * checks if main component is stored in svn or git and adds the relevant
	 * data to {@link BuildData}
	 * 
	 * @param data
	 *            {@link BuildData}
	 * @param env
	 *            environment variables of {@link AbstractBuild}
	 */
	private void injectVersionControlInfo(BuildData data, EnvVars env)
	{
		String svnRevision = env.get(BuildEnvVars.SVN_REVISION.toString());
		String gitRevision = env.get(BuildEnvVars.GIT_COMMIT.toString());
		if (gitRevision != null) {
			data.setSourceType(SourceType.GIT.toString());
			data.setSourceUrl(env.get(BuildEnvVars.GIT_URL.toString()));
			data.setRevision(gitRevision);
			data.setVersion(env.get(BuildEnvVars.GIT_TAG_NAME.toString()));
		} else if (svnRevision != null) {
			data.setSourceType(SourceType.SVN.toString());
			data.setSourceUrl(env.get(BuildEnvVars.SVN_URL.toString()));
			data.setRevision(svnRevision);
			data.setVersion(svnRevision);
		}
	}

	/**
	 * is laoding a file by given path
	 * 
	 * @param path
	 * @return loaded {@link File}
	 */
	private File loadFile(String path)
	{
		File file = null;
		try {
			file = ioAccess.load(path);
		} catch (IOException e) {
			logger.logFailure(e, "FILE LOADING FAILED");
		}
		return file;
	}

	@Override
	public DescriptorImpl getDescriptor()
	{
		return (DescriptorImpl) super.getDescriptor();
	}

	/**
	 * Descriptor for {@link BuildDependencyPublisher}. Used as a singleton. The
	 * class is marked as public so that it can be accessed from views.
	 */
	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

		/**
		 * database path
		 */
		private String dbPath;

		public String getDbPath()
		{
			return dbPath;
		}

		/**
		 * In order to load the persisted global configuration, you have to call
		 * load() in the constructor.
		 */
		public DescriptorImpl()
		{
			load();
		}

		/**
		 * is filling the selectItems of the dropdown list of the composer.lock
		 * configuration
		 * 
		 * @param project
		 *            {@link AbstractProject}
		 * @return {@link ListBoxModel}
		 */
		public ListBoxModel doFillLockPathItems(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
		{
			ListBoxModel items = new ListBoxModel();
			List<Path> matches = getPathItems(project, "composer.lock");
			if (matches != null) {
				for (Path path : matches) {
					Path base = getWorkspace(project);
					Path relative = base.relativize(path);
					items.add(relative.toString());
				}
			} else {
				items.add("default");
			}
			return items;
		}

		/**
		 * is filling the selectItems of the dropdown list of the composer.json
		 * configuration
		 * 
		 * @param project
		 *            {@link AbstractProject}
		 * @return {@link ListBoxModel}
		 */
		public ListBoxModel doFillJsonPathItems(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
		{
			ListBoxModel items = new ListBoxModel();
			List<Path> matches = getPathItems(project, "composer.json");
			if (matches != null) {
				for (Path path : matches) {
					Path base = getWorkspace(project);
					Path relative = base.relativize(path);
					items.add(relative.toString());
				}
			} else {
				items.add("default");
			}
			return items;
		}

		/**
		 * is looking for all files underneath the workspace directory of a
		 * build-job by given file name
		 * 
		 * @param project
		 *            {@link AbstractProject}
		 * @param compare
		 *            - file name to look for
		 * @return list of {@link Path} of found files by compare value
		 */
		private List<Path> getPathItems(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project,
				String compare)
		{
			List<Path> matches = new ArrayList<Path>();
			Path root = getWorkspace(project);
			if (root != null) {
				return findFiles(root, compare, matches);
			}
			return matches;
		}

		/**
		 * extracting workspace path of last build
		 * 
		 * @param project
		 *            {@link AbstractProject}
		 * @return workspace path
		 */
		private Path getWorkspace(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
		{
			AbstractBuild<?, ?> build = project.getLastBuild();
			FilePath path = build.getWorkspace();
			File root = null;
			try {
				root = new File(path.toURI().toURL().getPath());
			} catch (IOException e) {

			} catch (InterruptedException e) {

			}
			return root.toPath();
		}

		/**
		 * is searching recursively for file names by given directory and name
		 * to compare with
		 * 
		 * @param root
		 *            - root directory from where to start the search from
		 * @param compare
		 *            - value to look for
		 * @param lst
		 *            - list to store {@link Path} found values
		 * @return list of {@link Path}
		 */
		public List<Path> findFiles(Path root, String compare, List<Path> lst)
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
		 * is validating the database path entered via the global configration
		 * 
		 * @param value
		 *            - entered database path
		 * @param project
		 *            {@link AbstractProject}
		 * @return {@link FormValidation}
		 */
		public FormValidation doCheckDbPath(@QueryParameter String value,
				@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
		{
			File f = new File(value);
			if (f.isFile()) {
				if (isDbFile(f)) {
					return FormValidation.ok();
				} else {
					return FormValidation
							.error("The given file is not a sqlite database file. File should have .db suffix");
				}
			} else if (f.isDirectory()) {
				return FormValidation
						.error("The given path points on a directoy. Please specify a sqlite database file");
			} else {
				return FormValidation.error("Invalid path. Please specify a sqlite database file");
			}
		}

		/**
		 * checks if a file is a sqlite database file by looking at it's suffix
		 * *.db
		 * 
		 * @param file
		 *            {@link File}
		 * @return
		 */
		private boolean isDbFile(File file)
		{
			if (file.getName().endsWith(".db"))
				return true;
			return false;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public boolean isApplicable(Class<? extends AbstractProject> aClass)
		{
			// Indicates that this builder can be used with all kinds of project
			// types
			return true;
		}

		/**
		 * This human readable name is used in the configuration screen.
		 */
		@Override
		public String getDisplayName()
		{
			return "Version Analysis Of Integration Dependencies";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException
		{
			// To persist global configuration information,
			// set that to properties and call save().
			// ^Can also use req.bindJSON(this, formData);
			// (easier when there are many fields; need set* methods for this,
			dbPath = formData.getString("dbPath");
			save();
			return super.configure(req, formData);
		}
	}

	@Override
	public BuildStepMonitor getRequiredMonitorService()
	{
		return BuildStepMonitor.STEP;
	}

	public String getLockPath()
	{
		return lockPath;
	}

	public String getJsonPath()
	{
		return lockPath;
	}
}
