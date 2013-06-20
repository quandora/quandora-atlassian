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

package com.quandora.plugins.REST.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.quandora.plugins.REST.Admin.ConfigResource.Config;
import com.quandora.plugins.REST.client.QuandoraResponse;
import com.quandora.plugins.REST.client.Request;
import com.quandora.plugins.REST.client.RestClient;

/**
 * A resource of message.
 */
@Path("/")
public class QuandoraRest {

    private final UserManager userManager;
    private final PluginSettingsFactory pluginSettingsFactory;

    public QuandoraRest(UserManager userManager, PluginSettingsFactory pluginSettingsFactory,
            TransactionTemplate transactionTemplate)
    {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.userManager = userManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuandoraInfos(@QueryParam("request") String request, @Context HttpServletRequest httpRequest) throws Exception
    {
        String username = userManager.getRemoteUsername(httpRequest);
        if (username == null)
        {
            return Response.status(Status.UNAUTHORIZED).build();
        }

        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();

        Config config = new Config();
        config.setDomain((String) settings.get(Config.class.getName() + ".domain"));
        config.setLogin((String) settings.get(Config.class.getName() + ".login"));
        config.setPass((String) settings.get(Config.class.getName() + ".pass"));

        RestClient client = new RestClient(config);

        Request req = client.createRequest("https://"+config.getDomain()+"/"+request);
        QuandoraResponse JsonResponse = client.execute(req);

        return Response.ok(JsonResponse.result , MediaType.APPLICATION_JSON).build();
    }
}