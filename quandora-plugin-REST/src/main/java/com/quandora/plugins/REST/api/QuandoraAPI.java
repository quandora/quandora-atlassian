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

package com.quandora.plugins.REST.api;

import java.util.List;

import com.quandora.plugins.REST.model.Question;

/**
 * Provides the Quandora Services such as configured quandora domain name or
 * questions result from a search
 * 
 * @author Nicolas
 *
 */
public interface QuandoraAPI {

    /**
     * Provides a List of Questions in result of the String used to search.
     * 
     * @param search search string query
     * @return List of Questions
     * @throws Exception
     */
    public List<Question> getQuestions(String search) throws Exception;

    /**
     * Provides the Quandora domain configured
     * @return Quandora Domain set in the administration panel
     */
    public String getQuandoraDomain();
}
