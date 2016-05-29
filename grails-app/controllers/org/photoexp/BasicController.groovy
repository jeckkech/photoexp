package org.photoexp

import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.gridfs.GridFS
import com.mongodb.gridfs.GridFSDBFile
import grails.transaction.Transactional
import org.photoexp.entity.data.Image

import javax.imageio.ImageIO

/**
 * Created by Max on 20.02.2016.
 */

@Transactional
class BasicController {
    def springSecurityService;
    def imageService;

    def index = {
        def user = springSecurityService.currentUser;


        MongoClient client = new MongoClient();
        DB db = client.getDB("mongo");

        GridFS gridFSPhoto = new GridFS(db, "photo");
        for(Image image : user.getImages()){
            System.out.println(image.name);
            GridFSDBFile imageFile = gridFSPhoto.findOne(image.name);
            System.out.println(ImageIO.read(imageFile.getInputStream()));
            System.out.println("________________________________________");
        }

        return [user: user];
    }



}
