package photoexp.file

import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.gridfs.GridFS
import com.mongodb.gridfs.GridFSDBFile
import org.photoexp.entity.user.DefaultUser
import photoexp.banks.DepositphotosService

/**
 * Created by Max on 29.05.2016.
 */
class FileService {
    static scope = "singleton"
    def grailsApplication

    private DepositphotosService getDPService(){
       return new DepositphotosService();
    }

    public void getImagesAndSubmit(DefaultUser user){
        MongoClient client = new MongoClient()
        DB db = client.getDB("mongo")
        Map<String, InputStream> streamList = new HashMap<>()

        user.getImages().each{

            if(!it.isUploaded()){
                System.out.println(it.getName())
                GridFS gfsPhoto = new GridFS(db, "photo")
                GridFSDBFile file = gfsPhoto.findOne(it.name)
                streamList.put(it.name, file.getInputStream())
            }
        }
        new DepositphotosService().uploadPictures(streamList)
    }
}
