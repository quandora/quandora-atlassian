package com.quandora.plugins.REST.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.quandora.plugins.REST.Admin.ConfigResource.Config;
import com.quandora.plugins.REST.client.QuandoraClient;
import com.quandora.plugins.REST.client.QuandoraResponse;
import com.quandora.plugins.REST.client.Request;
import com.quandora.plugins.REST.model.Question;

public class QuandoraService implements QuandoraAPI{

    private final QuandoraClient client;
    private final Config config;

    public QuandoraService(PluginSettingsFactory pluginSettingsFactory){
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();

        this.config = new Config();
        this.config.setDomain((String) settings.get(Config.class.getName() + ".domain"));
        this.config.setLogin((String) settings.get(Config.class.getName() + ".login"));
        this.config.setPass((String) settings.get(Config.class.getName() + ".pass"));

        this.client = new QuandoraClient(config);
    }

    @Override
    public List<Question> getQuestions(String search) throws Exception {
        Request req = client.createRequest("https://"+config.getDomain()+"/m/json/search?q="+URLEncoder.encode(search, "UTF-8")+"&l=5");

        return ParseJSONQuestionResponse(client.execute(req));
    }

    private List<Question> ParseJSONQuestionResponse(QuandoraResponse r) throws JsonProcessingException, IOException{

        List<Question> questionList = new ArrayList<Question>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(r.result);
        String questionJSONList = rootNode.path("data").path("result").getTextValue();

        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createJsonParser(questionJSONList);
        // advance stream to START_ARRAY first:
        jp.nextToken();
        // and then each time, advance to opening START_OBJECT
        while (jp.nextToken() == JsonToken.START_OBJECT) {
            Question foobar = mapper.readValue(jp, Question.class);
            // process
            questionList.add(foobar);
        }
        return questionList;
    }

}
