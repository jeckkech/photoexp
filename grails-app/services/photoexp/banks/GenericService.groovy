package photoexp.banks

import grails.core.GrailsApplication
import org.photoexp.entity.user.DefaultUser;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Max on 25.05.2016.
 */
public interface GenericService {
    GrailsApplication grailsApplication;

    public void uploadPictures(Map<String, InputStream> images);
}
