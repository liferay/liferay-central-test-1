##
## Properties Override
##

    #
    # Specify where to get the overridden properties. Updates should not be made
    # on this file but on the overridden version of this file.
    #
    include-and-override=portlet-service-ext.properties

##
## Build
##

    build.namespace=${portletShortName}
    build.number=${buildNumber}
    build.date=${currentTimeMillis?c}

##
## Spring
##

    #
    # Input a list of comma delimited Spring configurations. These will be
    # loaded after the bean definitions specified in the contextConfigLocation
    # parameter in web.xml.  By default, spring.configs is empty but exists
    # to be backwards compatible with previous versions of Liferay.
    #
    spring.configs=

    #
    # Set the bean name for the Liferay data source.
    #
    spring.hibernate.data.source=liferayDataSource

    #
    # Set the bean name for the Liferay session factory.
    #
    spring.hibernate.session.factory=&liferaySessionFactory

##
## Hibernate
##

    #
    # Input a list of comma delimited Hibernate configurations.
    #
    hibernate.configs=\
        META-INF/portlet-hbm.xml,\
        META-INF/ext-hbm.xml

    #
    # Use the Liferay SQL dialect because it will automatically detect the proper
    # SQL dialect based on your connection URL.
    #
    hibernate.dialect=${springHibernatePackage}.DynamicDialect

    #
    # Set the Hibernate connection release mode. You should not modify this
    # unless you know what you're doing. The default setting works best for
    # Spring managed transactions. See the method buildSessionFactory in class
    # org.springframework.orm.hibernate3.LocalSessionFactoryBean and search for
    # the phrase "on_close" to understand how this works.
    #
    #hibernate.connection.release_mode=on_close

    #
    # Set the Hibernate cache provider. Ehcache is recommended in a clustered
    # environment. See the property "net.sf.ehcache.configurationResourceName"
    # for detailed configuration.
    #
    hibernate.cache.provider_class=com.liferay.util.dao.hibernate.EhCacheProvider
    #hibernate.cache.provider_class=net.sf.hibernate.cache.HashtableCacheProvider

    #
    # This property is used if Hibernate is configured to use Ehcache's
    # cache provider.
    #
    net.sf.ehcache.configurationResourceName=/ehcache/hibernate.xml

    #
    # Set other Hibernate cache settings.
    #
    hibernate.cache.use_query_cache=true
    hibernate.cache.use_second_level_cache=true
    hibernate.cache.use_minimal_puts=true
    hibernate.cache.use_structured_entries=false

    #
    # Uncomment these properties to disable Hibernate caching.
    #
    #hibernate.cache.provider_class=org.hibernate.cache.NoCacheProvider
    #hibernate.cache.use_query_cache=false
    #hibernate.cache.use_second_level_cache=false

    #
    # Set the JDBC batch size to improve performance. However, if you're using
    # Oracle 9i, you must set the batch size to 0 as a workaround for a hanging
    # bug in the Oracle driver. See http://support.liferay.com/browse/LEP-1234
    # for more information.
    #
    hibernate.jdbc.batch_size=20
    #hibernate.jdbc.batch_size=0

    #
    # Set other miscellaneous Hibernate properties.
    #
    hibernate.jdbc.use_scrollable_resultset=true
    hibernate.bytecode.use_reflection_optimizer=true
    hibernate.show_sql=false

    #
    # Use the classic query factory until WebLogic and Hibernate 3 can get
    # along. See http://www.hibernate.org/250.html#A23 for more information.
    #
    hibernate.query.factory_class=org.hibernate.hql.classic.ClassicQueryTranslatorFactory

##
## Custom SQL
##

    #
    # Input a list of comma delimited custom SQL configurations.
    #
    custom.sql.configs=custom-sql/default.xml

    #
    # Some databases do not recognize a NULL IS NULL check. Set the
    # "custom.sql.function.isnull" and "custom.sql.function.isnotnull"
    # properties for your specific database.
    #
    # There is no need to manually set these properties because
    # com.liferay.portal.spring.hibernate.DynamicDialect already sets it.
    # However, these properties are set so that you can see how you can override
    # it for a database that DynamicDialect does not yet know how to auto
    # configure.
    #

    #
    # DB2
    #
    #custom.sql.function.isnull=CAST(? AS VARCHAR(32672)) IS NULL
    #custom.sql.function.isnotnull=CAST(? AS VARCHAR(32672)) IS NOT NULL

    #
    # MySQL (for testing only)
    #
    #custom.sql.function.isnull=IFNULL(?, '1') = '1'
    #custom.sql.function.isnotnull=IFNULL(?, '1') = '0'

    #
    # Sybase
    #
    #custom.sql.function.isnull=ISNULL(?, '1') = '1'
    #custom.sql.function.isnotnull=ISNULL(?, '1') = '0'

##
## Value Object
##

    value.object.finder.cache.enabled=true