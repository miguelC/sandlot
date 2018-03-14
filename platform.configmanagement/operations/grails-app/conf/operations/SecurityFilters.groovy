package operations

class SecurityFilters {

    def filters = {
       justADList(controller:'ADList', action:'*') {
			before = {
				if(!session.isUserPresent){
					redirect(uri: '/')
					return false
				}
			}
		}
		justClientInfo(controller:'clientInfo', action:'*') {
			before = {
				if(!session.isUserPresent){
					redirect(uri: '/')
					return false
				}
			}
		}
		justNavigationLinks(controller:'navigationLinks', action:'*') {
			before = {
				if(!session.isUserPresent){
					redirect(uri: '/')
					return false
				}
			}
		}
		justApplications(controller:'applications', action:'*') {
			before = {
				if(!session.isUserPresent){
					redirect(uri: '/')
					return false
				}
			}
		}
		justLicense(controller:'license', action:'*') {
			before = {
				if(!session.isUserPresent){
					redirect(uri: '/')
					return false
				}
			}
		}
    }
}
