using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class LanguageRepository:ILanguageRepository
    {
        private readonly ISession session;
        public IEnumerable<Language> GetAllLanguages()
        {
            return session.Query<Language>();
        }

        public void SaveLanguage(Language language)
        {
            session.BeginTransaction();
            session.SaveOrUpdate(language);
            session.Transaction.Commit();
        }

        public IEnumerable<Language> GetLanguagesByMovieId(Guid movieId)
        {
            return session.Query<Language>().Where(language => language.MovieId == movieId);
        }

        public void DeleteLanguageByLanguageId(Guid? languageID)
        {
            var checkIfExists = session.Query<Language>().SingleOrDefault(language => language.Id == languageID);
            if (checkIfExists != null) session.Delete((checkIfExists));
        }

        public void DeleteLanguageByMovieId(Guid movieId)
        {
            var checkIfExists = session.Query<Language>().Where(language => language.MovieId == movieId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }
    }
}