﻿using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
    public interface ILanguageRepository
    {
        IEnumerable<Language> GetAllLanguages();
        void SaveLanguage(Language language);
        IEnumerable<Language> GetLanguagesByMovieId(Guid movieId);
        void DeleteLanguageByLanguageId(Guid? languageID);
        void DeleteLanguageByMovieId(Guid movieId);
    }
}