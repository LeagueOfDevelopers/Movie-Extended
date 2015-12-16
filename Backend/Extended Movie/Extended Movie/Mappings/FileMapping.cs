using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;
using Extended_Movie.Models;

namespace Extended_Movie.Mappings
{
    public class FileMapping:ClassMapping<File>
    {
        public FileMapping()
        {
            Table("Files");
            Id(file => file.Id, mapper =>
            {
                mapper.Generator(Generators.Guid);
                mapper.Column("Id");
            });
            Property(file => file.FilePath, mapper => mapper.Column("FilePath"));
            Property(file => file.FileType, mapper => mapper.Column("FileType"));
        }
    }
}