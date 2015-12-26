using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    public class LanguageRepository:ILanguageRepository
    {
        public IEnumerable<Language> GetAllLanguages()
        {
            throw new NotImplementedException();
        }

        public void SaveLanguage(Language language)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Language> GetLanguagesByMovieId(Guid movieId)
        {
            throw new NotImplementedException();
        }

        public void DeleteLanguageByLanguageId(Guid? languageID)
        {
            throw new NotImplementedException();
        }

        public void DeleteLanguageByMovieId(Guid movieId)
        {
            throw new NotImplementedException();
        }
    }
}