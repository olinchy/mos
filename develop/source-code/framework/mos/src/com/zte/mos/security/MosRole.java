package com.zte.mos.security;

/**
 * Created by luoqingkai on 14-10-16.
 */
public enum MosRole
{
    EMS("EMS"), NE("NE"), CLI("CLI");
    private final String role;

    MosRole(String role)
    {
        this.role = role;
    }

    public String toString()
    {
        return role;
    }
//
//    public static MosRole valueOf(String role) throws Exception{
//        for (MosRole r : MosRole.values()){
//            if (r.role.equals(role)){
//                return r;
//            }
//        }
//        throw new Exception("");
//    }
}
