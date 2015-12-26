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

        private readonly ISession session;

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
            throw new NotImplementedException();
        }

        public void DeleteCinemaByCompanyId(Guid companyId)
        {
            throw new NotImplementedException();
        }

        public Cinema GetCinemaByCinemaId(Guid? cinemaId)
        {
            return session.Query<Cinema>().SingleOrDefault(cinema => cinema.Id == cinemaId);
        }
    }
}