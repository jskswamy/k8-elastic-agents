/*
 * Copyright 2016 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.elasticagent.executors;

import com.example.elasticagent.AgentInstances;
import com.example.elasticagent.ExampleInstance;
import com.example.elasticagent.PluginRequest;
import com.example.elasticagent.RequestExecutor;
import com.example.elasticagent.requests.ShouldAssignWorkRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.stripToEmpty;

public class ShouldAssignWorkRequestExecutor implements RequestExecutor {
    private final AgentInstances<ExampleInstance> agentInstances;
    private final PluginRequest pluginRequest;
    private final ShouldAssignWorkRequest request;

    public ShouldAssignWorkRequestExecutor(ShouldAssignWorkRequest request, AgentInstances<ExampleInstance> agentInstances, PluginRequest pluginRequest) {
        this.request = request;
        this.agentInstances = agentInstances;
        this.pluginRequest = pluginRequest;
    }

    @Override
    public GoPluginApiResponse execute() {
        ExampleInstance instance = agentInstances.find(request.agent().elasticAgentId());

        if (instance == null) {
            return DefaultGoPluginApiResponse.success("false");
        }

        boolean environmentMatches = stripToEmpty(request.environment()).equalsIgnoreCase(stripToEmpty(instance.environment()));

        Map<String, String> containerProperties = instance.properties() == null ? new HashMap<String, String>() : instance.properties();
        Map<String, String> requestProperties = request.properties() == null ? new HashMap<String, String>() : request.properties();

        boolean propertiesMatch = requestProperties.equals(containerProperties);

        if (environmentMatches && propertiesMatch) {
            return DefaultGoPluginApiResponse.success("true");
        }

        return DefaultGoPluginApiResponse.success("false");
    }
}
