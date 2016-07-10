using System.Collections.Generic;
using System.Net.Mail;
using Domain.Models;
using Domain.Models.Entities;
using Journalist;

namespace Domain.Authorization
{
    public class Account
    {
        public Account(
            string firstname,
            string lastname,
            MailAddress email,
            Password password
           )
        {
            Require.NotEmpty(firstname, nameof(firstname));
            Require.NotEmpty(lastname, nameof(lastname));
            Require.NotNull(email, nameof(email));
            Require.NotNull(password, nameof(password));
            Require.NotNull(email, nameof(email));
            Require.NotNull(password, nameof(password));

            Firstname = firstname;
            Lastname = lastname;
            Email = email;
            Password = password;
        }


        protected Account()
        {
        }

        public virtual int UserId { get; protected set; }

        public virtual string Firstname { get; protected set; }

        public virtual string Lastname { get; protected set; }

        public virtual MailAddress Email { get; protected set; }

        public virtual Password Password { get; set; }
        
        public virtual ISet<Company> Companies { get; set; } 



    }
}
