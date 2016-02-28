import org.photoexp.SecRole
import org.photoexp.SecUser
import org.photoexp.SecUserSecRole

class BootStrap {

    def init = { servletContext ->
        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)


        def adminUser = SecUser.findByUsername("admin") ?: new SecUser(username: 'admin', password: 'admin', enabled: true).save(failOnError: true);
        def basicUser = SecUser.findByUsername("user") ?: new SecUser(username: 'user', password: 'user', enabled: true).save(failOnError: true);

        if(!adminUser.authorities.contains(adminRole)){
            SecUserSecRole.create(adminUser, adminRole);
        }
        if(!basicUser.authorities.contains(userRole)){
            SecUserSecRole.create(basicUser, userRole);
        }
    }
    def destroy = {
    }
}
