using System.Collections.Generic;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
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
    }
}