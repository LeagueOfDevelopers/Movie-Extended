using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Models.Entities;

namespace Domain.FingerPrinting
{
   public interface IFingerPrintKeeper
    {
       void CreateHashes(string audiopath,Movie movie);
       bool AudioHashExists(int id);
       void DeleteHashes(int id);
       double QueryWithTimeInformation(string snippetway);
       bool IfTimeExists(int movieId);
    }
}
