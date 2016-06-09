package photoexp.banks

import org.apache.commons.net.ftp.FTPClient
import org.photoexp.entity.data.Image
import org.photoexp.entity.user.DefaultUser

/**
 * Created by Max on 30.05.2016.
 */
class ShutterstockService implements GenericService{
    private static final String FTP_URL = "ftp.depositphotos.com";
    private static final String FTP_USERNAME = "fangorndt";
    private static final String FTP_PASSWORD = "dimon4ikdt2014";

    static final String FTP_URL_TEST = "localhost";


    @Override
    public void uploadPictures(Map<String, InputStream> images) {
        String status

        images.keySet().each {
            def image = images.get(it)
            def imageName = it
            def code = 0

            new FTPClient().with {
                connect "127.0.0.1"
                setDefaultPort(21)
                login FTP_USERNAME, FTP_PASSWORD
                enterLocalPassiveMode()
                setFileType(BINARY_FILE_TYPE)
//                    changeWorkingDirectory "/"
                storeFile (imageName+".jpg", image)
                status = replyString
                code = replyCode
                disconnect()
            }
            if(code == 226){
                Image currentImage = Image.findByName(imageName)
                currentImage.setUploaded(true)
                currentImage.save(flush:true)
            }

        }

    }
}
