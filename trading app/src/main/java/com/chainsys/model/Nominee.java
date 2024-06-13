package com.chainsys.model;

public class Nominee {
    private int nomineeId;
    private String nomineeName;
    private String relationship;
    private String phoneno;
    private int userId;


	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	// Constructors
    public Nominee() {
    }

    public Nominee(int nomineeId, String nomineeName, String relationship, int userId) {
        this.nomineeId = nomineeId;
        this.nomineeName = nomineeName;
        this.relationship = relationship;
        this.userId = userId;
    }

    // Getters and Setters
    public int getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(int nomineeId) {
        this.nomineeId = nomineeId;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // toString() method
    @Override
    public String toString() {
        return "Nominee{" +
                "nomineeId=" + nomineeId +
                ", nomineeName='" + nomineeName + '\'' +
                ", relationship='" + relationship + '\'' +
                ", userId=" + userId +
                '}';
    }
}
