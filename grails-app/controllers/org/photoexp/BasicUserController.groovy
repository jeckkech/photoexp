package org.photoexp

import org.photoexp.entity.user.BasicUser

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BasicUserController {

    def scaffold = BasicUser;
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BasicUser.list(params), model:[basicUserCount: BasicUser.count()]
    }

    def show(BasicUser basicUser) {
        respond basicUser
    }

    def create() {
        respond new BasicUser(params)
    }

    @Transactional
    def save(BasicUser basicUser) {
        if (basicUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (basicUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond basicUser.errors, view:'create'
            return
        }

        basicUser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'basicUser.label', default: 'BasicUser'), basicUser.id])
                redirect basicUser
            }
            '*' { respond basicUser, [status: CREATED] }
        }
    }

    def edit(BasicUser basicUser) {
        respond basicUser
    }

    @Transactional
    def update(BasicUser basicUser) {
        if (basicUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (basicUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond basicUser.errors, view:'edit'
            return
        }

        basicUser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'basicUser.label', default: 'BasicUser'), basicUser.id])
                redirect basicUser
            }
            '*'{ respond basicUser, [status: OK] }
        }
    }

    @Transactional
    def delete(BasicUser basicUser) {

        if (basicUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        basicUser.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'basicUser.label', default: 'BasicUser'), basicUser.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'basicUser.label', default: 'BasicUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
