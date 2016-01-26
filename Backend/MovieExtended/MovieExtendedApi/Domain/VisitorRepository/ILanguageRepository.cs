using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
    public interface ILanguageRepository
    {
        IEnumerable<Language> GetAllLanguages();
        void SaveLanguage(Language language);
        IEnumerable<Language> GetLanguagesByMovieId(string movieId);
        void DeleteLanguageByLanguageId(string languageID);
        void DeleteLanguageByMovieId(string movieId);
        IEnumerable<Language> GetLanguageByName(string languageName);
    }
}
