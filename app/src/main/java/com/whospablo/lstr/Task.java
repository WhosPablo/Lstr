package com.whospablo.lstr;

/**
 * Created by pablo_arango on 10/11/16.
 */

class Task {
    private long id;
    private String title;
    private String summary;
    private boolean finished;

    Task(long id, String title, String summary, boolean finished) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.finished = finished;
    }

    long getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getSummary() {
        return summary;
    }

    void setSummary(String summary) {
        this.summary = summary;
    }

    Boolean getFinished() {
        return finished;
    }

    void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
