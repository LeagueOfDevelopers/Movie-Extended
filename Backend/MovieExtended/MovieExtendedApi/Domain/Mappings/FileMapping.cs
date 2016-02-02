using Domain.Models;
using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;


namespace Domain.Mappings
{
    public class FileMapping:ClassMapping<File>
    {
        public FileMapping()
        {
            Table("Files");
            Id(file => file.Id, mapper =>
            {
                mapper.Generator(Generators.EnhancedTable);
            });
            Property(file => file.FilePath, mapper => mapper.Column("FilePath"));
            Property(file => file.FileType, mapper => mapper.Column("FileType"));
        }
    }
}