package org.springframework.boot.sutdy.extend_life_cycle.springFactories;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * MyEnableAutoConfigurationImport
 *
 * @author nicky
 * @date 2021/7/8 上午10:19
 */
public class MyEnableAutoConfigurationImportSelector implements DeferredImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(MyEnableAutoConfiguration.class,
				this.getClass().getClassLoader());
		return configurations.toArray(new String[configurations.size()]);
	}
}
