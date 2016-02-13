using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models.Entities
{
    public class AndroidToken
    {
        public AndroidToken(int Id ,  Guid qrCodeToken)
        {
            this.Id = Id;
            this.qrCodeToken = qrCodeToken;
        }
        
        public AndroidToken()
        {
            qrCodeToken = Guid.NewGuid();
        }

        public virtual int Id { get; protected set; }
        public virtual Guid qrCodeToken { get; protected set; }
    }
}
