package org.photoexp

import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.gridfs.GridFS
import com.mongodb.gridfs.GridFSInputFile
import org.imgscalr.Scalr
import org.photoexp.entity.data.Image
import org.photoexp.entity.user.DefaultUser
import org.springframework.web.multipart.commons.CommonsMultipartResolver

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DefaultUserController {
    def springSecurityService;
    def imageService;
    CommonsMultipartResolver commonsMultipartResolver;
    def scaffold = DefaultUser;
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DefaultUser.list(params), model: [defaultUserCount: DefaultUser.count()]
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
            respond defaultUser.errors, view: 'create'
            return
        }

        defaultUser.save flush: true
        if (!defaultUser.authorities.contains(userRole)) {
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

    def edit() {
        respond springSecurityService.currentUser
    }

    def addImage() {
        File photo;
        try {
            DefaultUser user = springSecurityService.currentUser;
            def file = request.getFile("file");

            photo = new File(file.getOriginalFilename());
            photo.createNewFile();
            FileOutputStream fos = new FileOutputStream(photo);
            fos.write(file.getBytes());
            fos.close();

            MongoClient client = new MongoClient();
            DB db = client.getDB("mongo");

            GridFS gfsPhoto = new GridFS(db, "photo");

            System.out.println(ImageIO.read(photo).getColorModel().hasAlpha());
            BufferedImage thumbnail = Scalr.resize(imageService.dropAlphaChannel(ImageIO.read(photo)), 50, 50)

            String gfsFileName = "${file.name}${Math.random() * 10000 * new Date().hashCode()}";
            GridFSInputFile gfsFile = gfsPhoto.createFile(photo);
            gfsFile.setFilename(gfsFileName);
            gfsFile.save();

            Image image = new Image(id: gfsFile.getId().toString(), name: gfsFileName, thumbnail: imageService.getBase64(thumbnail));
            image.setUser(user);

            if (image.validate()) {
                image.insert(failOnError: false);
            }

            user.addToImages(image);
            user.save flush: true
        } finally {
            photo?.delete();
        }
        return "Kinda saved";
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
            respond defaultUser.errors, view: 'edit'
            return
        }

        defaultUser.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), defaultUser.id])
                redirect defaultUser
            }
            '*' { respond defaultUser, [status: OK] }
        }
    }

    @Transactional
    def delete(DefaultUser defaultUser) {

        if (defaultUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        defaultUser.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), defaultUser.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'defaultUser.label', default: 'DefaultUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
