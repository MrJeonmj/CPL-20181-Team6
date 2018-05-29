package com.example.user.jolp_v0;

public class CurrentUserInfo
{
    private static String id;
    private static String name;

    public static String getId()
    {
        return id;
    }

    public static void setIdAndName(String id)
    {
        CurrentUserInfo.id = id;

        // TODO: get name from server
        CurrentUserInfo.name = "홍길동";
    }

    public static String getName()
    {
        return name;
    }
}
