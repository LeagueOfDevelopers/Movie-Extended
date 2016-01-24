using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class LanguageRepository:ILanguageRepository
    {
        private readonly ISession _session;
        

        public LanguageRepository(ISession session)
        {
            _session = session;
        }
        public IEnumerable<Language> GetAllLanguages()
        {
            return _session.Query<Language>();
        }

        public void SaveLanguage(Language language)
        {
            _session.BeginTransaction();
            _session.SaveOrUpdate(language);
            _session.Transaction.Commit();
        }

        public IEnumerable<Language> GetLanguagesByMovieId(Guid movieId)
        {
            return _session.Query<Language>().Where(language => language.MovieId == movieId);
        }

        public void DeleteLanguageByLanguageId(Guid? languageID)
        {
            var checkIfExists = _session.Query<Language>().SingleOrDefault(language => language.Id == languageID);
            if (checkIfExists != null) _session.Delete((checkIfExists));
        }

        public void DeleteLanguageByMovieId(Guid movieId)
        {
            var checkIfExists = _session.Query<Language>().Where(language => language.MovieId == movieId);
            if (checkIfExists != null) _session.Delete(checkIfExists);   
        }
    }
}