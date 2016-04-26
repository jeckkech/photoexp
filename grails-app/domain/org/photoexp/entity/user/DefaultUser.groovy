package org.photoexp.entity.user

import org.photoexp.SecUser;
import org.photoexp.entity.data.Image;

import java.util.List;

public class DefaultUser extends SecUser{
    Float totalRevenue;
    Float availableRevenue;
    String paypalEmail;
    Boolean isWithdrawRequested;

    DefaultUser(String username, String password) {
        super(username, password)
    }

    static mapping = {
        collection "users"
        database "mongo"
        isWithdrawRequested: false
    }

    static constraints = {
        password display: false
        totalRevenue nullable: true
        availableRevenue nullable: true
        paypalEmail nullable: true
        isWithdrawRequested nullable: true
    }

    static hasMany = [images: Image]; // should it be replaced to List<Image> images;?

}
