using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    interface ILanguageRepository
    {
        IEnumerable<Language> GetAllLanguages();
        void SaveLanguage(Language language);
        IEnumerable<Language> GetLanguagesByMovieId(Guid movieId);
        void DeleteLanguageByLanguageId(Guid? languageID);

    }
}
