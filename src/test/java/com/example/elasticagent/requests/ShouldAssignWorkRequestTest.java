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

package com.example.elasticagent.requests;

import com.example.elasticagent.Agent;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ShouldAssignWorkRequestTest {

    @Test
    public void shouldDeserializeFromJSON() throws Exception {
        String json = "{\n" +
                "  \"environment\": \"prod\",\n" +
                "  \"agent\": {\n" +
                "    \"agent_id\": \"42\",\n" +
                "    \"agent_state\": \"Idle\",\n" +
                "    \"build_state\": \"Idle\",\n" +
                "    \"config_state\": \"Enabled\"\n" +
                "  },\n" +
                "  \"properties\": {\n" +
                "    \"property_name\": \"property_value\"\n" +
                "  }\n" +
                "}";

        ShouldAssignWorkRequest request = ShouldAssignWorkRequest.fromJSON(json);
        assertThat(request.environment(), equalTo("prod"));
        assertThat(request.agent(), equalTo(new Agent("42", Agent.AgentState.Idle, Agent.BuildState.Idle, Agent.ConfigState.Enabled)));
        HashMap<String, String> expectedProperties = new HashMap<>();
        expectedProperties.put("property_name", "property_value");
        assertThat(request.properties(), Matchers.<Map<String, String>>equalTo(expectedProperties));
    }
}
