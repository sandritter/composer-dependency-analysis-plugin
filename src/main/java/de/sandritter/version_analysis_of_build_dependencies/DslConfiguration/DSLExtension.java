package de.sandritter.version_analysis_of_build_dependencies.DslConfiguration;

import de.sandritter.version_analysis_of_build_dependencies.BuildDependencyPublisher;
import javaposse.jobdsl.dsl.helpers.publisher.PublisherContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;
import javaposse.jobdsl.plugin.DslExtensionMethod;

public class DSLExtension  extends ContextExtensionPoint {
    @DslExtensionMethod(context = PublisherContext.class)
    public Object example(String dependencyReflectionFilePath, String composerJsonPath) {
        return new BuildDependencyPublisher(dependencyReflectionFilePath, composerJsonPath);
    }
}
