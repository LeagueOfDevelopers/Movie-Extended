using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class CinemaRepository : ICinemaRepository
    {

        private readonly ISession _session ;

        public CinemaRepository(ISession session)
        {
            _session = session;
        }

        public IEnumerable<Cinema> GetAllCinemas()
        {
            return _session.Query<Cinema>();
        }

        public void SaveCinemaData(Cinema cinema)
        {
            _session.BeginTransaction();
            _session.SaveOrUpdate(cinema);
            _session.Transaction.Commit();
        }

        public Cinema GetCinemaByCompanyId(Guid companyId)
        {
            return _session.Query<Cinema>().SingleOrDefault(cinema => cinema.CompanyId==companyId);
        }

        public void DeleteCinemaByCinemaId(Guid? cinemaId)
        {
            var checkIfExists = _session.Query<Cinema>().Where(cinema => cinema.Id == cinemaId);
            if (checkIfExists != null) _session.Delete(checkIfExists);
        }

        public void DeleteCinemaByCompanyId(Guid companyId)
        {
            var checkIfExists = _session.Query<Cinema>().Where(cinema => cinema.CompanyId == companyId);
            if (checkIfExists != null) _session.Delete(checkIfExists);
        }

        public Cinema GetCinemaByCinemaId(Guid? cinemaId)
        {
            return _session.Query<Cinema>().SingleOrDefault(cinema => cinema.Id == cinemaId);
        }
    }
}