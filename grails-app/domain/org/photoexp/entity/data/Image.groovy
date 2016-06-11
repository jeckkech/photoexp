package org.photoexp.entity.data

import org.photoexp.entity.user.DefaultUser

class Image {
    static belongsTo = [user: DefaultUser];

    String id;
    String thumbnail; //base64?
    String name;
    String shutterstockId;
    boolean uploaded;
    boolean uploadedShutterstock;


    static constraints = {
        id unique: true
        uploaded nullable: true
    }

    static mapping = {
        collection "images"
        database "mongo"
        uploaded defaultValue: false
    }
}
