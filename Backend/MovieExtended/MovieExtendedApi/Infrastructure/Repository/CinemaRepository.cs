using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models.Entities;
using Domain.Repository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.Repository
{
    public class CinemaRepository : ICinemaRepository
    {
        private readonly SessionProvider _provider;

        public CinemaRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }

        public IEnumerable<Cinema> GetAllCinemas()
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Cinema>();
        }

        public void SaveCinemaData(Cinema cinema)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.SaveOrUpdate(cinema);
            session.Transaction.Commit();
        }

        public IEnumerable<Cinema> GetCinemaByCompanyId(int company)
        {
            throw new NotImplementedException();
        }


        public void DeleteCinemaByCinemaId(int cinemaId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Cinema>().Where(cinema => cinema.Id == cinemaId);
            if (checkIfExists != null)
            {
                session.BeginTransaction();
                session.Delete(checkIfExists);
                session.Transaction.Commit();
            }
        }

        public void DeleteCinemaByCompanyId(int company)
        {
            throw new NotImplementedException();
        }

       

        public Cinema GetCinemaByCinemaId(int cinemaId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Cinema>().SingleOrDefault(cinema => cinema.Id == cinemaId);
        }

        public void UpdateCinema(string jsonForUpdate)
        {
            throw new NotImplementedException();
        }
    }
}