package com.example.tapisirisi.logic.model;

import java.util.List;

public class UserMotif {
    private long id;
    private String fileDownloadUri;

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    private List<Motif> motifs;

    private User user;

    public List<Motif> getMotifs() {
        return motifs;
    }

    public void setMotifs(List<Motif> motifs) {
        this.motifs = motifs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
