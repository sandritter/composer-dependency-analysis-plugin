package de.sandritter.version_analysis_of_build_dependencies.DslConfiguration;

import de.sandritter.version_analysis_of_build_dependencies.BuildDependencyPublisher;
import hudson.Extension;
import javaposse.jobdsl.dsl.helpers.publisher.PublisherContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;
import javaposse.jobdsl.plugin.DslExtensionMethod;

@Extension(optional = true)
public class DSLExtension  extends ContextExtensionPoint {
	
    @DslExtensionMethod(context = PublisherContext.class)
    public Object buildDependencyAnalysis(Runnable closure) 
    {
    	DSLContext context = new DSLContext();
    	executeInContext(closure, context);
        return new BuildDependencyPublisher(
        	context.jsonPath, 
        	context.lockPath
        );
    }
}