package org.photoexp

import org.photoexp.entity.user.BasicUser
import org.photoexp.entity.user.DefaultUser

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DefaultUserController {

    def scaffold = DefaultUser;
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DefaultUser.list(params), model:[defaultUserCount: DefaultUser.count()]
    }

    def show(DefaultUser defaultUser) {
        respond defaultUser
    }

    def create() {
        respond new DefaultUser(params)
    }

    @Transactional
    def save(DefaultUser defaultUser) {
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        if (defaultUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (defaultUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond defaultUser.errors, view:'create'
            return
        }

        defaultUser.save flush:true
        if(!defaultUser.authorities.contains(userRole)){
            SecUserSecRole.create(defaultUser, userRole);
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), defaultUser.id])
                redirect defaultUser
            }
            '*' { respond defaultUser, [status: CREATED] }
        }
    }

    def edit(DefaultUser defaultUser) {
        respond defaultUser
    }

    @Transactional
    def update(DefaultUser defaultUser) {
        if (defaultUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (defaultUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond defaultUser.errors, view:'edit'
            return
        }

        defaultUser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), defaultUser.id])
                redirect defaultUser
            }
            '*'{ respond defaultUser, [status: OK] }
        }
    }

    @Transactional
    def delete(DefaultUser defaultUser) {

        if (defaultUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        defaultUser.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), defaultUser.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
