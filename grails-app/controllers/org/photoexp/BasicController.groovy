package org.photoexp

import grails.transaction.Transactional

/**
 * Created by Max on 20.02.2016.
 */

@Transactional
class BasicController {
    def springSecurityService;

    def index = {
        def user = springSecurityService.currentUser;

        return [user: user];
    }



}
