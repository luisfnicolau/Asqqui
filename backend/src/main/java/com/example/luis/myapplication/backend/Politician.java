package com.example.luis.myapplication.backend;

/**
 * Created by Luis-DELL on 4/25/2016.
 */
public class Politician {

    private long id;

    private String name;
    private String party;
    private byte[] photo;
    private String voteNumber;
    private String position;
    private String description;
    private String proposals;

    public Politician(long id) {
        this.id = id;
    }

    public Politician(long id, String name, String voteNumber, String position){
        this.id = id;
        this.name = name;
        this.voteNumber = voteNumber;
        this.position = position;
    }

    public Politician(long id, String name, String voteNumber,
                      String position, String party, byte[] photo, String description, String proposals){
        this.id = id;
        this.name = name;
        this.voteNumber = voteNumber;
        this.position = position;
        this.party = party;
        this.photo = photo;
        this.description = description;
        this.proposals = proposals;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(String voteNumber) {
        this.voteNumber = voteNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProposals() {
        return proposals;
    }

    public void setProposals(String proposals) {
        this.proposals = proposals;
    }
}
