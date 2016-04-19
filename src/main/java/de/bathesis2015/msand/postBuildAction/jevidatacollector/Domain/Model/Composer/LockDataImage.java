package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.DependencyType;



/**
 * this is class is an image that holds the information 
 * of the composer.lock file
 *
 * @author Michael Sandritter
 * 21.09.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockDataImage{
	
	/**
	 * list of packages loaded via composer
	 */
	@JsonProperty("packages")
	private List<PackageDataImage> packages;

	public List<PackageDataImage> getPackages() {
		return packages;
	}
	
	/**
	 * filters replaced composer packages
	 * @param keySet
	 * @return list of {@link PackageDataImage}
	 */
	@Deprecated
	public Map<DependencyType, List<PackageDataImage>> filterRequirements(Set<String> keySet){
		List<PackageDataImage> direct = new ArrayList<PackageDataImage>();
		for (PackageDataImage pkg : packages){
			if(keySet.contains(pkg.getName())){
				direct.add(pkg);
			} else if (isReplaced(pkg, keySet)){
				direct.add(pkg);
			} 
		}
		Map<DependencyType, List<PackageDataImage>> ret = new HashMap<DependencyType, List<PackageDataImage>>();
		ret.put(DependencyType.DIRECT, direct);
		return ret;
	}
	
	@Deprecated
	private boolean isReplaced(PackageDataImage pkg, Set<String> keySet){
		Map<String, String> map = pkg.getReplacementMap();
		if (map != null) {
			for (String key : keySet){
				if (pkg.getReplacementMap().containsKey(key)){
					return true;
				}
			}
		}
		return false;
	}
}
