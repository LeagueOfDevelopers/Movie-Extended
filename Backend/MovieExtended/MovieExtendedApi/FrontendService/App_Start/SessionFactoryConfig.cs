using Domain.Mappings;
using Extended_Movie.Mappings;
using NHibernate;
using NHibernate.Mapping.ByCode;
using NHibernate.Tool.hbm2ddl;
using NHibernate.Cfg;

namespace FrontendService.App_Start
{
    public static class SessionFactoryConfig
    {
        public static ISessionFactory CreateSessionFactory()
        {
            var configuration = new Configuration();
            configuration.Configure();
            var modelMapper = new ModelMapper();
            modelMapper.AddMapping<CinemaMapping>();
            modelMapper.AddMapping<CompanyMapping>();
            modelMapper.AddMapping<MovieMapping>();
            modelMapper.AddMapping<LanguageMapping>();
            modelMapper.AddMapping<FileMapping>();
            configuration.AddDeserializedMapping(modelMapper.CompileMappingForAllExplicitlyAddedEntities(), null);

            var factory = configuration.BuildSessionFactory();

            new SchemaUpdate(configuration).Execute(false, true);

            return factory;
        }
    }
}