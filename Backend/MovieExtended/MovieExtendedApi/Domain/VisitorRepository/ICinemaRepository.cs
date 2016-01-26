using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface ICinemaRepository
    {
        IEnumerable<Cinema> GetAllCinemas();
        void SaveCinemaData(Cinema cinema);
        IEnumerable<Cinema> GetCinemaByCompanyId(string companyId);
        void DeleteCinemaByCinemaId(string cinemaId);
        void DeleteCinemaByCompanyId(string companyId);
        Cinema GetCinemaByCinemaId(string cinemaId);

    }
}
