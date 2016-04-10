using System.ComponentModel.DataAnnotations;

namespace Domain.Models.FrontendEntities
{
   public class Credentials
    {
       protected Credentials()
       {
           
       }

       public Credentials(string emal, string password)
       {
           Email = emal;
           Pass = password;
       }
        [EmailAddress]
        public virtual string Email { get; set; }
        public virtual string Pass { get; set; }
    }
}
