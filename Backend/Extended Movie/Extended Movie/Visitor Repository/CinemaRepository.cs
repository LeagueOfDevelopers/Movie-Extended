using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class CinemaRepository : ICinemaRepository
    {

        private readonly ISession session ;

        public CinemaRepository()
        {
            
        }

        public IEnumerable<Cinema> GetAllCinemas()
        {
            return session.Query<Cinema>();
        }

        public void SaveCinemaData(Cinema cinema)
        {
            session.BeginTransaction();
            session.SaveOrUpdate(cinema);
            session.Transaction.Commit();
        }

        public Cinema GetCinemaByCompanyId(Guid companyId)
        {
            return session.Query<Cinema>().SingleOrDefault(cinema => cinema.CompanyId==companyId);
        }

        public void DeleteCinemaByCinemaId(Guid? cinemaId)
        {
            var checkIfExists = session.Query<Cinema>().Where(cinema => cinema.Id == cinemaId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void DeleteCinemaByCompanyId(Guid companyId)
        {
            var checkIfExists = session.Query<Cinema>().Where(cinema => cinema.CompanyId == companyId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public Cinema GetCinemaByCinemaId(Guid? cinemaId)
        {
            return session.Query<Cinema>().SingleOrDefault(cinema => cinema.Id == cinemaId);
        }
    }
}