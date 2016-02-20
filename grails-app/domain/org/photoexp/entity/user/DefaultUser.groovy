package org.photoexp.entity.user;

import org.photoexp.entity.data.Image;

import java.util.List;

public class DefaultUser extends BasicUser{
    static hasMany = [images: Image]; // should it be replaced to List<Image> images;?

    Float totalRevenue;
    Float availableRevenue;
    String paypalEmail;
    Boolean isWithdrawRequested;

    static mapping = {
        isWithdrawRequested: false
    }
}
