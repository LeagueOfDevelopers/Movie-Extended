using System.Collections.Generic;
using System.Linq;
using Domain.Models.Entities;
using Domain.VisitorRepository;
using NHibernate.Linq;
using Journalist;


namespace Infrastructure.VisitorRepository
{
    public class CinemaRepository : ICinemaRepository
    {
        private readonly SessionProvider _provider ;

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
            session.Save(cinema);
            session.Transaction.Commit();
        }

        public IEnumerable<Cinema> GetCinemaByCompanyId(int companyId)
        {
            var session = _provider.GetCurrentSession();
            var allcinemasById = session.Query<Cinema>().Where(cinema => cinema.Company.Id==companyId).AsEnumerable<Cinema>();
            
            return allcinemasById;
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

        public void DeleteCinemaByCompanyId(int companyId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Cinema>().Where(cinema => cinema.Company.Id == companyId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public Cinema GetCinemaByCinemaId(int cinemaId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Cinema>().SingleOrDefault(cinema => cinema.Id == cinemaId);
        }

        public void UpdateCinema(string jsonForUpdate)
        {
            throw new System.NotImplementedException();
        }
    }
}