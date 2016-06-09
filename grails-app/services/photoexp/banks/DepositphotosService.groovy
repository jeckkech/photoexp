package photoexp.banks

import com.mongodb.DB
import com.mongodb.gridfs.GridFS;
import grails.core.GrailsApplication
import org.apache.commons.net.ftp.FTPClient
import org.grails.datastore.mapping.mongo.config.MongoCollection
import org.photoexp.entity.data.Image
import org.photoexp.entity.user.DefaultUser;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Max on 25.05.2016.
 */
public class DepositphotosService implements GenericService {
    private static final String FTP_URL = "ftp.depositphotos.com";
    private static final String FTP_USERNAME = "fangorndt";
    private static final String FTP_PASSWORD = "dimon4ikdt2014";

    static final String FTP_URL_TEST = "localhost";


    @Override
    public void uploadPictures(Map<String, InputStream> images, DefaultUser user) {
        String status

            images.keySet().each {
                def image = images.get(it)
                def imageName = it
                def success = false

                new FTPClient().with {
                   connect "127.0.0.1"
                    setDefaultPort(21)
                    login FTP_USERNAME, FTP_PASSWORD
                    enterLocalPassiveMode()
                    setFileType(BINARY_FILE_TYPE)
//                    changeWorkingDirectory "/"
                    storeFile (imageName+".jpg", image)
                    status = replyString
                    disconnect()
                }

                Image currentImage = Image.findByName(imageName)
                currentImage.setUploaded(true)
                currentImage.save(flush:true)

            }

    }
}
