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

import java.util.HashMap;
import java.util.Map;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.sal.api.ApplicationProperties;
import com.quandora.plugins.REST.api.QuandoraAPI;


@SuppressWarnings("unchecked")
public class RelatedQuestionIndicatorImpl extends AbstractJiraContextProvider
{
    private final ApplicationProperties applicationProperties;
    private final QuandoraAPI QA;

    public RelatedQuestionIndicatorImpl(ApplicationProperties applicationProperties, QuandoraAPI QA)
    {
        this.applicationProperties = applicationProperties;
        this.QA = QA;
    }

    @Override
    public Map getContextMap(User user, JiraHelper jiraHelper) {

        Map contextMap = new HashMap();
        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue");
        String queryText = currentIssue.getSummary() + " " + currentIssue.getKey();
        if (currentIssue.getDescription() != null){
            queryText += " "+currentIssue.getDescription();
        }
        queryText=queryText.trim();

        try {
            contextMap.put("questionList", QA.getQuestions(queryText));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return contextMap;
    }


}