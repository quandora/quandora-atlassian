package com.quandora.plugins.REST;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.quandora.plugins.REST.Admin.ConfigResource.Config;
import com.quandora.plugins.clients.QuandoraResponse;
import com.quandora.plugins.clients.Request;
import com.quandora.plugins.clients.RestClient;

/**
 * A resource of message.
 */
@Path("/")
public class QuandoraRest {

    private final PluginSettingsFactory pluginSettingsFactory;

    public QuandoraRest(UserManager userManager, PluginSettingsFactory pluginSettingsFactory,
            TransactionTemplate transactionTemplate)
    {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getQuandoraInfos(@QueryParam("request") String request) throws Exception
    {

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