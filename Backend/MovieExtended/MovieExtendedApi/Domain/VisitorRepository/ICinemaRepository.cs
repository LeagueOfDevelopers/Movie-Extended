using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface ICinemaRepository
    {
        IEnumerable<Cinema> GetAllCinemas();
        void SaveCinemaData(Cinema cinema);
        IEnumerable<Cinema> GetCinemaByCompanyId(int companyId);
        void DeleteCinemaByCinemaId(int cinemaId);
        void DeleteCinemaByCompanyId(int companyId);
        Cinema GetCinemaByCinemaId(int cinemaId);

    }
}
