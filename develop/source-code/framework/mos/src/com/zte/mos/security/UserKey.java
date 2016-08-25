package com.zte.mos.security;

/**
 * Created by luoqingkai on 14-10-18.
 */
public class UserKey
{
    public final String user;
    public final String pwd;
    public final MosRole role;

    public UserKey(String user, String pwd, MosRole role)
    {
        this.user = user;
        this.pwd = pwd;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof UserKey)
        {
            UserKey other = (UserKey) obj;
            return user.equals(other.user)
                    && pwd.equals(other.pwd)
                    && role.equals(other.role);
        }
        return false;
    }
}
