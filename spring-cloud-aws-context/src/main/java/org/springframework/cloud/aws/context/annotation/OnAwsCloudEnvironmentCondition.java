/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.aws.context.annotation;

import com.amazonaws.AmazonClientException;
import com.amazonaws.util.EC2MetadataUtils;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Agim Emruli
 * @author Gleb Schukin
 */
public class OnAwsCloudEnvironmentCondition implements ConfigurationCondition{

	private static final String EC2_METADATA_ROOT = "/latest/meta-data";

	private static Boolean isCloudEnvironment;

	@Override
	public ConfigurationPhase getConfigurationPhase() {
		return ConfigurationPhase.PARSE_CONFIGURATION;
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (isCloudEnvironment == null) {
			try {
				isCloudEnvironment = EC2MetadataUtils.getData(EC2_METADATA_ROOT + "/instance-id", 1) != null;
			} catch (AmazonClientException e) {
				isCloudEnvironment = false;
			}
		}

		return isCloudEnvironment;
	}
}