package com.rjtech.rjs.persistence.postprocessors;

import com.rjtech.rjs.core.exception.RJSRuntimeException;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityScannerPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    @Autowired
    private ResourcePatternResolver resourceLoader;

    private List<String> entityPathPatterns;
    
    private List<String> excludeEntityPathPatterns;
    
    public void setExcludeEntityPathPatterns(List<String> excludeEntityPathPatterns) {
		this.excludeEntityPathPatterns = excludeEntityPathPatterns;
	}

	public void setEntityPathPatterns(List<String> entityPathPatterns) {
        this.entityPathPatterns = entityPathPatterns;
    }

    /**
     * {@inheritDoc}
     */
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
        try {
        	List<String> excludeResources = new ArrayList<String>();
        	if(CollectionUtils.isNotEmpty(excludeEntityPathPatterns)) {
        		for (String entityPathPattern : excludeEntityPathPatterns) {
        			Resource[] resources = resourceLoader.getResources(entityPathPattern);
        			Arrays.asList(resources).stream().map(obj ->excludeResources.add(obj.getFilename()));
        		}
        	}
        	
        	
            for (String entityPathPattern : entityPathPatterns) {
                Resource[] resources = resourceLoader.getResources(entityPathPattern);
                for (Resource resource : resources) {
                	if(!excludeResources.contains(resource.getFilename())) {
                		  CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
                          MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                          if (metadataReader.getAnnotationMetadata().isAnnotated(javax.persistence.Entity.class.getName())) {
                              mutablePersistenceUnitInfo
                                      .addManagedClassName(metadataReader.getClassMetadata().getClassName());
                          }
                	}
                }
            }
            mutablePersistenceUnitInfo.setExcludeUnlistedClasses(true);
        } catch (IOException e) {
            throw new RJSRuntimeException(e);
        }
    }
}
