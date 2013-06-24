package com.quandora.plugins.REST.model;

import java.util.Date;



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
        this.title = title;
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
        this.summary = summary;
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
}
