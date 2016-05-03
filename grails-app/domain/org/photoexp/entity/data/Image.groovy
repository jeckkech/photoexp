package org.photoexp.entity.data

import org.photoexp.entity.user.DefaultUser

class Image {
    static belongsTo = [user: DefaultUser];

    String id;
    String thumbnail; //base64?
    String name;

    static constraints = {
        id unique: true
    }

    static mapping = {
        collection "images"
        database "mongo"
    }
}
