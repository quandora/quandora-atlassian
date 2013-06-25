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

package com.quandora.plugins.REST.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Question Object in which is deserialized JSON
 * @author Nicolas Joseph
 *
 */
@JsonIgnoreProperties({"author","tags"})
public class Question {
    private static final long serialVersionUID = 1L;

    public static final String AUTHOR = "author";
    public static final String BASE_ID = "baseId";
    public static final String TITLE = "title";
    public static final String SUMMARY = "summary";
    public static final String CREATED = "created";
    public static final String VIEWS = "views";
    public static final String LAST_VIEW = "lastView";
    public static final String VOTES = "votes";
    public static final String ANSWERS = "answers";
    public static final String TAGS = "tags";


    protected String uid;

    protected String title;

    protected String summary;

    protected long votes;

    protected long views;

    protected long answers;

    protected Date created;

    protected String authorId;

    protected String baseId;

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }




    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }




    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }




    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title =  unescapeXML(title);
    }




    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }




    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = unescapeXML(summary);
    }




    /**
     * @return the votes
     */
    public long getVotes() {
        return votes;
    }




    /**
     * @param votes the votes to set
     */
    public void setVotes(long votes) {
        this.votes = votes;
    }




    /**
     * @return the views
     */
    public long getViews() {
        return views;
    }




    /**
     * @param views the views to set
     */
    public void setViews(long views) {
        this.views = views;
    }




    /**
     * @return the answers
     */
    public long getAnswers() {
        return answers;
    }




    /**
     * @param answers the answers to set
     */
    public void setAnswers(long answers) {
        this.answers = answers;
    }




    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }




    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }




    /**
     * @return the authorId
     */
    public String getAuthorId() {
        return authorId;
    }




    /**
     * @param authorId the authorId to set
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }




    /**
     * @return the baseId
     */
    public String getBaseId() {
        return baseId;
    }




    /**
     * @param baseId the baseId to set
     */
    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }




    @Override
    public String toString() {
        return title;
    }

    private static String unescapeXML( final String xml )
    {
        Pattern xmlEntityRegex = Pattern.compile( "&(#?)([^;]+);" );
        //Unfortunately, Matcher requires a StringBuffer instead of a StringBuilder
        StringBuffer unescapedOutput = new StringBuffer( xml.length() );

        Matcher m = xmlEntityRegex.matcher( xml );
        Map<String,String> builtinEntities = null;
        String entity;
        String hashmark;
        String ent;
        int code;
        while ( m.find() ) {
            ent = m.group(2);
            hashmark = m.group(1);
            if ( (hashmark != null) && (hashmark.length() > 0) ) {
                code = Integer.parseInt( ent );
                entity = Character.toString( (char) code );
            } else {
                //must be a non-numerical entity
                if ( builtinEntities == null ) {
                    builtinEntities = buildBuiltinXMLEntityMap();
                }
                entity = builtinEntities.get( ent );
                if ( entity == null ) {
                    //not a known entity - ignore it
                    entity = "&" + ent + ';';
                }
            }
            m.appendReplacement( unescapedOutput, entity );
        }
        m.appendTail( unescapedOutput );

        return unescapedOutput.toString();
    }

    private static Map<String,String> buildBuiltinXMLEntityMap()
    {
        Map<String,String> entities = new HashMap<String,String>(10);
        entities.put( "lt", "<" );
        entities.put( "gt", ">" );
        entities.put( "amp", "&" );
        entities.put( "apos", "'" );
        entities.put( "quot", "\"" );
        return entities;
    }
}
