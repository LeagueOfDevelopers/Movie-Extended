using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models.FrontendEntities
{
    public class AndroidLanguage
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public int TrackFileId { get; set; }

        public AndroidLanguage()
        {
            
        }
    }
}
