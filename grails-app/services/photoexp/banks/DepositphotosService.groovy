package photoexp.banks;

import grails.core.GrailsApplication
import org.apache.commons.net.ftp.FTPClient;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Max on 25.05.2016.
 */
public class DepositphotosService implements GenericService {
    private static final String FTP_URL = "";
    private static final String FTP_USERNAME = "";
    private static final String FTP_PASSWORD = "";

    static final String FTP_URL_TEST = "localhost";


    @Override
    public void uploadPictures(Map<String, InputStream> images) {
        String status

            images.keySet().each {
                def image = images.get(it)
                def imageName = it
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
                System.out.println(status)
                System.out.println(imageName)
            }

    }
}
