using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using Domain.VisitorRepository;
using Journalist;
using NHibernate;
using NHibernate.Linq;

namespace Infrastructure.VisitorRepository
{
    public class LanguageRepository:ILanguageRepository
    {
        private readonly SessionProvider _provider;
        

        public LanguageRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }
        public IEnumerable<Language> GetAllLanguages()
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>();
        }

        public void SaveLanguage(Language language)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(language);
            session.Transaction.Commit();
        }

        

        public IEnumerable<Language> GetLanguagesByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>().Where(language => language.MovieId == movieId);
        }

        public void DeleteLanguageByLanguageId(int languageID)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Language>().SingleOrDefault(language => language.Id == languageID);
            if (checkIfExists != null) session.Delete((checkIfExists));
        }

        public void DeleteLanguageByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Language>().Where(language => language.MovieId == movieId);
            if (checkIfExists != null) session.Delete(checkIfExists);   
        }

        public IEnumerable<Language> GetLanguageByName(string languageName)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>().Where(language => language.Name == languageName);
        }
    }
}