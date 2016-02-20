package org.photoexp.entity.user

class BasicUser {
    String name;
    String email;
    String password;

    static constraints = {
        name(blank: false)
        email(blank: false)
        password(blank: false)
    }

    static mapping = {
        collection "users"
        database "mongo"
    }
}
