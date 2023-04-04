using Moq;
using Service.CurrencyConverterLogic;
using System.Net;
using System.Xml;

namespace CurrencyConverterWebServiceTests
{
    public class Tests
    {
        private Mock<IHttpClient> _httpClientMock;
        private ReferenceRateProvider _rateProvider;
        private CurrencyConverter _currencyConverter;

        [SetUp]
        public void Setup()
        {
            _httpClientMock = new Mock<IHttpClient>();
            _rateProvider = new ReferenceRateProvider(_httpClientMock.Object);
            _currencyConverter = new CurrencyConverter();
        }

        [Test]
        [TestCase(5, 1, 144.7, 723.5)]
        [TestCase(3, 1.0078, 25, 74.42)]
        [TestCase(-2, 2.25, 1.5, -1.33)]
        public void ConvertCurrency_WhenValidValues_CalculateExchange(decimal amount, decimal fromRate, decimal toRate, decimal expected)
        {
            // Arrange

            // Act
            decimal actual = _currencyConverter.Convert(amount, fromRate, toRate);

            // Assert
            Assert.That(actual, Is.EqualTo(expected).Within(0.01));
        }


        [Test]
        [TestCase(24, 2.34, 1.43)]
        [TestCase(-324, 12.43, 111.43)]
        [TestCase(500, 5.34, 10.4)]
        [TestCase(10000, 144.7, 1.008)]
        public void ConvertCurrency_SwitchFromAndToRate_RevertsExchange(decimal amount, decimal fromRate, decimal toRate)
        {

            // Arrange
            decimal expected = amount;

            // Act
            decimal exchangedValue = _currencyConverter.Convert(amount, fromRate, toRate);
            decimal actual = _currencyConverter.Convert(exchangedValue, toRate, fromRate);

            // Assert
            Assert.That(actual, Is.EqualTo(expected).Within(0.01));

        }


        [Test]
        [TestCase(0, 0)]
        [TestCase(0, -1)]
        [TestCase(4, 0)]
        [TestCase(4, -3)]
        [TestCase(-1, 6)]
        [TestCase(-3, -2)]
        public void ConvertCurrency_WhenNegativeOrZeroRateGiven_ThrowInvalidDataException(decimal fromRate, decimal toRate)
        {

            // Arrange
            decimal amount = 1;
            // Act

            // Assert
            Assert.Throws<InvalidDataException>(() => _currencyConverter.Convert(amount, fromRate, toRate));

        }


        [Test]
        public void GetRateForCurrency_ReturnsExpectedRate()
        {

            // Arrange
            var xmlString =
                "<Cube><Cube time='2023-04-03'>" +
                "<Cube currency='USD' rate='1.2345'/>" +
                "<Cube currency='JPY' rate='144'/>" +
                "</Cube></Cube>";
            var responseMessage = new HttpResponseMessage(HttpStatusCode.OK);
            responseMessage.Content = new StringContent(xmlString);

            _httpClientMock.Setup(x => x.Get(It.IsAny<string>())).Returns(responseMessage);

            // Act
            var rateUSD = _rateProvider.GetRateForCurrency("USD");
            var rateJPY = _rateProvider.GetRateForCurrency("JPY");

            // Assert
            Assert.That(rateUSD, Is.EqualTo(1.2345));
            Assert.That(rateJPY, Is.EqualTo(144));

        }


        [Test]
        public void GetRateForCurrency_CurrencyNotExisting_ThrowsArgumentNotFoundExpcetion()
        {
            // Arrange
            var xmlString =
                           "<Cube><Cube time='2023-04-03'>" +
                           "<Cube currency='USD' rate='1.2345'/>" +
                           "<Cube currency='JPY' rate='144'/>" +
                           "</Cube></Cube>";
            var responseMessage = new HttpResponseMessage(HttpStatusCode.OK);
            responseMessage.Content = new StringContent(xmlString);
            _httpClientMock.Setup(x => x.Get(It.IsAny<string>())).Returns(responseMessage);

            // Act

            // Assert
            Assert.Throws<KeyNotFoundException>(() => _rateProvider.GetRateForCurrency("RON"));

        }


        [Test]
        public void GetRateForCurrency_XmlCouldNotBeRetrieved_ThrowsArgumentException()
        {
            var responseMessage = new HttpResponseMessage(HttpStatusCode.Conflict);
            _httpClientMock.Setup(x => x.Get(It.IsAny<string>())).Returns(responseMessage);

            Assert.Throws<XmlException>(() => _rateProvider.GetRateForCurrency("RON"));
        }
    }
}