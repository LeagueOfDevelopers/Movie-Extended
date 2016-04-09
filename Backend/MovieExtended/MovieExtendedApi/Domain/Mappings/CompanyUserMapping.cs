using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class CompanyUserMapping:ClassMapping<CompanyUser>
    {
        public CompanyUserMapping()
        {
            Table("CompanyUsers");
            Id(user => user.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.Identity);
            } );
            Property(user =>user.FirstName,mapper => mapper.Column("FirstName"));
            Property(user => user.LastName, mapper => mapper.Column("Lastname"));
            Property(user => user.MD5Hash, mapper => mapper.Column("Hash"));
            Property(user => user.Email , mapper => mapper.Column("Email"));
            OneToOne(user => user.Company, mapper =>
            {
                mapper.Cascade(Cascade.All);
                
            } );

        }
    }
}
