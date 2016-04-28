package org.photoexp.entity.data

import org.photoexp.entity.user.DefaultUser

class Image {
    static belongsTo = [user: DefaultUser];

    File image;
    String thumbnail; //base64?
    String name;
    String description;
    String tags;

    static constraints = {
    }
}
