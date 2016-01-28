using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using Domain.VisitorRepository;
using NHibernate;
using NHibernate.Linq;

namespace Infrastructure.VisitorRepository
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
            _session.Save(language);
            _session.Transaction.Commit();
        }

        public IEnumerable<Language> GetLanguagesByMovieId(int movieId)
        {
            return _session.Query<Language>().Where(language => language.MovieId == movieId);
        }

        public void DeleteLanguageByLanguageId(int languageId)
        {
            var checkIfExists = _session.Query<Language>().SingleOrDefault(language => language.Id == languageId);
            if (checkIfExists != null) _session.Delete((checkIfExists));
        }

        public void DeleteLanguageByMovieId(int movieId)
        {
            var checkIfExists = _session.Query<Language>().Where(language => language.MovieId == movieId);
            if (checkIfExists != null) _session.Delete(checkIfExists);   
        }

        public IEnumerable<Language> GetLanguageByName(string languageName)
        {
            return _session.Query<Language>().Where(language => language.Name == languageName);
        }
    }
}