/*
Copyright 2013 Quandora Corp
    Contributors :  Nicolas Joseph

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import com.quandora.plugins.REST.client.QuandoraClient;
import com.quandora.plugins.REST.client.QuandoraResponse;
import com.quandora.plugins.REST.client.Request;

/**
 * Acces to Quandora REST API. This is a REST proxy
 * It is not yet used by the plugin
 * 
 * @author Nicolas Joseph
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
        System.out.println(httpRequest.getHeader("innerToken"));
        if (username == null)
        {
            return Response.status(Status.UNAUTHORIZED).build();
        }

        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();

        Config config = new Config();
        config.setDomain((String) settings.get(Config.class.getName() + ".domain"));
        config.setLogin((String) settings.get(Config.class.getName() + ".login"));
        config.setPass((String) settings.get(Config.class.getName() + ".pass"));

        QuandoraClient client = new QuandoraClient(config);

        Request req = client.createRequest("https://"+config.getDomain()+"/"+encodeRequest(request));
        QuandoraResponse JsonResponse = client.execute(req);

        return Response.ok(JsonResponse.result , MediaType.APPLICATION_JSON).build();
    }

    /**
     * Encode URL request
     * @param request
     * @return encoded String
     * @throws UnsupportedEncodingException
     */
    private String encodeRequest(String request) throws UnsupportedEncodingException{
        String encoded = "";
        int temp = 0;

        while(!request.equalsIgnoreCase("")){
            temp = request.indexOf("/");
            if (temp != -1){
                encoded += URLEncoder.encode(request.substring(0, temp),"UTF-8")+"/";
                request = request.substring(temp+1);
            }
            else{
                temp = request.indexOf("=");
                encoded += request.substring(0,temp+1);
                request = request.substring(temp);

                temp = request.indexOf("%26");
                if (temp!=-1){
                    encoded += URLEncoder.encode(request.substring(0,temp), "UTF-8")+"&";
                    request = request.substring(temp+3);
                }
                else{
                    encoded += URLEncoder.encode(request, "UTF-8");
                    request = "";
                }
            }
        }

        return encoded;
    }
}