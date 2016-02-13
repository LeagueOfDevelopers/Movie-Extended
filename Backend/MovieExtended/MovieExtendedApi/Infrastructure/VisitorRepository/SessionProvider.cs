using System;
using System.IO;
using Domain.Mappings;
using Domain.Models.Entities;
using NHibernate;
using NHibernate.Cfg;
using NHibernate.Mapping.ByCode;
using NHibernate.Tool.hbm2ddl;

namespace Infrastructure.VisitorRepository
{
    public  class SessionProvider
    {
        [ThreadStatic]
        private static ISession _session;

        [ThreadStatic]
        private static ITransaction _transaction;

        private readonly ISessionFactory _factory;

        public SessionProvider()
        {
            var configuration = new Configuration();
            configuration.Configure();
            var modelMapper = new ModelMapper();
            modelMapper.AddMapping<CinemaMapping>();
            modelMapper.AddMapping<CompanyMapping>();
            modelMapper.AddMapping<MovieMapping>();
            modelMapper.AddMapping<LanguageMapping>();
            modelMapper.AddMapping<FileMapping>();
            modelMapper.AddMapping<QrCodeFingerprintMapping>();
            modelMapper.AddMapping<AndroidTokenMapping>();
            configuration.AddDeserializedMapping(modelMapper.CompileMappingForAllExplicitlyAddedEntities(), null);

            _factory = configuration.BuildSessionFactory();

            new SchemaUpdate(configuration).Execute(false, true);
        }

        public ISession GetCurrentSession()
        {
            return _session;
        }
        
        public void OpenSession()
        {
            if (_session == null || !_session.IsOpen)
            {
                _session = _factory.OpenSession();
            }

            _transaction = _session.BeginTransaction();
        }

        public void CloseSession()
        {
            if (_transaction != null && _transaction.IsActive)
            {
                _transaction.Commit();
            }

            _session?.Dispose();
        }
    }
}