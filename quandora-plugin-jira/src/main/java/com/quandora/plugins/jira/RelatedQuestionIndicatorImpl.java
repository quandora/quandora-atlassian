/*
Copyright 2013 Nicolas Joseph

Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.quandora.plugins.jira;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.sal.api.ApplicationProperties;
import com.quandora.plugins.client.QuandoraResponse;
import com.quandora.plugins.client.Request;
import com.quandora.plugins.client.RestClient;


@SuppressWarnings("unchecked")
public class RelatedQuestionIndicatorImpl extends AbstractJiraContextProvider
{
    private final ApplicationProperties applicationProperties;

    public RelatedQuestionIndicatorImpl(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public Map getContextMap(User user, JiraHelper jiraHelper) {

        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue");
        String queryText = currentIssue.getDescription() + " " + currentIssue.getSummary() + " " + currentIssue.getKey();

        RestClient client = new RestClient();
        String baseURL = ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL);

        Request req = client.createRequest(baseURL+"/rest/quandora-rest/1.0/?request=/m/json/search?q="+queryText+"&l=5");
        System.out.println(req.getUrl());

        try {
            return getQuestionData(req,client);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private Map getQuestionData(Request req, RestClient client) throws Exception{
        QuandoraResponse JsonResponse = client.execute(req);
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> questionData = mapper.readValue(JsonResponse.result, Map.class);

        questionData = mapper.readValue(questionData.get("data").toString(), Map.class);

        questionData = mapper.readValue(questionData.get("result").toString(), Map.class);

        return questionData;
    }
}