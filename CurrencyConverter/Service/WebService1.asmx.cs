using System;
using System.Globalization;
using System.Net.Http;
using System.Web.Services;
using System.Xml;

namespace Service
{
    /// <summary>
    /// Summary description for WebService1
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WebService1 : System.Web.Services.WebService
    {

        [WebMethod]
        public float ConvertCurrency(float amount, string from, string to)
        {

            var httpClient = new HttpClient();
            var response = httpClient.GetAsync("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml").Result;
            var xmlString = response.Content.ReadAsStringAsync().Result;
            var xmlDocument = new XmlDocument();
            xmlDocument.LoadXml(xmlString);

            XmlReaderSettings settings = new XmlReaderSettings();
            settings.IgnoreComments = true;
            settings.IgnoreWhitespace = true;
            XmlReader reader = XmlReader.Create(new System.IO.StringReader(xmlString), settings);

            // Find the Cube node with the exchange rates
            reader.ReadToFollowing("Cube");
            reader.ReadToFollowing("Cube");
            string time = reader.GetAttribute("time");

            string rate = "0";
            // Find the Cube node with USD exchange rate
            reader.ReadToFollowing("Cube");
            while (reader.Read())
            {
                while (reader.MoveToNextAttribute())
                {
                    if (reader.Name == "currency" && reader.Value == to)
                    {
                        rate = reader.GetAttribute("rate");
                        Console.WriteLine("Exchange rate: " + rate);
                        return float.Parse(rate, CultureInfo.InvariantCulture);
                    }
                }
            }

            return 0;
        }


        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World!!!";
        }
    }
}
