﻿using System.Security.Cryptography;
using System.Text;

namespace Domain.Models
{
    public class QrCodeGenerator : IQrCodeGenerator
    {
        public string Generate(int movieId, int companyId)
        {
            var code = CreateMd5(movieId + companyId.ToString() + Constants.TokenGenerationSecret);
            var codeSha1 = GetSha1(code + Constants.CodeGenerationSecret);
            return codeSha1;
        }

        private string CreateMd5(string input)
        {
            // Use input string to calculate MD5 hash
            var md5 = MD5.Create();
            var inputBytes = Encoding.ASCII.GetBytes(input);
            var hashBytes = md5.ComputeHash(inputBytes);

            // Convert the byte array to hexadecimal string
            var sb = new StringBuilder();
            for (var i = 0; i < hashBytes.Length; i++)
            {
                sb.Append(hashBytes[i].ToString("X2"));
            }
            return sb.ToString();
        }

        private string GetSha1(string input)
        {
            var key = GetBytes(input);
            var sha1 = SHA1.Create();
            var hash = sha1.ComputeHash(key);
            var resultString = ConvertToString(hash);
            return resultString;
        }

        private byte[] GetBytes(string input)
        {
            return Encoding.UTF8.GetBytes(input);
        }

        private string ConvertToString(byte[] input)
        {
            return Encoding.Default.GetString(input);
        }
    }
}