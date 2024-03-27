package com.servizi.servizifileupload.dto;

import com.servizi.servizifileupload.entities.Studente;

public class DownloadProfilePictureDTO {

    private Studente studente;
    private byte[] profileImage;


    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
