package com.quandora.plugins.REST.api;

import java.util.List;

import com.quandora.plugins.REST.model.Question;

public interface QuandoraAPI {

    public List<Question> getQuestions(String search) throws Exception;

    public String getQuandoraDomain();
}
