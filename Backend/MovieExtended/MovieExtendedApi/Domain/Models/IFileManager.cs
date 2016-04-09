using System.Net.Http;
using System.Web;

namespace Domain.Models
{
    public interface IFileManager
    {
        HttpResponseMessage GetAnyFile(string filepath);
        void DeleteFile(string filepath);
        bool CheckExtension(string extension);
        string CreateTrackPath(string filename);
        string CreateImagePath(string filename);
        string CreateSubtitlePath(string filename);
        void SaveFileAs(HttpPostedFile file, string filepath);
    }
}
