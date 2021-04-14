package com.example.mymiwokapp;

import androidx.annotation.NonNull;

public class Word {
    private String mDefaultTranslation;
    private String msanskritTranslation;
    private int getaudioResourceid;
    private int sresourceid=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;

    public Word(String defaultTranslation, String sanskritTranslation, int getAudioResourceid) {
        mDefaultTranslation = defaultTranslation;
        msanskritTranslation = sanskritTranslation;
        getaudioResourceid=getAudioResourceid;

    }
    public Word(String defaultTranslation, String sanskritTranslation, int getsResourceId, int getAudioResouceId)
    {
        mDefaultTranslation=defaultTranslation;
        msanskritTranslation=sanskritTranslation;
        sresourceid=getsResourceId;
        getaudioResourceid=getAudioResouceId;
    }

    public String getmDefaultTranslation(){
        return(mDefaultTranslation);
    }

    public String getmSanskritTranslation()
    {
        return(msanskritTranslation);
    }

    public int getmResourceid() { return sresourceid; }

    public boolean hasImage()
    {
        return sresourceid!= NO_IMAGE_PROVIDED;
    }

    public int getaudioResourceid() {
        return getaudioResourceid;
    }
}
