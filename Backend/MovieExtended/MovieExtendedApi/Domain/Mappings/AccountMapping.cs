using System.Net.Mail;
using Domain.Authorization;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class AccountMapping:ClassMapping<Account>
    {
        public AccountMapping()
        {
            Table("Account");
            Id(user => user.UserId,mapper=> mapper.Generator(Generators.Identity));
            Property(user => user.Email , mapper =>
            {
                mapper.Column("Email");
                mapper.Unique(true);
                mapper.Type<MailAddressType>();
            });
            Property(account => account.Firstname , mapper => mapper.Column("FirstName")  );
            Property(account => account.Lastname, mapper => mapper.Column("LastName"));
            Property(account => account.Password , mapper =>
            {
                mapper.Column("Password");
                mapper.Type<PasswordType>();
            }  );
        }
    }
}
