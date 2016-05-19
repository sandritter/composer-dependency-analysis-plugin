package de.sandritter.version_analysis_of_build_dependencies;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.BuildData;
import de.sandritter.version_analysis_of_build_dependencies.Domain.Model.Transfer.Interface.Transferable;
import de.sandritter.version_analysis_of_build_dependencies.Exception.PluginConfigurationException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.BuildEnvVars;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.FileType;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Enum.SourceType;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Exception.DataMappingFailedException;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Facade.MappingFacade;
import de.sandritter.version_analysis_of_build_dependencies.Mapping.Module.MappingModule;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataLoaderFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Factory.DataStorageFactory;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface.DataLoader;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Database.Interface.DataStorage;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.Interface.IOAccess;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Module.FileLoadModule;
import de.sandritter.version_analysis_of_build_dependencies.Persistence.Module.PersistenceModule;
import de.sandritter.version_analysis_of_build_dependencies.Util.ListItemProvider;
import de.sandritter.version_analysis_of_build_dependencies.Util.Logger;
import de.sandritter.version_analysis_of_build_dependencies.Util.PathResolver;
import de.sandritter.version_analysis_of_build_dependencies.Util.Interface.Resolver;
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
	 * workspace path of this build
	 */
	private String workspacePath;

	/**
	 * logger that logs all warnings and errors to console output
	 */
	private static Logger logger;
	public static java.util.logging.Logger jenkinsLogger;

	private IOAccess ioAccess;
	private MappingFacade mappingFacade;
	private DataLoader dataLoader;
	private DataStorage dataStorage;
	private Properties config;

	/**
	 * constructor retrieving the paths to the composer.lock an composer.json
	 * file from the project configuration
	 * 
	 * @param jsonPath file path of a composer.json file
	 * @param lockPath file path of a composer.lock file
	 */
	@DataBoundConstructor
	public BuildDependencyPublisher(String jsonPath, String lockPath)
	{
		this.lockPath = lockPath;
		this.jsonPath = jsonPath;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws IOException
	{
		logger = Logger.getInstance(listener.getLogger());
		logger.logPluginStart();
		
		this.workspacePath = loadWorkspacePath(build);
		Resolver pathResolver = new PathResolver();
		createModules(loadDatabasePath());

		Map<FileType, File> dependencyReflectionFiles = loadDependencyReflectionFiles(pathResolver);
		BuildData buildData = collectBuildData(build, listener);
		Transferable transport = mapData(buildData, dependencyReflectionFiles);

		storeData(transport);

		analyseBuild(build, buildData);
		resolveDependencies(build, buildData);
		logger.logFinalProcessStatus();
		
		return true;
	}
	
	/**
	 * loads the database path from the inner discriptor implementation
	 * 
	 * @return String
	 */
	private String loadDatabasePath()
	{
		String databasePath = null;
		try {
			databasePath = getDescriptor().getDbPath();
			if (databasePath == null){
				throw new PluginConfigurationException("could not find database path configuration");
			}
		} catch (PluginConfigurationException e) {
			logger.logFailure(
				new PluginConfigurationException("could not find databasePath", e), 
				"Did you configure the database in your global jenkins settings? ;)"
			);
		}
		return databasePath;
	}

	/**
	 * collects all dependency reflection files
	 * 
	 * @param pathResolver {@link Resolver}
	 * @return Map<{@link FileType},{@link File}>
	 */
	private Map<FileType, File> loadDependencyReflectionFiles(Resolver pathResolver)
	{
		String finalLockPath = pathResolver
				.resolveAbsolutePath(FileType.COMPOSER_LOCK, workspacePath, lockPath);
		String finalJsonPath = pathResolver.
				resolveAbsolutePath(FileType.COMPOSER_JSON, workspacePath, jsonPath);
		
		Map<FileType, File> files = new HashMap<FileType, File>();
		files.put(FileType.COMPOSER_JSON, loadFile(finalJsonPath));
		files.put(FileType.COMPOSER_LOCK, loadFile(finalLockPath));
		return files;
	}
	
	/**
	 * is loading the workspace path of a build instance
	 * 
	 * @param build {@link AbstractBuild}
	 * @return workspace path
	 */
	private String loadWorkspacePath(AbstractBuild<?, ?> build)
	{
		String path = "";
		try {
			FilePath workspace = build.getWorkspace();
			path = workspace.toURI().toURL().getPath();
		} catch (MalformedURLException e) {
			logger.logFailure(e, "LOADING WORKSPACE FAILED");
		} catch (IOException e) {
			logger.logFailure(e, "LOADING WORKSPACE FAILED");
		} catch (InterruptedException e) {
			logger.logFailure(e, "LOADING WORKSPACE FAILED");
		}
		return path;
	}

	/**
	 * @param buildData
	 * @param files
	 * @return
	 */
	private Transferable mapData(BuildData buildData, Map<FileType, File> files)
	{
		Transferable transport = null;
		try {
			transport = mappingFacade.mapRowData(buildData, files);
			logger.log(
				Logger.LABEL + 
				Logger.SUCCESS + 
				"collected build- and version-information of this build has been successfully mapped to data access objects"
			);
		} catch (DataMappingFailedException e1) {
			logger.logFailure(e1, "DATA MAPPING FAILED");
		}
		return transport;
	}

	/**
	 * @param build
	 * @param buildData
	 */
	private void resolveDependencies(AbstractBuild<?, ?> build, BuildData buildData)
	{
		try {
			build.addAction(new DependentComponentResolver(dataLoader, buildData));
			logger.log(Logger.LABEL + Logger.SUCCESS + "dependent components have been sucessfully resolved");
		} catch (Exception e) {
			logger.logFailure(e, "RESOLVING DEPENDENT COMPONENTS FAILED");
		}
	}

	/**
	 * stores all data access objects to database
	 * 
	 * @param transport
	 */
	private void storeData(Transferable transport)
	{
		try {
			dataStorage.storeData(transport);
			logger.log(
					Logger.LABEL + Logger.SUCCESS + "data access objects have been successfully stored to database");
		} catch (Exception e) {
			logger.logFailure(e, "STORAGE FAILED");
		}
	}

	/**
	 * starts the analysis of the current build
	 * 
	 * @param build
	 * @param buildData
	 */
	private void analyseBuild(AbstractBuild<?, ?> build, BuildData buildData)
	{
		try {
			build.addAction(new IntegrationAnalyser(buildData, dataLoader));
			logger.log(Logger.LABEL + Logger.SUCCESS
					+ "analysis of all installed components of this build have been successful");
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
	 * @param build {@link AbstractBuild}
	 * @param l {@link BuildListener}
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
		data.setJobUrl(env.get(BuildEnvVars.JOB_URL.toString()));
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
	 * @param data {@link BuildData}
	 * @param env environment variables of {@link AbstractBuild}
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
		 * @param project {@link AbstractProject}
		 * @return {@link ListBoxModel}
		 */
		public ListBoxModel doFillLockPathItems(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
		{
			return ListItemProvider.fillPathItems(
				getWorkspace(project), 
				FileType.COMPOSER_LOCK
			);
		}

		/**
		 * is filling the selectItems of the dropdown list of the composer.json
		 * configuration
		 * 
		 * @param project {@link AbstractProject}
		 * @return {@link ListBoxModel}
		 */
		public ListBoxModel doFillJsonPathItems(@SuppressWarnings("rawtypes") @AncestorInPath AbstractProject project)
		{
			return ListItemProvider.fillPathItems(
				getWorkspace(project), 
				FileType.COMPOSER_JSON
			);
		}
		
		/**
		 * extracting workspace path of last build
		 * 
		 * @param project {@link AbstractProject}
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
				//TODO
			} catch (InterruptedException e) {
				//TODO
			}
			return root.toPath();
		}

		/**
		 * is validating the database path entered via the global configration
		 * 
		 * @param value entered database path
		 * @param project {@link AbstractProject}
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
		 * checks if a file is a sqlite database file by looking at it's suffix  *.db
		 * 
		 * @param file {@link File}
		 * @return
		 */
		private boolean isDbFile(File file)
		{
			if (file.getName().endsWith(".db")) return true;
			return false;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public boolean isApplicable(Class<? extends AbstractProject> aClass)
		{
			// Indicates that this builder can be used with all kinds of project types
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
