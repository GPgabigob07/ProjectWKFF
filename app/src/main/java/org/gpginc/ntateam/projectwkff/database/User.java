package org.gpginc.ntateam.projectwkff.database;

import com.google.firebase.auth.FirebaseUser;

import static org.gpginc.ntateam.projectwkff.GameFlux.LOG;

public class User
{
    private String name, mail, uid;
    private boolean paidUser = false;

    public static User fromDB(FirebaseUser user)
    {
        User u = new User().setMail(user.getEmail()).setName(user.getDisplayName()).setUid(user.getUid());
        LOG.w("USER CREATION", u.toString());
        return u;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
