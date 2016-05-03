class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "basic")
        "/edit"(controller: "defaultUser", action:'edit')
        "/register"(controller: "defaultUser", action:'create')
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/login/$action?"(controller: "login")
        "/login/$action?"(controller: "logout")
    }
}
