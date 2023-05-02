<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://tempuri.org/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://tempuri.org/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://tempuri.org/">
      <s:element name="ConvertCurrency">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="amount" type="s:decimal" />
            <s:element minOccurs="0" maxOccurs="1" name="from" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="to" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ConvertCurrencyResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="ConvertCurrencyResult" type="s:decimal" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="ConvertCurrencySoapIn">
    <wsdl:part name="parameters" element="tns:ConvertCurrency" />
  </wsdl:message>
  <wsdl:message name="ConvertCurrencySoapOut">
    <wsdl:part name="parameters" element="tns:ConvertCurrencyResponse" />
  </wsdl:message>
  <wsdl:portType name="WebService1Soap">
    <wsdl:operation name="ConvertCurrency">
      <wsdl:input message="tns:ConvertCurrencySoapIn" />
      <wsdl:output message="tns:ConvertCurrencySoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="WebService1Soap" type="tns:WebService1Soap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="ConvertCurrency">
      <soap:operation soapAction="http://tempuri.org/ConvertCurrency" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="WebService1Soap12" type="tns:WebService1Soap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="ConvertCurrency">
      <soap12:operation soapAction="http://tempuri.org/ConvertCurrency" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="WebService1">
    <wsdl:port name="WebService1Soap" binding="tns:WebService1Soap">
      <soap:address location="https://currencyconvertercarrental.azurewebsites.net/CurrencyConverterWS.asmx" />
    </wsdl:port>
    <wsdl:port name="WebService1Soap12" binding="tns:WebService1Soap12">
      <soap12:address location="https://currencyconvertercarrental.azurewebsites.net/CurrencyConverterWS.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>