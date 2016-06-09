shuttershit{}




// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.photoexp.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.photoexp.SecUserSecRole'
grails.plugin.springsecurity.authority.className = 'org.photoexp.SecRole'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/basic/index',          access: ['ROLE_USER']],
	[pattern: '/basic/index.gsp',          access: ['ROLE_USER']],
	[pattern: '/basicUser/index',          access: ['permitAll']],
	[pattern: '/defaultUser/index',          access: ['permitAll']],
	[pattern: '/defaultUser/create',          access: ['permitAll']],
	[pattern: '/defaultUser/save',          access: ['permitAll']],
	[pattern: '/defaultUser/update/**',          access: ['permitAll']],
	[pattern: '/defaultUser/**',          access: ['permitAll']],
	[pattern: '/image/**',          access: ['permitAll']],
	[pattern: '/defaultUser/show/**',          access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]
