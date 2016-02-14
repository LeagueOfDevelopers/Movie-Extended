using System.Collections.Generic;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
{
    public interface ILanguageRepository
    {
        IEnumerable<Language> GetAllLanguages();
        void SaveLanguage(Language language);
        IEnumerable<Language> GetLanguagesByMovieId(int movie);
        void DeleteLanguageByLanguageId(int languageID);
        void DeleteLanguageByMovieId(int movie);
        IEnumerable<Language> GetLanguageByName(string languageName);
        void UpdateLanguage(string jsonForUpdate);
    }
}
