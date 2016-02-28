package org.photoexp

import grails.transaction.Transactional
import org.apache.catalina.User
import org.photoexp.entity.user.BasicUser

/**
 * Created by Max on 20.02.2016.
 */

@Transactional
class BasicController {

//    static scaffold = BasicUser;

    def index = {
        def user = BasicUser.list([sort:"name", order:"asc"]);

        return [user: user];
    }
}
