using System.Collections.Generic;
using Domain.Models.Entities;

namespace Domain.Repository
{
    public interface ICinemaRepository
    {
        IEnumerable<Cinema> GetAllCinemas();
        void SaveCinemaData(Cinema cinema);
        IEnumerable<Cinema> GetCinemaByCompanyId(int company);
        void DeleteCinemaByCinemaId(int cinemaId);
        void DeleteCinemaByCompanyId(int company);
        Cinema GetCinemaByCinemaId(int cinemaId);
        void UpdateCinema(string jsonForUpdate);
        int CreateMovie(Movie movie, int cinemaId);
    }
}