using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate;
using NHibernate.Mapping.ByCode;
using NHibernate.Tool.hbm2ddl;
using NHibernate.Cfg;
using Extended_Movie.Mappings;

namespace Extended_Movie.App_Start
{
    public static class SessionFactoryConfig
    {
         public static ISessionFactory CreateSessionFactory()
        {
            var configuration = new Configuration();
            configuration.Configure();
            var mapper = new ModelMapper();
            mapper.AddMapping<FileMapping>();
            mapper.AddMapping<CinemaMapping>();
            mapper.AddMapping<LanguageMapping>();
            mapper.AddMapping<CompanyMapping>();
            configuration.AddDeserializedMapping(mapper.CompileMappingForAllExplicitlyAddedEntities(), null);
            var factory = configuration.BuildSessionFactory();
            new SchemaUpdate(configuration).Execute(false, true);
            return factory;
        }
    }
}