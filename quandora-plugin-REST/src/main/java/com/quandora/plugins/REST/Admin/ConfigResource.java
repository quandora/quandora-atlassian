package com.quandora.plugins.REST.Admin;
 
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
 
@Path("/")
public class ConfigResource
{
  private final UserManager userManager;
  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;
 
  public ConfigResource(UserManager userManager, PluginSettingsFactory pluginSettingsFactory, 
                  TransactionTemplate transactionTemplate)
  {
    this.userManager = userManager;
    this.pluginSettingsFactory = pluginSettingsFactory;
    this.transactionTemplate = transactionTemplate;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@Context HttpServletRequest request)
  {
    String username = userManager.getRemoteUsername(request);
    if (username == null || !userManager.isSystemAdmin(username))
    {
      return Response.status(Status.UNAUTHORIZED).build();
    }
   
    return Response.ok(transactionTemplate.execute(new TransactionCallback()
    {
      public Object doInTransaction()
      {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        Config config = new Config();
        config.setDomain((String) settings.get(Config.class.getName() + ".domain"));
        config.setLogin((String) settings.get(Config.class.getName() + ".login"));
        config.setPass((String) settings.get(Config.class.getName() + ".pass"));

        return config;
      }
    })).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(final Config config, @Context HttpServletRequest request)
  {
    String username = userManager.getRemoteUsername(request);
    if (username == null || !userManager.isSystemAdmin(username))
    {
      return Response.status(Status.UNAUTHORIZED).build();
    }
   
    transactionTemplate.execute(new TransactionCallback()
    {
      public Object doInTransaction()
      {
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        pluginSettings.put(Config.class.getName() + ".domain", config.getDomain());
        pluginSettings.put(Config.class.getName() + ".login", config.getLogin());
        pluginSettings.put(Config.class.getName() + ".pass", config.getPass());
        return null;
      }
    });
    return Response.noContent().build();
  }

  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static final class Config
  {
    @XmlElement private String domain;
    @XmlElement private String login;
    @XmlElement private String pass;
           
    public String getDomain()
    {
      return domain;
    }
           
    public void setDomain(String domain)
    {
      this.domain = domain;
    }
           
    public String getLogin()
    {
      return login;
    }
           
    public void setLogin(String login)
    {
      this.login = login;
    } 

    public String getPass()
    {
      return pass;
    }
           
    public void setPass(String pass)
    {
      this.pass = pass;
    }
  }
}