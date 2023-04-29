using CurrencyConverterWebServiceTests;
using Service.CurrencyConverterLogic;
using System.Net.Http;
using System.Security.Authentication;
using System.Web.Services;
using System.Web.Services.Protocols;

namespace Service
{
    /// <summary>
    /// Summary description for WebService1
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(Name = "TestService", ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WebService1 : System.Web.Services.WebService
    {


        public UserCredentials consumer;


        [WebMethod]
        [SoapDocumentMethod(Binding = "TestService")]
        [SoapHeader("consumer")]
        public decimal ConvertCurrency(decimal amount, string from, string to)
        {

            if (!checkConsumer())
            {
                throw new AuthenticationException("Fehler bei der Authentifizierung: ungültige Anmeldeinformationen.");
            }


            IHttpClient httpClient = new HttpClientWrapper();
            CurrencyConverter currencyConverter = new CurrencyConverter();
            ReferenceRateProvider referenceRateProvider = new ReferenceRateProvider(httpClient);

            decimal fromRate = 0;
            decimal toRate = 0;

            fromRate = referenceRateProvider.GetRateForCurrency(from);
            toRate = referenceRateProvider.GetRateForCurrency(to);

            return currencyConverter.Convert(amount, fromRate, toRate);

        }


        private bool checkConsumer()
        {
            // In this method you can check the username and password 
            // with your database or something
            // You could also encrypt the password for more security
            if (consumer != null)
            {
                if (consumer.username == "Alex" && consumer.password == "1234")
                    return true;
                else
                    return false;
            }
            else
                return false;
        }

    }
}
