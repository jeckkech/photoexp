package org.photoexp

import grails.transaction.Transactional
import org.apache.catalina.User
import org.photoexp.entity.user.BasicUser
import org.photoexp.entity.user.DefaultUser

/**
 * Created by Max on 20.02.2016.
 */

@Transactional
class BasicController {
    def springSecurityService;
//    static scaffold = BasicUser;

    def index = {
        def user = springSecurityService.currentUser;

        return [user: user];
    }
}
