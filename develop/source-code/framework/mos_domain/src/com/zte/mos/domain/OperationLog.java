package com.zte.mos.domain;


import com.zte.mos.exception.MOSException;

public class OperationLog {
    public final String type;
    public final String dn;
    public final String moClass;
    public final String mo;

    public OperationLog(String type, String dn, String moClass, String mo) {
        this.type = type;
        this.dn = dn;
        this.moClass = moClass;
        this.mo = mo;
    }

    public ManagementObject toMo(ModelHead header) throws MOSException{
        if (mo != null && moClass != null)
        {
            ManagementObject resultMo = header.buildMo(this.moClass);
            resultMo.setDn(dn);
            return resultMo;
        }
        return null;
    }
    public String toString(){
        return type + "," + moClass + ", dn=" + dn + ", mo=" + mo;
    }


}
