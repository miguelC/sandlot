class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'UserLogin', action:'goToLoginPage')
        "500"(view:'/error')
	}
}
