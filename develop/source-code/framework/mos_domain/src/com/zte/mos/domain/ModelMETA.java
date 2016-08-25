package com.zte.mos.domain;

public class ModelMETA {

    public final ModelHead modelHead;

    public final IMosHead mosHead;

    public ModelMETA(ModelHead modelHead, IMosHead mosHead) {
        this.modelHead = modelHead;
        this.mosHead = mosHead;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ModelMETA){
            ModelMETA meta = (ModelMETA) obj;
            return modelHead.equals(meta.modelHead);
        }else{
            return false;
        }
    }
}
