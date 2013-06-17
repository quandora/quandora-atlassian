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

package com.quandora.plugins.REST.clients;

import java.util.HashMap;
import java.util.Map;

public class QuandoraResponse {

    public int status;

    public String result;

    public Map<String,String> headers;

    public QuandoraResponse() {
        headers = new HashMap<String, String>();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }

}
